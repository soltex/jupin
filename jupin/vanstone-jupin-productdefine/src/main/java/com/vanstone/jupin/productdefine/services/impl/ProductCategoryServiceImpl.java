/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.productdefine.Brand;
import com.vanstone.jupin.productdefine.ProductCategory;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.Attr4Text;
import com.vanstone.jupin.productdefine.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTAttributeEnumvalueDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTCategoryAttributeDefRelDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryAttributeDefRelDOKey;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.productdefine.services.CategoryHasProductsException;
import com.vanstone.jupin.productdefine.services.CategoryHasSubCategoriesException;
import com.vanstone.jupin.productdefine.services.ProductCategoryService;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("productCategoryService")
@Validated
public class ProductCategoryServiceImpl extends DefaultBusinessService implements ProductCategoryService {

	/***/
	private static final long serialVersionUID = -390257932925884113L;

	private static Logger LOG = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PDTCategoryDOMapper pdtCategoryDOMapper;
	@Autowired
	private PDTAttributeDefDOMapper pdtAttributeDefDOMapper;
	@Autowired
	private PDTAttributeEnumvalueDOMapper pdtAttributeEnumvalueDOMapper;
	@Autowired
	private PDTCategoryAttributeDefRelDOMapper pdtCategoryAttributeDefRelDOMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addProductCategory(com.vanstone.jupin.productdefine.ProductCategory)
	 */
	@Override
	public ProductCategory addProductCategory(@NotNull final ProductCategory productCategory)
			throws CategoryHasProductsException {
		if (productCategory.getParentProductCategory() != null
				&& productCategory.getParentProductCategory().isExistProduct()) {
			throw new CategoryHasProductsException();
		}
		// 写入品类信息并更新父品类信息
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryDO pdtCategoryDO = BeanUtil.toPdtCategoryDO(productCategory);
				pdtCategoryDOMapper.insert(pdtCategoryDO);
				productCategory.setId(pdtCategoryDO.getId());
				if (productCategory.getParentProductCategory() != null && productCategory.isLeafable()) {
					PDTCategoryDO parentModel = new PDTCategoryDO();
					parentModel.setId(productCategory.getParentProductCategory().getId());
					parentModel.setLeafable(BoolUtil.parseBoolean(false));
					pdtCategoryDOMapper.updateByPrimaryKeySelective(parentModel);
				}
			}
		});
		// 刷新缓冲
		refreshAllProductCategories();
		return this.getProductCategoryDetail(productCategory.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addProductCategory(com.vanstone.jupin.productdefine.ProductCategory,
	 * java.util.Collection)
	 */
	@Override
	public ProductCategory addProductCategory(@NotNull final ProductCategory productCategory,
			final Collection<AbstractAttribute> attributes) throws CategoryHasProductsException {
		if (productCategory.getParentProductCategory() != null
				&& productCategory.getParentProductCategory().isExistProduct()) {
			throw new CategoryHasProductsException();
		}
		// 写入品类信息并更新父品类信息
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryDO pdtCategoryDO = BeanUtil.toPdtCategoryDO(productCategory);
				pdtCategoryDOMapper.insert(pdtCategoryDO);
				productCategory.setId(pdtCategoryDO.getId());
				if (productCategory.getParentProductCategory() != null && productCategory.isLeafable()) {
					PDTCategoryDO parentModel = new PDTCategoryDO();
					parentModel.setId(productCategory.getParentProductCategory().getId());
					parentModel.setLeafable(BoolUtil.parseBoolean(false));
					pdtCategoryDOMapper.updateByPrimaryKeySelective(parentModel);
				}
				if (attributes != null && attributes.size() > 0) {
					for (AbstractAttribute attribute : attributes) {
						MyAssert4Business.objectInitialized(attribute);
						PDTCategoryAttributeDefRelDOKey key = new PDTCategoryAttributeDefRelDOKey();
						key.setCategoryId(pdtCategoryDO.getId());
						key.setAttributeDefId(attribute.getId());
						pdtCategoryAttributeDefRelDOMapper.insert(key);
					}
				}
			}
		});
		// 刷新缓冲
		refreshAllProductCategories();
		return this.getProductCategoryDetail(productCategory.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getProductCategoryDetail(int)
	 */
	@Override
	public ProductCategory getProductCategoryDetail(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 从DB中直接获取Brand列表
	 * @param categoryID
	 * @return
	 */
	private Collection<Brand> _loadBrandsByCategoryIDFromDB(int categoryID) {
		return null;
	}
	
	/**
	 * 从DB中直接获取Attribute列表通过排序获取
	 * @param categoryIDs
	 * @return
	 */
	private Collection<AbstractAttribute> _loadAttributesByCategoryIDsFromDB(int[] categoryIDs) {
		return null;
	}
	
	/**
	 * 从DB中直接获取Attribute列表获取
	 * @param categoryID
	 * @return
	 */
	private Collection<AbstractAttribute> _loadAttributesByCategoryID(int categoryID) {
		return null;
	}
	
	@Override
	public ProductCategory getProductCategoryDetailAndValidate(int id) {
		ProductCategory productCategory = this.getProductCategoryDetail(id);
		MyAssert4Business.notNull(productCategory);
		return productCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getRootProductCategoryDetail()
	 */
	@Override
	public ProductCategory getRootProductCategoryDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateBaseProductCategoryInfo(int, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ProductCategory updateBaseProductCategoryInfo(int id, String categoryName, String description,
			String categoryBindPage, String formTemplate, Integer sort) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateProductCategoryCoverImage(int,
	 * com.vanstone.jupin.common.entity.ImageBean)
	 */
	@Override
	public ProductCategory updateProductCategoryCoverImage(final int id, final ImageBean coverImage)
			throws CategoryHasProductsException {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(id);
		if (productCategory.isExistProduct()) {
			throw new CategoryHasProductsException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateCoverImage(id, coverImage.getWeedFile().getFileid(), coverImage.getWeedFile()
						.getExtName(), coverImage.getWidth(), coverImage.getHeight());
			}
		});
		this.refreshAllProductCategories();
		return this.getProductCategoryDetail(id);
	}

	@Override
	public ProductCategory deleteProductCategoryCoverImage(final int id) throws CategoryHasProductsException {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(id);
		if (productCategory.isExistProduct()) {
			throw new CategoryHasProductsException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateCoverImage(id, null, null, null, null);
			}
		});
		this.refreshAllProductCategories();
		return this.getProductCategoryDetail(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * deleteProductCategory(int)
	 */
	@Override
	public void deleteProductCategory(int id) throws CategoryHasProductsException, CategoryHasSubCategoriesException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addAttributeToProductCategory(int, java.util.Collection)
	 */
	@Override
	public void addAttributeToProductCategory(int productCategoryId, Collection<AbstractAttribute> attributes)
			throws CategoryHasSubCategoriesException, ObjectDuplicateException {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * deleteAttributeInProductCategory(int, int)
	 */
	@Override
	public void deleteAttributeInProductCategory(int productCategoryId, int attributeId)
			throws CategoryHasProductsException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * forceDeleteProductCategory(int)
	 */
	@Override
	public void forceDeleteProductCategory(int id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshAllProductCategories()
	 */
	@Override
	public void refreshAllProductCategories() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshProductCategory(int)
	 */
	@Override
	public void refreshProductCategory(int id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshRootProductCategory()
	 */
	@Override
	public void refreshRootProductCategory() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateExistProductState(int, boolean)
	 */
	@Override
	public void updateExistProductState(int productCategoryID, boolean existProduct) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getProductCategories(java.lang.Integer)
	 */
	@Override
	public Collection<ProductCategory> getProductCategories(Integer parentID) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vanstone.jupin.productdefine.services.ProductCategoryService#addAttribute
	 * (com.vanstone.jupin.productdefine.attr.AbstractAttribute)
	 */
	@Override
	public AbstractAttribute addAttribute(AbstractAttribute attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateAttr4Text(com.vanstone.jupin.productdefine.attr.Attr4Text)
	 */
	@Override
	public Attr4Text updateAttr4Text(Attr4Text attr4Text) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateAttr4Enum(com.vanstone.jupin.productdefine.attr.Attr4Enum)
	 */
	@Override
	public Attr4Enum updateAttr4Enum(Attr4Enum attr4Enum) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vanstone.jupin.productdefine.services.ProductCategoryService#getAttribute
	 * (int)
	 */
	@Override
	public AbstractAttribute getAttribute(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getAttributesByIDs(java.util.Collection)
	 */
	@Override
	public Collection<AbstractAttribute> getAttributesByIDs(Collection<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
