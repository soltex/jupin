/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import com.vanstone.jupin.common.cache.JupinRedisRef;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ecs.product.GsonCreatorOfPD;
import com.vanstone.jupin.ecs.product.PDCache;
import com.vanstone.jupin.ecs.product.define.BasicProductCategory;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.AttributeService;
import com.vanstone.jupin.ecs.product.define.services.CategoryHasChildCategoriesException;
import com.vanstone.jupin.ecs.product.define.services.CategoryMustLeafNodeException;
import com.vanstone.jupin.ecs.product.define.services.ProductCategoryService;
import com.vanstone.jupin.ecs.product.define.services.DefineCommonService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.define.services.SizeService;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTAttributeEnumvalueDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTBrandDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTCategoryAttributeDefRelDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTCategoryBrandRelDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTBrandDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryAttributeDefRelDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryBrandRelDOKey;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.ecs.product.framework.serializer.PDAttributeSerializer;
import com.vanstone.redis.RedisCallback;
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
	@Autowired
	private PDTCategoryBrandRelDOMapper pdtCategoryBrandRelDOMapper;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.productdefine.services.ProductCategoryService#
	 * addProductCategory(com.vanstone.jupin.productdefine.ProductCategory)
	 */
	@Override
	public ProductCategoryDetail addProductCategory(@NotNull final ProductCategoryDetail productCategory)
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
	public ProductCategoryDetail addProductCategory(@NotNull final ProductCategoryDetail productCategory,
			final Collection<AbstractAttribute> attributes) throws ExistProductsNotAllowWriteException {
		if (productCategory.getParentProductCategory() != null
				&& productCategory.getParentProductCategory().isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		// 写入品类信息并更新父品类信息
		productCategory.setLeafable(true);
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
	public ProductCategoryDetail getProductCategoryDetail(final int id) {
		final String key = PDCache.getCategoryKey(id);
		
		ProductCategoryDetail loadProductCategory = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<ProductCategoryDetail>() {
			@Override
			public ProductCategoryDetail doInRedis(Jedis jedis) {
				String value = jedis.get(key);
				if (value == null || value.equals("")) {
					return null;
				}
				Gson gson = new GsonBuilder().registerTypeAdapter(AbstractAttribute.class	, new PDAttributeSerializer()).disableHtmlEscaping().create();
				ProductCategoryDetail pc = gson.fromJson(value, ProductCategoryDetail.class);
				return pc;
			}
		});
		
		if (loadProductCategory != null) {
			return loadProductCategory;
		}
		
		return ZKUtil.executeMutex(key, new InterProcessMutexCallback<ProductCategoryDetail>() {

			@Override
			public ProductCategoryDetail doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTCategoryDO model = pdtCategoryDOMapper.selectByPrimaryKey(id);
				if (model == null) {
					return null;
				}
				SizeTemplate sizeTemplate = null;
				if (model.getSizeTemplateId() != null) {
					sizeTemplate = sizeService.getSizeTemplate(model.getSizeTemplateId());
				}
				ProductCategoryDetail parentProductCategory = null;
				if (model.getParentId() != null) {
					PDTCategoryDO parentCategoryDO = pdtCategoryDOMapper.selectByPrimaryKey(model.getParentId());
					parentProductCategory = BeanUtil.toProductCategory(parentCategoryDO, null, null);
				}
				
				final ProductCategoryDetail productCategory = BeanUtil.toProductCategory(model, parentProductCategory, sizeTemplate);
				
				//leaf categories , child categories , all child categories
				Collection<ProductCategoryDetail> leafProductCategories = _loadLeafProductCategoriesFromDB(id);
				productCategory.addLeafProductCategories(leafProductCategories);
				
				Collection<ProductCategoryDetail> childProductCategories = _loadChildProductCategoriesFromDB(id);
				productCategory.addChildProductCategories(childProductCategories);
				
				Collection<ProductCategoryDetail> allChildProductCategories = _loadAllChildProductCategoriesFromDB(id);
				productCategory.addAllChildProductCategories(allChildProductCategories);
				
				Collection<ProductCategoryDetail> productCategoriesNodePath = _loadProductCategoryNodePathFromDB(id);
				productCategory.addProductCategoriesNodePath(productCategoriesNodePath);
				
				//allCategoryIDs
				Collection<Integer> allCategoryIDs = new ArrayList<Integer>();
				if (allChildProductCategories != null && allChildProductCategories.size() >0) {
					for (ProductCategoryDetail pc : allChildProductCategories) {
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
			public ProductCategoryDetail doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getProductCategoryDetail(id);
			}
			
		});
	}
	
	private Collection<ProductCategoryDetail> _loadLeafProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategoryDetail> leafCategories = new ArrayList<ProductCategoryDetail>();
		for (PDTCategoryDO m : models) {
			if (m.getLeafable().equals(BoolUtil.parseBoolean(true))) {
				leafCategories.add(BeanUtil.toProductCategory(m, null, null));
				continue;
			}
			Collection<ProductCategoryDetail> temp_pcs = _loadLeafProductCategoriesFromDB(m.getId());
			if (temp_pcs != null && temp_pcs.size() >0) {
				leafCategories.addAll(temp_pcs);
			}
		}
		return leafCategories;
	}
	
	private Collection<ProductCategoryDetail> _loadChildProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategoryDetail> pcs = new ArrayList<ProductCategoryDetail>();
		for (PDTCategoryDO m : models) {
			pcs.add(BeanUtil.toProductCategory(m, null, null));
		}
		return pcs;
	}
	
	private Collection<ProductCategoryDetail> _loadAllChildProductCategoriesFromDB(int categoryID) {
		List<PDTCategoryDO> models = this.pdtCategoryDOMapper.selectByParentID(categoryID);
		if (models == null || models.size() <=0) {
			return null;
		}
		Collection<ProductCategoryDetail> childCategories = new ArrayList<ProductCategoryDetail>();
		for (PDTCategoryDO m : models) {
			childCategories.add(BeanUtil.toProductCategory(m, null, null));
			if (m.getLeafable().equals(BoolUtil.parseBoolean(true))) {
				continue;
			}
			Collection<ProductCategoryDetail> temp_pcs = _loadAllChildProductCategoriesFromDB(m.getId());
			if (temp_pcs != null && temp_pcs.size() >0) {
				childCategories.addAll(temp_pcs);
			}
		}
		return childCategories;
	}
	
	private Collection<ProductCategoryDetail> _loadProductCategoryNodePathFromDB(int categoryID) {
		PDTCategoryDO categoryDO = this.pdtCategoryDOMapper.selectByPrimaryKey(categoryID);
		if (categoryDO == null) {
			throw new IllegalArgumentException();
		}
		Collection<ProductCategoryDetail> pcs = new ArrayList<ProductCategoryDetail>();
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
		ProductCategoryDetail[] tempArray = pcs.toArray(new ProductCategoryDetail[pcs.size()]);
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
	public ProductCategoryDetail getProductCategoryDetailAndValidate(int id) {
		ProductCategoryDetail productCategory = this.getProductCategoryDetail(id);
		MyAssert4Business.notNull(productCategory);
		return productCategory;
	}
	
	@Override
	public Collection<ProductCategoryDetail> getProductCategoriesOfLevel1() {
		final String key = PDCache.PRODUCT_CATEGORY_ROOT_NODE;
		Collection<ProductCategoryDetail> loadProductCategories = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Collection<ProductCategoryDetail>>() {
			@Override
			public Collection<ProductCategoryDetail> doInRedis(Jedis jedis) {
				String value = jedis.get(key);
				if (value == null || value.equals("")) {
					return null;
				}
				Gson gson = GsonCreatorOfPD.create();
				return gson.fromJson(value, new TypeToken<List<ProductCategoryDetail>>(){private static final long serialVersionUID = 5637975477897724892L;}.getType());}
		});
		if (loadProductCategories != null) {
			return loadProductCategories;
		}
		final Collection<ProductCategoryDetail> loadProductCategories1 = ZKUtil.executeMutex(key, new InterProcessMutexCallback<Collection<ProductCategoryDetail>>() {
			@Override
			public Collection<ProductCategoryDetail> doInAcquireMutex(CuratorFramework curatorFramework) {
				List<Integer> ids = pdtCategoryDOMapper.selectLevel1Category();
				if (ids == null || ids.size() <=0) {
					return null;
				}
				Collection<ProductCategoryDetail> pcs = new ArrayList<ProductCategoryDetail>();
				for (Integer id : ids) {
					pcs.add(getProductCategoryDetail(id));
				}
				return pcs;
			}
			
			@Override
			public Collection<ProductCategoryDetail> doInNotAcquireMutex(CuratorFramework curatorFramework) {
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
	public ProductCategoryDetail updateBaseProductCategoryInfo(final int id, final String categoryName, final String description, final String categoryBindPage, final String formTemplate, final Integer sort) throws ExistProductsNotAllowWriteException {
		ProductCategoryDetail loadProductCategory = this.getProductCategoryDetailAndValidate(id);
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
	public ProductCategoryDetail updateParentProductCategory(final int id, final Integer parentCategoryID) throws ExistProductsNotAllowWriteException{
		final ProductCategoryDetail loadProductCategory = this.getProductCategoryDetailAndValidate(id);
		final ProductCategoryDetail oldParentProductCategory = loadProductCategory.getParentProductCategory();
		final ProductCategoryDetail loadParentProductCategory = parentCategoryID != null ? this.getProductCategoryDetailAndValidate(parentCategoryID) : null;
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
	public ProductCategoryDetail updateProductCategoryCoverImage(final int id, final ImageBean coverImage) throws ExistProductsNotAllowWriteException {
		ProductCategoryDetail productCategory = this.getProductCategoryDetailAndValidate(id);
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
	public ProductCategoryDetail deleteProductCategoryCoverImage(final int id) throws ExistProductsNotAllowWriteException {
		ProductCategoryDetail productCategory = this.getProductCategoryDetailAndValidate(id);
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
		ProductCategoryDetail loadProductCategory = this.getProductCategoryDetailAndValidate(id);
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
		ProductCategoryDetail productCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
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
		ProductCategoryDetail productCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
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
	public void deleteAttributeInProductCategory(final ProductCategoryDetail productCategory, final AbstractAttribute attribute) throws ExistProductsNotAllowWriteException {
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
		ProductCategoryDetail loadProductCategory = this.getProductCategoryDetailAndValidate(productCategoryID);
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
	public Collection<ProductCategoryDetail> getProductCategories(String key, int offset, int limit) {
		List<Integer> ids = this.pdtCategoryDOMapper.selectByKey(key, new RowBounds(offset, limit));
		if (ids == null || ids.size() <=0) {
			return null;
		}
		Collection<ProductCategoryDetail> pcs = new ArrayList<ProductCategoryDetail>();
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
		ProductCategoryDetail loadProductCategory = this.getProductCategoryDetailAndValidate(categoryID);
		Collection<ProductCategoryDetail> allChildProductCategories = loadProductCategory.getAllChildProductCategories();
		if (allChildProductCategories !=null && allChildProductCategories.size() >0) {
			for (ProductCategoryDetail pc : allChildProductCategories) {
				if (pc.isExistProduct()) {
					return false;
				}
			}
		}
		return !loadProductCategory.isExistProduct();
	}

	@Override
	public Map<Integer, ProductCategoryDetail> getProductCategoriesMap(final Collection<Integer> ids) {
		if (ids == null || ids.size() <=0) {
			return null;
		}
		return this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Map<Integer, ProductCategoryDetail>>() {
			@Override
			public Map<Integer, ProductCategoryDetail> doInRedis(Jedis jedis) {
				List<String> keies = new ArrayList<String>();
				for (Integer id : ids) {
					keies.add(PDCache.getCategoryKey(id));
				}
				List<String> values = jedis.mget(keies.toArray(new String[keies.size()]));
				int index = 0;
				Map<Integer, ProductCategoryDetail> dataMap = new LinkedHashMap<Integer, ProductCategoryDetail>();
				for (Integer id : ids) {
					String value = values.get(index);
					index++;
					if (value == null || value.equals("")) {
						continue;
					}
					Gson gson = GsonCreatorOfPD.create();
					ProductCategoryDetail pc = gson.fromJson(value, ProductCategoryDetail.class);
					dataMap.put(id, pc);
				}
				if (dataMap.size() <=0) {
					return null;
				}
				return dataMap;
			}
		});
	}

	@Override
	public ProductCategoryDetail appendBrandToProductCategoryDetail(final BasicProductCategory basicProductCategory, final Brand brand) throws CategoryMustLeafNodeException, ObjectDuplicateException {
		MyAssert4Business.objectInitialized(basicProductCategory);
		MyAssert4Business.notNull(brand);
		if (!basicProductCategory.isLeafable()) {
			throw new CategoryMustLeafNodeException();
		}
		PDTCategoryBrandRelDOKey key = this.pdtCategoryBrandRelDOMapper.selectByPrimaryKey(basicProductCategory.getId(), brand.getId());
		if (key != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryBrandRelDOKey key = new PDTCategoryBrandRelDOKey();
				key.setBrandId(brand.getId());
				key.setCategoryId(basicProductCategory.getId());
				pdtCategoryBrandRelDOMapper.insert(key);
			}
		});
		this.defineCommonService.clearProductDefineCache();
		return this.getProductCategoryDetail(basicProductCategory.getId());
	}
	
	@Override
	public ProductCategoryDetail appendBrandsToProductCategoryDetail(final BasicProductCategory basicProductCategory, final Collection<Brand> brands) throws CategoryMustLeafNodeException, ObjectDuplicateException {
		MyAssert4Business.objectInitialized(basicProductCategory);
		MyAssert4Business.notNull(brands);
		if (!basicProductCategory.isLeafable()) {
			throw new CategoryMustLeafNodeException();
		}
		for (Brand brand : brands) {
			PDTCategoryBrandRelDOKey key = this.pdtCategoryBrandRelDOMapper.selectByPrimaryKey(basicProductCategory.getId(), brand.getId());
			if (key != null) {
				throw new ObjectDuplicateException();
			}
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				for (Brand brand : brands) {
					PDTCategoryBrandRelDOKey key = new PDTCategoryBrandRelDOKey();
					key.setBrandId(brand.getId());
					key.setCategoryId(basicProductCategory.getId());
					pdtCategoryBrandRelDOMapper.insert(key);
				}
			}
		});
		this.defineCommonService.clearProductDefineCache();
		return this.getProductCategoryDetail(basicProductCategory.getId());
	}
	
	@Override
	public void deleteBrandFromProductCategory(final BasicProductCategory productCategory, final Brand brand) throws ExistProductsNotAllowWriteException {
		MyAssert4Business.objectInitialized(productCategory);
		MyAssert4Business.objectInitialized(brand);
		if (productCategory.isExistProduct()) {
			throw new ExistProductsNotAllowWriteException();
		}
		PDTCategoryBrandRelDOKey relDOKey = this.pdtCategoryBrandRelDOMapper.selectByPrimaryKey(productCategory.getId(), brand.getId());
		if (relDOKey != null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryBrandRelDOKey key = new PDTCategoryBrandRelDOKey();
				key.setCategoryId(productCategory.getId());
				key.setBrandId(brand.getId());
				pdtCategoryBrandRelDOMapper.deleteByPrimaryKey(key);
			}
		});
		this.defineCommonService.clearProductDefineCache();
	}

	@Override
	public Collection<Brand> getBrandsByLeafProductCategory(BasicProductCategory basicProductCategoryOfLeafNode) {
		if (!basicProductCategoryOfLeafNode.isLeafable()) {
			throw new IllegalArgumentException();
		}
		Integer[] categoryIDs = new Integer[]{basicProductCategoryOfLeafNode.getId()};
		List<PDTBrandDO> pdtBrandDOs = this.pdtBrandDOMapper.selectByCategoryIDs(categoryIDs);
		if (pdtBrandDOs == null || pdtBrandDOs.size() <=0) {
			return null;
		}
		Collection<Brand> brands = new ArrayList<Brand>();
		for (PDTBrandDO model : pdtBrandDOs) {
			Brand brand = BeanUtil.toBrand(model);
			brands.add(brand);
		}
		return brands;
	}
	
}
