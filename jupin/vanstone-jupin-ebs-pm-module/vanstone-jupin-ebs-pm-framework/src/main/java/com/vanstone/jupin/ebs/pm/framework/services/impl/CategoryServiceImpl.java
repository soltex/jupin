/**
 * 
 */
package com.vanstone.jupin.ebs.pm.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ebs.pm.GsonCreatorOfPD;
import com.vanstone.jupin.ebs.pm.PDCache;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTAttributeEnumvalueDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTBrandDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTCategoryAttributeDefRelDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTBrandDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTCategoryAttributeDefRelDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.ebs.pm.framework.serializer.PDAttributeSerializer;
import com.vanstone.jupin.ebs.pm.productdefine.Brand;
import com.vanstone.jupin.ebs.pm.productdefine.ProductCategory;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AbstractAttribute;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Enum;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ebs.pm.productdefine.services.AttributeService;
import com.vanstone.jupin.ebs.pm.productdefine.services.CategoryHasChildCategoriesException;
import com.vanstone.jupin.ebs.pm.productdefine.services.CategoryService;
import com.vanstone.jupin.ebs.pm.productdefine.services.DefineCommonService;
import com.vanstone.jupin.ebs.pm.productdefine.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ebs.pm.productdefine.services.SizeService;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("categoryService")
@Validated
public class CategoryServiceImpl extends DefaultBusinessService implements CategoryService {
	
	/***/
	private static final long serialVersionUID = -390257932925884113L;

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PDTCategoryDOMapper pdtCategoryDOMapper;
	@Autowired
	private PDTCategoryAttributeDefRelDOMapper pdtCategoryAttributeDefRelDOMapper;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private PDTBrandDOMapper pdtBrandDOMapper;
	@Autowired
	private DefineCommonService defineCommonService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private PDTAttributeDefDOMapper pdtAttributeDefDOMapper;
	@Autowired
	private PDTAttributeEnumvalueDOMapper pdtAttributeEnumvalueDOMapper;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addProductCategory(com.vanstone.jupin.productdefine.ProductCategory)
	 */
	@Override
	public ProductCategory addProductCategory(@NotNull final ProductCategory productCategory)
			throws ExistProductsNotAllowWriteException {
		if (productCategory.getParentProductCategory() != null && productCategory.getParentProductCategory().isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
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
		this.defineCommonService.clearCategoryDefineCache();
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
			final Collection<AbstractAttribute> attributes) throws ExistProductsNotAllowWriteException {
		if (productCategory.getParentProductCategory() != null
				&& productCategory.getParentProductCategory().isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
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
		this.defineCommonService.clearCategoryDefineCache();
		return this.getProductCategoryDetail(productCategory.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * getProductCategoryDetail(int)
	 */
	@Override
	public ProductCategory getProductCategoryDetail(final int id) {
		final String key = PDCache.getCategoryKey(id);
		
		ProductCategory loadProductCategory = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<ProductCategory>() {
			@Override
			public ProductCategory doInRedis(Jedis jedis) {
				String value = jedis.get(key);
				if (value == null || value.equals("")) {
					return null;
				}
				Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class	, new PDAttributeSerializer()).disableHtmlEscaping().create();
				ProductCategory pc = gson.fromJson(value, ProductCategory.class);
				return pc;
			}
		});
		
		if (loadProductCategory != null) {
			return loadProductCategory;
		}
		
		return ZKUtil.executeMutex(key, new InterProcessMutexCallback<ProductCategory>() {

			@Override
			public ProductCategory doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTCategoryDO model = pdtCategoryDOMapper.selectByPrimaryKey(id);
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
				
				final ProductCategory productCategory = BeanUtil.toProductCategory(model, parentProductCategory, sizeTemplate);
				
				//leaf categories , child categories , all child categories
				Collection<ProductCategory> leafProductCategories = _loadLeafProductCategoriesFromDB(id);
				productCategory.addLeafProductCategories(leafProductCategories);
				
				Collection<ProductCategory> childProductCategories = _loadChildProductCategoriesFromDB(id);
				productCategory.addChildProductCategories(childProductCategories);
				
				Collection<ProductCategory> allChildProductCategories = _loadAllChildProductCategoriesFromDB(id);
				productCategory.addAllChildProductCategories(allChildProductCategories);
				
				Collection<ProductCategory> productCategoriesNodePath = _loadProductCategoryNodePathFromDB(id);
				productCategory.addProductCategoriesNodePath(productCategoriesNodePath);
				
				//allCategoryIDs
				Collection<Integer> allCategoryIDs = new ArrayList<Integer>();
				if (allChildProductCategories != null && allChildProductCategories.size() >0) {
					for (ProductCategory pc : allChildProductCategories) {
						allCategoryIDs.add(pc.getId());
					}
				}
				allCategoryIDs.add(id);
				//brands
				productCategory.addBrands(_loadBrandsByCategoryIDsFromDB(allCategoryIDs.toArray(new Integer[allCategoryIDs.size()])));
				//attributes
				if (allCategoryIDs != null && allCategoryIDs.size() >0) {
					Collection<AbstractAttribute> attributes = _loadAttributesByCategoryIDsFromDB(allCategoryIDs.toArray(new Integer[allCategoryIDs.size()]));
					productCategory.addAttributes(attributes);
				}
				//currents attributes
				Collection<AbstractAttribute> currentAttributes = _loadAttributesByCategoryID(id);
				if (currentAttributes != null && currentAttributes.size() >0) {
					productCategory.addCurrentAttributes(currentAttributes);
				}
				//search attributes
				if (allCategoryIDs != null && allCategoryIDs.size() >0) {
					Collection<AbstractAttribute> attributes = _loadSearchableAttributesFromDB(allCategoryIDs.toArray(new Integer[allCategoryIDs.size()]));
					if (attributes != null && attributes.size() >0) {
						for (AbstractAttribute attr : attributes) {
							productCategory.addSearchSearchAttribute((Attr4Enum)attr);
						}
					}
				}
				//write to redis
				redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
					@Override
					public void doInRedisWithoutResult(Jedis jedis) {
						Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class	, new PDAttributeSerializer()).disableHtmlEscaping().create();
						jedis.set(key, gson.toJson(productCategory));
					}
				});
				return productCategory;
			}
			
			@Override
			public ProductCategory doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getProductCategoryDetail(id);
			}
			
		});
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
			Collection<ProductCategory> temp_pcs = _loadLeafProductCategoriesFromDB(m.getId());
			if (temp_pcs != null && temp_pcs.size() >0) {
				leafCategories.addAll(temp_pcs);
			}
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
			Collection<ProductCategory> temp_pcs = _loadAllChildProductCategoriesFromDB(m.getId());
			if (temp_pcs != null && temp_pcs.size() >0) {
				childCategories.addAll(temp_pcs);
			}
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
	
	private Collection<AbstractAttribute> _loadSearchableAttributesFromDB(Integer[] categoryIDs) {
		List<Integer> attributeIDs = this.pdtAttributeDefDOMapper.selectIDsByCategoryIDs_Searchable(categoryIDs, true);
		Map<Integer, AbstractAttribute> dataMap = attributeService.getAttributesByIDsMap(attributeIDs);
		if (dataMap != null && dataMap.size() >0) {
			return dataMap.values();
		}
		return null;
	}
	
	/**
	 * 从DB中直接获取Attribute列表通过排序获取
	 * @param categoryIDs
	 * @return
	 */
	private Collection<AbstractAttribute> _loadAttributesByCategoryIDsFromDB(Integer[] categoryIDs) {
		List<PDTCategoryAttributeDefRelDO> models = pdtCategoryAttributeDefRelDOMapper.selectByCategoryIDs(categoryIDs);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<Integer> attributeIDs = new ArrayList<Integer>();
		for (PDTCategoryAttributeDefRelDO model : models) {
			attributeIDs.add(model.getAttributeDefId());
		}
		Map<Integer, AbstractAttribute> dataMap =attributeService.getAttributesByIDsMap(attributeIDs);
		return dataMap != null && dataMap.size() >0 ? dataMap.values() : null;
	}
	
	/**
	 * 从DB中直接获取Attribute列表获取
	 * @param categoryID
	 * @return
	 */
	private Collection<AbstractAttribute> _loadAttributesByCategoryID(int categoryID) {
		List<PDTCategoryAttributeDefRelDO> models = this.pdtCategoryAttributeDefRelDOMapper.selectByCategoryID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<Integer> attributeIDs = new ArrayList<Integer>();
		for (PDTCategoryAttributeDefRelDO model : models) {
			attributeIDs.add(model.getAttributeDefId());
		}
		Map<Integer, AbstractAttribute> dataMap = attributeService.getAttributesByIDsMap(attributeIDs);
		return dataMap != null && dataMap.size() >0 ? dataMap.values() : null;
	}
	
	@Override
	public ProductCategory getProductCategoryDetailAndValidate(int id) {
		ProductCategory productCategory = this.getProductCategoryDetail(id);
		MyAssert4Business.notNull(productCategory);
		return productCategory;
	}
	
	@Override
	public Collection<ProductCategory> getProductCategoriesOfLevel1() {
		final String key = PDCache.PRODUCT_CATEGORY_ROOT_NODE;
		Collection<ProductCategory> loadProductCategories = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Collection<ProductCategory>>() {
			@Override
			public Collection<ProductCategory> doInRedis(Jedis jedis) {
				String value = jedis.get(key);
				if (value == null || value.equals("")) {
					return null;
				}
				Gson gson = GsonCreatorOfPD.create();
				return gson.fromJson(value, new TypeToken<List<ProductCategory>>(){private static final long serialVersionUID = 5637975477897724892L;}.getType());}
		});
		if (loadProductCategories != null) {
			return loadProductCategories;
		}
		final Collection<ProductCategory> loadProductCategories1 = ZKUtil.executeMutex(key, new InterProcessMutexCallback<Collection<ProductCategory>>() {
			@Override
			public Collection<ProductCategory> doInAcquireMutex(CuratorFramework curatorFramework) {
				List<Integer> ids = pdtCategoryDOMapper.selectLevel1Category();
				if (ids == null || ids.size() <=0) {
					return null;
				}
				Collection<ProductCategory> pcs = new ArrayList<ProductCategory>();
				for (Integer id : ids) {
					pcs.add(getProductCategoryDetail(id));
				}
				return pcs;
			}
			
			@Override
			public Collection<ProductCategory> doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getProductCategoriesOfLevel1();
			}
		});
		
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Gson gson = GsonCreatorOfPD.create();
				jedis.set(key, gson.toJson(loadProductCategories1));
			}
		});
		return loadProductCategories1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateBaseProductCategoryInfo(int, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ProductCategory updateBaseProductCategoryInfo(final int id, final String categoryName, final String description, final String categoryBindPage, final String formTemplate, final Integer sort) throws ExistProductsNotAllowWriteException {
		ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(id);
		if (loadProductCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateProductCategoryBaseInfo(categoryName, description, categoryBindPage, sort, formTemplate, id);
			}
		});
		this.defineCommonService.clearProductDefineCache();
		return this.getProductCategoryDetail(id);
	}
	
	@Override
	public ProductCategory updateParentProductCategory(final int id, final Integer parentCategoryID) throws ExistProductsNotAllowWriteException{
		final ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(id);
		final ProductCategory oldParentProductCategory = loadProductCategory.getParentProductCategory();
		final ProductCategory loadParentProductCategory = parentCategoryID != null ? this.getProductCategoryDetailAndValidate(parentCategoryID) : null;
		if (loadProductCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
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
		defineCommonService.clearProductDefineCache();
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
	public ProductCategory updateProductCategoryCoverImage(final int id, final ImageBean coverImage) throws ExistProductsNotAllowWriteException {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(id);
		if (productCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateCoverImage(id, coverImage.getWeedFile().getFileid(), coverImage.getWeedFile().getExtName(), coverImage.getWidth(), coverImage.getHeight());
			}
		});
		this.defineCommonService.clearCategoryDefineCache();
		return this.getProductCategoryDetail(id);
	}

	@Override
	public ProductCategory deleteProductCategoryCoverImage(final int id) throws ExistProductsNotAllowWriteException {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(id);
		if (productCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateCoverImage(id, null, null, null, null);
			}
		});
		this.defineCommonService.clearCategoryDefineCache();
		return this.getProductCategoryDetail(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * deleteProductCategory(int)
	 */
	@Override
	public void deleteProductCategory(final int id) throws ExistProductsNotAllowWriteException, CategoryHasChildCategoriesException {
		ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(id);
		if (loadProductCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		if (loadProductCategory.getAllChildProductCategories() != null && loadProductCategory.getAllChildProductCategories().size() >0) {
			throw new CategoryHasChildCategoriesException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.deleteByPrimaryKey(id);
			}
		});
		this.defineCommonService.clearProductDefineCache();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addAttributeToProductCategory(int, java.util.Collection)
	 */
	@Override
	public void addAttributesToProductCategory(final int productCategoryID, final Collection<AbstractAttribute> attributes) throws ObjectDuplicateException {
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
	public void addAttributeToProductCategory(final int productCategoryID, final AbstractAttribute attribute) throws ObjectDuplicateException {
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
			return false;
		}
		for (AbstractAttribute attr : targetAttributes) {
			if (attr.getId().equals(attribute.getId())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean attributesExistInProductCategory(int productCategoryID, Collection<AbstractAttribute> attributes) {
		ProductCategory productCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
		Collection<AbstractAttribute> targetAttributes = productCategory.getAttributes();
		if (targetAttributes == null || targetAttributes.size() <=0) {
			return false;
		}
		for (AbstractAttribute attr : attributes) {
			for (AbstractAttribute targetAttribute : targetAttributes) {
				if (targetAttribute.getId().equals(attr.getId())) {
					return true;
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
	public void deleteAttributeInProductCategory(final ProductCategory productCategory, final AbstractAttribute attribute) throws ExistProductsNotAllowWriteException {
		if (!attributeExistInProductCategory(productCategory.getId(), attribute)) {
			throw new IllegalArgumentException();
		}
		if (productCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryAttributeDefRelDOMapper.deleteByProductCategoryID_AttributeID(productCategory.getId(), attribute.getId());
			}
		});
		this.defineCommonService.clearCategoryDefineCache();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * refreshProductCategory(int)
	 */
	@Override
	public void refreshProductCategory(final int id) {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				jedis.del(PDCache.getCategoryKey(id));
			}
		});
		this.getProductCategoryDetailAndValidate(id);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * updateExistProductState(int, boolean)
	 */
	@Override
	public void updateExistProductState(final int productCategoryID, final boolean existProduct) {
		ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
		if (!loadProductCategory.isExistProduct()) {
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					PDTCategoryDO model = new PDTCategoryDO();
					model.setId(productCategoryID);
					model.setExistProduct(BoolUtil.parseBoolean(existProduct));
					pdtCategoryDOMapper.updateByPrimaryKeySelective(model);
				}
			});
			this.defineCommonService.clearCategoryDefineCache();
		}
	}
	
	@Override
	public Collection<ProductCategory> getProductCategories(String key, int offset, int limit) {
		List<Integer> ids = this.pdtCategoryDOMapper.selectByKey(key, new RowBounds(offset, limit));
		if (ids == null || ids.size() <=0) {
			return null;
		}
		Collection<ProductCategory> pcs = new ArrayList<ProductCategory>();
		for (Integer id : ids) {
			pcs.add(getProductCategoryDetail(id));
		}
		return pcs;
	}
	
	@Override
	public int getTotalProductCategories(String key) {
		return this.pdtCategoryDOMapper.selectTotalByKey(key);
	}
	
	@Override
	public boolean validateAllowUDOperateCategory(int categoryID) {
		ProductCategory loadProductCategory = this.getProductCategoryDetailAndValidate(categoryID);
		Collection<ProductCategory> allChildProductCategories = loadProductCategory.getAllChildProductCategories();
		if (allChildProductCategories !=null && allChildProductCategories.size() >0) {
			for (ProductCategory pc : allChildProductCategories) {
				if (pc.isExistProduct()) {
					return false;
				}
			}
		}
		return !loadProductCategory.isExistProduct();
	}
	
}
