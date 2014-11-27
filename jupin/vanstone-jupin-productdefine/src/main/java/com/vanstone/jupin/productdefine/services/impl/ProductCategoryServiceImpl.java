/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.jupin.productdefine.Brand;
import com.vanstone.jupin.productdefine.ProductCategory;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.Attr4EnumValue;
import com.vanstone.jupin.productdefine.attr.Attr4Text;
import com.vanstone.jupin.productdefine.attr.AttributeState;
import com.vanstone.jupin.productdefine.attr.AttributeType;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;
import com.vanstone.jupin.productdefine.cache.ProductDefineCacheKey;
import com.vanstone.jupin.productdefine.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTAttributeEnumvalueDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTBrandDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTCategoryAttributeDefRelDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.productdefine.persistence.object.PDTAttributeDefDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTAttributeEnumvalueDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTBrandDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryAttributeDefRelDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.productdefine.services.CategoryHasProductsException;
import com.vanstone.jupin.productdefine.services.CategoryHasSubCategoriesException;
import com.vanstone.jupin.productdefine.services.ProductCategoryService;
import com.vanstone.jupin.productdefine.services.SizeService;
import com.vanstone.redis.RedisCallbackWithoutResult;
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
	@Autowired
	private SizeService sizeService;
	@Autowired
	private PDTBrandDOMapper pdtBrandDOMapper;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addProductCategory(com.vanstone.jupin.productdefine.ProductCategory)
	 */
	@Override
	public ProductCategory addProductCategory(@NotNull final ProductCategory productCategory)
			throws CategoryHasProductsException {
		if (productCategory.getParentProductCategory() != null && productCategory.getParentProductCategory().isExistProduct()) {
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
		clearAllProductCategoriesInCache();
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
						PDTCategoryAttributeDefRelDO key = new PDTCategoryAttributeDefRelDO();
						key.setCategoryId(pdtCategoryDO.getId());
						key.setAttributeDefId(attribute.getId());
						key.setSort(Constants.SYS_DEFAULT_SORT);
						pdtCategoryAttributeDefRelDOMapper.insert(key);
					}
				}
			}
		});
		// 刷新缓冲
		clearAllProductCategoriesInCache();
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
		PDTCategoryDO model = this.pdtCategoryDOMapper.selectByPrimaryKey(id);
		if (model == null) {
			return null;
		}
		SizeTemplate sizeTemplate = null;
		if (model.getSizeTemplateId() != null) {
			sizeTemplate = sizeService.getSizeTemplate(model.getSizeTemplateId());
		}
		ProductCategory parentProductCategory = null;
		if (model.getParentId() != null) {
			PDTCategoryDO parentCategoryDO = pdtCategoryDOMapper.selectByPrimaryKey(model.getParentId());
			parentProductCategory = BeanUtil.toProductCategory(parentCategoryDO, null, null);
		}
		
		ProductCategory productCategory = BeanUtil.toProductCategory(model, parentProductCategory, sizeTemplate);
		
		//leaf categories , child categories , all child categories
		Collection<ProductCategory> leafProductCategories = this._loadLeafProductCategoriesFromDB(id);
		productCategory.addLeafProductCategories(leafProductCategories);
		
		Collection<ProductCategory> childProductCategories = this._loadChildProductCategoriesFromDB(id);
		productCategory.addChildProductCategories(childProductCategories);
		
		Collection<ProductCategory> allChildProductCategories = this._loadAllChildProductCategoriesFromDB(id);
		productCategory.addAllChildProductCategories(allChildProductCategories);
		
		Collection<ProductCategory> productCategoriesNodePath = this._loadProductCategoryNodePathFromDB(id);
		productCategory.addProductCategoriesNodePath(productCategoriesNodePath);
		
		//brands
		Collection<Integer> leafCategoryIDs = new ArrayList<Integer>();
		if (leafProductCategories != null && leafProductCategories.size() >0) {
			for (ProductCategory pc : leafProductCategories) {
				leafCategoryIDs.add(pc.getId());
			}
			productCategory.addBrands(this._loadBrandsByCategoryIDsFromDB(leafCategoryIDs.toArray(new Integer[leafCategoryIDs.size()])));
		}
		return productCategory;
	}
	
	private Collection<ProductCategory> _loadLeafProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategory> leafCategories = new ArrayList<ProductCategory>();
		for (PDTCategoryDO m : models) {
			if (m.getLeafable().equals(BoolUtil.parseBoolean(true))) {
				leafCategories.add(BeanUtil.toProductCategory(m, null, null));
				continue;
			}
			leafCategories.addAll(_loadLeafProductCategoriesFromDB(m.getId()));
		}
		return leafCategories;
	}
	
	private Collection<ProductCategory> _loadChildProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategory> pcs = new ArrayList<ProductCategory>();
		for (PDTCategoryDO m : models) {
			pcs.add(BeanUtil.toProductCategory(m, null, null));
		}
		return pcs;
	}
	
	private Collection<ProductCategory> _loadAllChildProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategory> childCategories = new ArrayList<ProductCategory>();
		for (PDTCategoryDO m : models) {
			childCategories.add(BeanUtil.toProductCategory(m, null, null));
			if (m.getLeafable().equals(BoolUtil.parseBoolean(true))) {
				continue;
			}
			childCategories.addAll(_loadAllChildProductCategoriesFromDB(m.getId()));
		}
		return childCategories;
	}
	
	private Collection<ProductCategory> _loadProductCategoryNodePathFromDB(int categoryID) {
		PDTCategoryDO categoryDO = this.pdtCategoryDOMapper.selectByPrimaryKey(categoryID);
		if (categoryDO == null) {
			throw new IllegalArgumentException();
		}
		Collection<ProductCategory> pcs = new ArrayList<ProductCategory>();
		pcs.add(BeanUtil.toProductCategory(categoryDO, null, null));
		Integer parentID = categoryDO.getParentId();
		while (parentID != null) {
			PDTCategoryDO tempModel = this.pdtCategoryDOMapper.selectByPrimaryKey(parentID);
			if (tempModel == null) {
				throw new IllegalArgumentException();
			}
			pcs.add(BeanUtil.toProductCategory(tempModel, null, null));
			parentID = tempModel.getParentId();
		}
		ProductCategory[] tempArray = pcs.toArray(new ProductCategory[pcs.size()]);
		CollectionUtils.reverseArray(tempArray);
		pcs.clear();
		Collections.addAll(pcs, tempArray);
		return pcs;
	}
	
	/**
	 * 从DB中直接获取Brand列表
	 * @param categoryID
	 * @return
	 */
	private Collection<Brand> _loadBrandsByCategoryIDsFromDB(Integer[] categoryIDs) {
		List<PDTBrandDO> models = this.pdtBrandDOMapper.selectByCategoryIDs(categoryIDs);
		if (models == null) {
			return null;
		}
		Collection<Brand> brands = new ArrayList<Brand>();
		for (PDTBrandDO model : models) {
			brands.add(BeanUtil.toBrand(model));
		}
		return brands;
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
	public ProductCategory updateBaseProductCategoryInfo(final int id, final String categoryName, final String description, final String categoryBindPage, final String formTemplate, final Integer sort) {
		this.getProductCategoryDetailAndValidate(id);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateProductCategoryBaseInfo(categoryName, description, categoryBindPage, sort, formTemplate, id);
			}
		});
		return this.getProductCategoryDetail(id);
	}
	
	@Override
	public ProductCategory updateParentProductCategory(final int id, final Integer parentCategoryID) {
		final ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(id);
		final ProductCategory oldParentProductCategory = loadProductCategory.getParentProductCategory();
		final ProductCategory loadParentProductCategory = parentCategoryID != null ? this.getProductCategoryDetailAndValidate(parentCategoryID) : null;
		
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				//更新自己的ParentID
				pdtCategoryDOMapper.updateParentCategoryID(id, parentCategoryID);
				//更新目标category
				if (loadParentProductCategory != null && loadParentProductCategory.isLeafable()) {
					pdtCategoryDOMapper.updateLeafable(parentCategoryID, BoolUtil.parseBoolean(false));
				}
				//更新原始父category
				if (oldParentProductCategory != null) {
					List<PDTCategoryDO> pdtCategoryDOs = pdtCategoryDOMapper.selectByParentID(oldParentProductCategory.getId());
					if (pdtCategoryDOs == null || pdtCategoryDOs.size() <=0) {
						pdtCategoryDOMapper.updateLeafable(oldParentProductCategory.getId(), BoolUtil.parseBoolean(true));
					}
				}
			}
		});
		return this.getProductCategoryDetail(id);
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
				pdtCategoryDOMapper.updateCoverImage(id, coverImage.getWeedFile().getFileid(), coverImage.getWeedFile().getExtName(), coverImage.getWidth(), coverImage.getHeight());
			}
		});
		this.clearAllProductCategoriesInCache();
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
		this.clearAllProductCategoriesInCache();
		return this.getProductCategoryDetail(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * deleteProductCategory(int)
	 */
	@Override
	public void deleteProductCategory(final int id) throws CategoryHasProductsException, CategoryHasSubCategoriesException {
		this.getProductCategoryDetailAndValidate(id);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.deleteByPrimaryKey(id);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addAttributeToProductCategory(int, java.util.Collection)
	 */
	@Override
	public void addAttributesToProductCategory(final int productCategoryID, final Collection<AbstractAttribute> attributes) throws CategoryHasSubCategoriesException, ObjectDuplicateException {
		if (attributesExistInProductCategory(productCategoryID, attributes)) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				for (AbstractAttribute attribute : attributes) {
					PDTCategoryAttributeDefRelDO relDO = new PDTCategoryAttributeDefRelDO();
					relDO.setAttributeDefId(attribute.getId());
					relDO.setCategoryId(productCategoryID);
					relDO.setSort(Constants.SYS_DEFAULT_SORT);
					pdtCategoryAttributeDefRelDOMapper.insert(relDO);
				}
			}
		});
	}
	
	@Override
	public void addAttributeToProductCategory(final int productCategoryID, final AbstractAttribute attribute) throws CategoryHasSubCategoriesException, ObjectDuplicateException {
		if (attributeExistInProductCategory(productCategoryID, attribute)) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryAttributeDefRelDO relDO = new PDTCategoryAttributeDefRelDO();
				relDO.setAttributeDefId(attribute.getId());
				relDO.setCategoryId(productCategoryID);
				relDO.setSort(Constants.SYS_DEFAULT_SORT);
				pdtCategoryAttributeDefRelDOMapper.insert(relDO);
			}
		});
	}
	
	@Override
	public boolean attributeExistInProductCategory(int productCategoryID, AbstractAttribute attribute) {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
		Collection<AbstractAttribute> targetAttributes = productCategory.getAttributes();
		if (targetAttributes == null || targetAttributes.size() <=0) {
			return true;
		}
		for (AbstractAttribute attr : targetAttributes) {
			if (attr.getId().equals(attribute.getId())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean attributesExistInProductCategory(int productCategoryID, Collection<AbstractAttribute> attributes) {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
		Collection<AbstractAttribute> targetAttributes = productCategory.getAttributes();
		if (targetAttributes == null || targetAttributes.size() <=0) {
			return true;
		}
		for (AbstractAttribute attr : attributes) {
			for (AbstractAttribute targetAttribute : targetAttributes) {
				if (targetAttribute.getId().equals(attr.getId())) {
					return false;
				}
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * deleteAttributeInProductCategory(int, int)
	 */
	@Override
	public void deleteAttributeInProductCategory(final ProductCategory productCategory, final AbstractAttribute attribute) throws CategoryHasProductsException {
		if (!attributeExistInProductCategory(productCategory.getId(), attribute)) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryAttributeDefRelDOMapper.deleteByProductCategoryID_AttributeID(productCategory.getId(), attribute.getId());
			}
		});
		clearAllProductCategoriesInCache();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * forceDeleteProductCategory(int)
	 */
	@Override
	public void forceDeleteProductCategory(int id) {
		//暂时不实现，版本升级后在实现功能
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshAllProductCategories()
	 */
	@Override
	public void clearAllProductCategoriesInCache() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys("keys " + ProductDefineCacheKey.PRODUCT_CATEGORY_CACHE_PREFIX + "*");
				if (keies == null || keies.size() <=0) {
					jedis.del(keies.toArray(new String[keies.size()]));
				}
			}
		});
		LOG.info("Clear All Product Categories in cache.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshProductCategory(int)
	 */
	@Override
	public void refreshProductCategory(int id) {
		
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
	public void updateExistProductState(final int productCategoryID, final boolean existProduct) {
		
		this.getProductCategoryDetailAndValidate(productCategoryID);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryDO model = new PDTCategoryDO();
				model.setId(productCategoryID);
				model.setExistProduct(BoolUtil.parseBoolean(existProduct));
				pdtCategoryDOMapper.updateByPrimaryKeySelective(model);
			}
		});
		clearAllProductCategoriesInCache();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getProductCategories(java.lang.Integer)
	 */
	@Override
	public Collection<ProductCategory> getProductCategories(Integer parentID) {
		
		return null;
	}
	
	@Override
	public Attr4Text addAttr4Text(final Attr4Text attr4Text) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(attr4Text.getAttributeName());
				model.setAttributeDescription(attr4Text.getAttributeDescription());
				model.setAttributeType(attr4Text.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(attr4Text.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(attr4Text.isRequiredable()));
				pdtAttributeDefDOMapper.insert(model);
				attr4Text.setId(model.getId());
			}
		});
		return attr4Text;
	}

	@Override
	public Attr4Enum addAttr4Enum(final Attr4Enum attr4Enum, final Set<String> objectValues) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(attr4Enum.getAttributeName());
				model.setAttributeDescription(attr4Enum.getAttributeDescription());
				model.setAttributeType(attr4Enum.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(attr4Enum.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(attr4Enum.isRequiredable()));
				model.setMultiselectable(BoolUtil.parseBoolean(attr4Enum.isMultiselectable()));
				model.setSearchable(BoolUtil.parseBoolean(attr4Enum.isSearchable()));
				pdtAttributeDefDOMapper.insert(model);
				attr4Enum.setId(model.getId());
				int index = 1;
				for (String value : objectValues) {
					PDTAttributeEnumvalueDO valueDo = new PDTAttributeEnumvalueDO();
					valueDo.setAttributeDefId(model.getId());
					valueDo.setObjecttext(value);
					valueDo.setSort(index);
					valueDo.setValueState(AttributeState.Active.getCode());
					pdtAttributeEnumvalueDOMapper.insert(valueDo);
					attr4Enum.putValue(valueDo.getId(), value);
				}
			}
		});
		return attr4Enum;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateAttr4Text(com.vanstone.jupin.productdefine.attr.Attr4Text)
	 */
	@Override
	public Attr4Text updateAttr4Text(final Attr4Text attr4Text) {
		AbstractAttribute attribute = this.getAttribute(attr4Text.getId());
		if (attribute  == null || !attribute.getAttributeType().equals(AttributeType.Text)) {
			throw new IllegalArgumentException();
		}
		final Attr4Text loadAttr4Text = (Attr4Text)attribute;
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadAttr4Text.setAttributeDescription(attr4Text.getAttributeDescription());
				loadAttr4Text.setAttributeName(attr4Text.getAttributeName());
				loadAttr4Text.setListshowable(attr4Text.isListshowable());
				loadAttr4Text.setRequiredable(attr4Text.isRequiredable());
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setAttributeName(loadAttr4Text.getAttributeName());
				model.setAttributeDescription(loadAttr4Text.getAttributeDescription());
				model.setAttributeType(loadAttr4Text.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(loadAttr4Text.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(loadAttr4Text.isRequiredable()));
				pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		return loadAttr4Text;
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
		PDTAttributeDefDO model = this.pdtAttributeDefDOMapper.selectByPrimaryKey(id);
		if(model.getAttributeType().equals(AttributeType.Text.getCode())) {
			//文本类型
			Attr4Text attr4Text = new Attr4Text();
			attr4Text.setAttributeDescription(model.getAttributeDescription());
			attr4Text.setAttributeName(model.getAttributeName());
			attr4Text.setId(model.getId());
			attr4Text.setListshowable(BoolUtil.parseInt(model.getListshowable()));
			attr4Text.setRequiredable(BoolUtil.parseInt(model.getRequiredable()));
			return attr4Text;
		}else {
			//枚举类型
			Attr4Enum attr4Enum = new Attr4Enum();
			attr4Enum.setAttributeDescription(model.getAttributeDescription());
			attr4Enum.setAttributeName(model.getAttributeName());
			attr4Enum.setId(model.getId());
			attr4Enum.setListshowable(BoolUtil.parseInt(model.getListshowable()));
			attr4Enum.setRequiredable(BoolUtil.parseInt(model.getRequiredable()));
			attr4Enum.setMultiselectable(BoolUtil.parseInt(model.getMultiselectable()));
			attr4Enum.setSearchable(BoolUtil.parseInt(model.getSearchable()));
			
			List<PDTAttributeEnumvalueDO> pdtAttributeEnumvalueDOs = this.pdtAttributeEnumvalueDOMapper.selectByAttributeDefId(id);
			if (pdtAttributeEnumvalueDOs == null || pdtAttributeEnumvalueDOs.size() <=0) {
				LOG.error("Attribute Def ID {} don't contain enumvalue", id);
				throw new IllegalArgumentException("");
			}
			for (PDTAttributeEnumvalueDO valueDo : pdtAttributeEnumvalueDOs) {
				attr4Enum.putValue(valueDo.getId(), valueDo.getObjecttext());
			}
			return attr4Enum;
		}
	}
	
	@Override
	public Attr4Enum appendAttr4EnumValue(final Attr4Enum attr4Enum, final String objectValue, final Integer sort) throws ObjectDuplicateException {
		MyAssert4Business.objectInitialized(attr4Enum);
		PDTAttributeEnumvalueDO tempModel = this.pdtAttributeEnumvalueDOMapper.selectByAttributeDefId_AttributeName(attr4Enum.getId(), objectValue);
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeEnumvalueDO model = new PDTAttributeEnumvalueDO();
				model.setAttributeDefId(attr4Enum.getId());
				model.setObjecttext(objectValue);
				model.setValueState(AttributeState.Active.getCode());
				if (sort == null) {
					Integer max = pdtAttributeEnumvalueDOMapper.selectMaxSortValueByAttributeDefId(attr4Enum.getId());
					if (max == null) {
						max = Constants.SYS_DEFAULT_SORT;
					}
					model.setSort(max);
				}else{
					model.setSort(sort);
				}
				pdtAttributeEnumvalueDOMapper.insert(model);
			}
		});
		return (Attr4Enum)this.refreshAttribute(attr4Enum.getId());
	}
	
	@Override
	public Attr4Enum updateAttr4EnumValue(final Attr4EnumValue attr4EnumValue) throws ObjectDuplicateException {
		PDTAttributeEnumvalueDO tempModel = this.pdtAttributeEnumvalueDOMapper.selectByAttributeDefId_AttributeName_NotSelf(attr4EnumValue.getAttr4Enum().getId()	, attr4EnumValue.getAttr4Enum().getAttributeName(), attr4EnumValue.getId());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeEnumvalueDO model = new PDTAttributeEnumvalueDO();
				model.setId(attr4EnumValue.getId());
				model.setObjecttext(attr4EnumValue.getObjectText());
				model.setSort(attr4EnumValue.getSort());
				pdtAttributeEnumvalueDOMapper.updateByPrimaryKeySelective(model);
			}
		});
		return null;
	}
	
	@Override
	public AbstractAttribute refreshAttribute(int attributeId) {
		final String key = ProductDefineCacheKey.getAttributeCacheKey(attributeId);
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				jedis.del(key);
			}
		});
		LOG.info("Refresh Attribute {}", attributeId);
		return this.getAttribute(attributeId);
	}
	
	@Override
	public Attr4EnumValue getAttr4EnumValue(final int enumValueID) {
		final String key = ProductDefineCacheKey.getEnumAttributeValueCacheKey(enumValueID);
		final ServiceUtil<Attr4EnumValue, Integer> serviceUtil = new ServiceUtil<Attr4EnumValue, Integer>();
		Attr4EnumValue loadAttr4EnumValue = serviceUtil.getObjectFromRedisByKey(this.redisTemplate, JupinRedisRef.Jupin_Core, Attr4EnumValue.class, key);
		if (loadAttr4EnumValue != null) {
			return loadAttr4EnumValue;
		}
		//从DB中获取
		return ZKUtil.executeMutex(Constants.buildZKLockMutexNodePath(ProductDefineCacheKey.getEnumAttributeValueCacheKey(enumValueID)), new InterProcessMutexCallback<Attr4EnumValue>() {
			@Override
			public Attr4EnumValue doInAcquireMutex(CuratorFramework curatorFramework) {
				//获取到锁，执行操作
				PDTAttributeEnumvalueDO valueModel = pdtAttributeEnumvalueDOMapper.selectByPrimaryKey(enumValueID);
				if (valueModel == null) {
					return null;
				}
				Attr4Enum attr4Enum = (Attr4Enum)getAttribute(enumValueID);
				Attr4EnumValue attr4EnumValue = new Attr4EnumValue();
				attr4EnumValue.setAttr4Enum(attr4Enum);
				attr4EnumValue.setId(valueModel.getId());
				attr4EnumValue.setObjectText(valueModel.getObjecttext());
				attr4EnumValue.setSort(valueModel.getSort());
				
				serviceUtil.setObjectToRedis(redisTemplate, JupinRedisRef.Jupin_Core, key, attr4EnumValue);
				return attr4EnumValue;
			}
			@Override
			public Attr4EnumValue doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getAttr4EnumValue(enumValueID);
			}
		});
	}
	
	@Override
	public void deleteAttr4EnumValue(final int enumValueId) {
		final Attr4EnumValue attr4EnumValue = this.getAttr4EnumValue(enumValueId);
		if (attr4EnumValue == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtAttributeEnumvalueDOMapper.deleteByPrimaryKey(enumValueId);
				if (attr4EnumValue.getAttr4Enum().getValues().size() == 1) {
					pdtAttributeDefDOMapper.deleteByPrimaryKey(attr4EnumValue.getAttr4Enum().getId());
				}
			}
		});
	}
	
	@Override
	public Attr4Enum updateBaseAttr4Enum(final Attr4Enum attr4Enum) {
		final Attr4Enum loadAttr4Enum = (Attr4Enum)this.getAttribute(attr4Enum.getId());
		if (loadAttr4Enum == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadAttr4Enum.setId(attr4Enum.getId());
				loadAttr4Enum.setAttributeName(attr4Enum.getAttributeName());
				loadAttr4Enum.setAttributeDescription(attr4Enum.getAttributeDescription());
				loadAttr4Enum.setListshowable(attr4Enum.isListshowable());
				loadAttr4Enum.setRequiredable(attr4Enum.isRequiredable());
				loadAttr4Enum.setMultiselectable(attr4Enum.isMultiselectable());
				loadAttr4Enum.setSearchable(attr4Enum.isSearchable());
				PDTAttributeDefDO model = new PDTAttributeDefDO();
				model.setId(loadAttr4Enum.getId());
				model.setAttributeName(loadAttr4Enum.getAttributeName());
				model.setAttributeDescription(loadAttr4Enum.getAttributeDescription());
				model.setAttributeType(loadAttr4Enum.getAttributeType().getCode());
				model.setListshowable(BoolUtil.parseBoolean(loadAttr4Enum.isListshowable()));
				model.setRequiredable(BoolUtil.parseBoolean(loadAttr4Enum.isRequiredable()));
				model.setMultiselectable(BoolUtil.parseBoolean(loadAttr4Enum.isMultiselectable()));
				model.setSearchable(BoolUtil.parseBoolean(loadAttr4Enum.isSearchable()));
				pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		return (Attr4Enum)this.refreshAttribute(loadAttr4Enum.getId());
	}
	
	@Override
	public Map<Integer, AbstractAttribute> getAttributesByIDsMap(Collection<Integer> ids) {
		return null;
	}
	
	@Override
	public Collection<Brand> getBrands(ProductCategory productCategory) {
		return null;
	}
	
}
