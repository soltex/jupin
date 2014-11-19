/**
 * 
 */
package com.vanstone.jupin.framework.productdef.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.business.lang.EnumUtils;
import com.vanstone.business.lang.TrueFalseUtil;
import com.vanstone.common.MyAssert;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.framework.Constants;
import com.vanstone.jupin.framework.cache.JupinRedisCache;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.AttributeScope;
import com.vanstone.jupin.framework.productdef.AttributeType;
import com.vanstone.jupin.framework.productdef.EnumValue;
import com.vanstone.jupin.framework.productdef.ProductCategory;
import com.vanstone.jupin.framework.productdef.persistence.PDTAttributeDefDOMapper;
import com.vanstone.jupin.framework.productdef.persistence.PDTAttributeEnumValueDOMapper;
import com.vanstone.jupin.framework.productdef.persistence.PDTCategoryAttributeDefRelDOMapper;
import com.vanstone.jupin.framework.productdef.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeDefDO;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeEnumValueDO;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTCategoryAttributeDefRelDOKey;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTCategoryDO;
import com.vanstone.jupin.framework.productdef.services.IProductDefService;
import com.vanstone.jupin.framework.services.AbstractJupinService;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("productDefService")
public class ProductDefServiceImpl extends AbstractJupinService implements IProductDefService {

	/** */
	private static final long serialVersionUID = 7974966124434236819L;
	
	private static Logger LOG = LoggerFactory.getLogger(ProductDefServiceImpl.class);

	@Autowired
	private PDTAttributeDefDOMapper pdtAttributeDefDOMapper;
	@Autowired
	private PDTAttributeEnumValueDOMapper pdtAttributeEnumValueDOMapper;
	@Autowired
	private PDTCategoryAttributeDefRelDOMapper pdtCategoryAttributeDefRelDOMapper;
	@Autowired
	private PDTCategoryDOMapper pdtCategoryDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.framework.productdef.services.IProductDefService#
	 * addProductCategory
	 * (com.vanstone.jupin.framework.productdef.ProductCategory,
	 * java.util.Collection)
	 */
	@Override
	public ProductCategory addProductCategory(final ProductCategory productCategory, final Collection<AbstractAttribute> productAttributes) {
		MyAssert.notNull(productCategory);
		
		initDefaultProductCategory();
		
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				if (!CollectionUtils.isEmpty(productAttributes)) {
					for (AbstractAttribute attribute : productAttributes) {
						LOG.info("id : " + attribute.getId() + " -- " + attribute.initialable());
						if (!attribute.initialable()) {
							attribute = addAttribute(attribute);
						} else {
							AbstractAttribute loadAttribute = getAttribute(attribute.getId());
							if (loadAttribute == null) {
								throw new IllegalArgumentException("Current Attribute Id Nof found. -> "
										+ attribute.getId());
							}
						}
					}
				}
				productCategory.setLeafable(true);
				PDTCategoryDO pdtCategoryDO = BeanUtil.toPDTCategoryDO(productCategory);
				pdtCategoryDOMapper.insert(pdtCategoryDO);
				productCategory.setId(pdtCategoryDO.getId());
				if (!CollectionUtils.isEmpty(productAttributes)) {
					for (AbstractAttribute attribute : productAttributes) {
						PDTCategoryAttributeDefRelDOKey key = new PDTCategoryAttributeDefRelDOKey();
						key.setAttributeDefId(attribute.getId());
						key.setCategoryId(pdtCategoryDO.getId());
						pdtCategoryAttributeDefRelDOMapper.insert(key);
					}
				}
				if (productCategory.getParentProductCategory() != null
						&& !productCategory.getParentProductCategory().getId()
								.equals(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID)) {
					PDTCategoryDO parentCategoryDO = new PDTCategoryDO();
					parentCategoryDO.setId(productCategory.getParentProductCategory().getId());
					parentCategoryDO.setLeafable(TrueFalseUtil.returnFalse());
					pdtCategoryDOMapper.updateByPrimaryKeySelective(parentCategoryDO);
					LOG.info("Update Parent Category ID : " + productCategory.getParentProductCategory().getId() + " not leaf.");
				}
			}
		});
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		return this.getProductCategory(productCategory.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.framework.productdef.services.IProductDefService#
	 * updateBaseProductCategoryInfo
	 * (com.vanstone.jupin.framework.productdef.ProductCategory)
	 */
	@Override
	public ProductCategory updateBaseProductCategoryInfo(final ProductCategory productCategory) {
		MyAssert4Business.objectInitialized(productCategory);
		ProductCategory loadProductCategory = this.getProductCategory(productCategory.getId());
		if (loadProductCategory == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.updateBaseInfo(productCategory.getId(), productCategory.getCategoryName(), 
						productCategory.getDescription(), productCategory.getCategoryBindPage()	, null, productCategory.getSort());
			}
		});
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		return this.getProductCategory(productCategory.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.framework.productdef.services.IProductDefService#
	 * updateCoverImageOfProductCategory(int,
	 * com.vanstone.jupin.framework.common.ImageObject)
	 */
	@Override
	public ProductCategory updateCoverImageOfProductCategory(final int cid, final ImageObject imageObject) {
		ProductCategory productCategory = this.getProductCategory(cid);
		if (productCategory == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryDO model = new PDTCategoryDO();
				model.setId(cid);
				if (imageObject != null) {
					pdtCategoryDOMapper.updateCoverImageObject(cid, imageObject.getImageWeedFile().getFileid(),
							imageObject.getWidth(), imageObject.getHeight(), imageObject.getImageWeedFile()
									.getExtName());
				} else {
					pdtCategoryDOMapper.updateCoverImageObject(cid, null, null, null, null);
				}
			}
		});
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		return this.getProductCategory(cid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.jupin.framework.productdef.services.IProductDefService#
	 * getProductCategory(int)
	 */
	@Override
	public ProductCategory getProductCategory(int cid) {
		
		if (cid <= Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID) {
			cid = Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID;
		}
		
		String key =CacheUtil.buildCategoryKey(cid);
		
		ServiceUtil<ProductCategory, String> serviceUtil = new ServiceUtil<ProductCategory, String>();
		ProductCategory loadCategory = serviceUtil.getObjectFromRedisByKey(redisTemplate, JupinRedisCache.jupin, ProductCategory.class, key);
		if (loadCategory != null) {
			return loadCategory;
		}
		
		PDTCategoryDO model = this.pdtCategoryDOMapper.selectByPrimaryKey(cid);
		if (model == null) {
			return null;
		}
		ProductCategory pc = BeanUtil.toProductCategory(model);
		
		//Parent Product Category 
		if (model.getParentId() != null && !model.getParentId().equals(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID)) {
			PDTCategoryDO parentPdtCategoryDO = this.pdtCategoryDOMapper.selectByPrimaryKey(model.getParentId());
			ProductCategory parentProductCategory = BeanUtil.toProductCategory(parentPdtCategoryDO);
			pc.setParentProductCategory(parentProductCategory);
		}
		
		//Leaf Product Category
		Collection<ProductCategory> leafProductCategories = this._loadLeafProductCategoriesFromDB(cid);
		if (leafProductCategories != null && leafProductCategories.size() >0) {
			pc.addToLeafProductCategories(leafProductCategories);
		}
		
		//Child Product Category
		Collection<ProductCategory> childProductCategories = this._loadChildProductCategoriesFromDB(cid);
		if (childProductCategories != null && childProductCategories.size() >0) {
			pc.addToChildProductCategories(childProductCategories);
		}
		
		//All Child Product Category
		Collection<ProductCategory> allChildProductCategories = this._loadAllChildProductCategoriesFromDB(cid);
		if (allChildProductCategories != null && allChildProductCategories.size() > 0) {
			pc.addToAllChildProductCategories(allChildProductCategories);
		}
		
		//Product Category Node Path
		Collection<ProductCategory> productCategoryNodePath = this._loadProductCategoryNodePath(cid);
		if (productCategoryNodePath != null && productCategoryNodePath.size() >0) {
			pc.addToProductCategoryNodePath(productCategoryNodePath);
		}
		
		//All Attributes
		List<Integer> nodePathWithSelfIds = new ArrayList<Integer>();
		if (pc.getProductCategoryNodePath() != null && pc.getProductCategoryNodePath().size() >0) {
			for (ProductCategory c : pc.getProductCategoryNodePath()) {
				nodePathWithSelfIds.add(c.getId());
			}
		}
		nodePathWithSelfIds.add(cid);
		List<Integer> attributeIds = this.pdtCategoryAttributeDefRelDOMapper.selectAttributeIdsByCategoryIds(nodePathWithSelfIds);
		if (attributeIds != null && attributeIds.size() >0) {
			List<AbstractAttribute> allAttributes = this.getAttributeByIds(attributeIds);
			pc.addToAllAttributes(allAttributes);
		}
		
		//search Attributes
		if (pc.getAllAttributes() != null && pc.getAllAttributes().size() >0) {
			for (AbstractAttribute attribute : pc.getAllAttributes()) {
				if (attribute.getAttributeType().equals(AttributeType.Enum)) {
					Attr4Enum attr4Enum = (Attr4Enum)attribute;
					if (attr4Enum.isSearchable()) {
						pc.addToSearchAttributes(attr4Enum);
					}
				}
			}
		}
		
		//allProductAttributes
		if (pc.getAllAttributes() != null && pc.getAllAttributes().size() >0) {
			for (AbstractAttribute attribute : pc.getAllAttributes()) {
				if (attribute.getAttributeScope().equals(AttributeScope.Product)) {
					pc.addToAllProductAttributes(attribute);
				}
			}
		}
		
		//allSkuAttributes
		if (pc.getAllAttributes() != null && pc.getAllAttributes().size() >0) {
			for (AbstractAttribute attribute : pc.getAllAttributes()) {
				if (attribute.getAttributeScope().equals(AttributeScope.Sku) && attribute.getAttributeType().equals(AttributeType.Enum)) {
					Attr4Enum attr4Enum = (Attr4Enum)attribute;
					pc.addToAllSkuAttributes(attr4Enum);
				}
			}
		}
		serviceUtil.setObjectToRedis(redisTemplate, JupinRedisCache.jupin, key, pc);
		return pc;
	}
	
	@Override
	public Collection<ProductCategory> getRootproductCategories() {
		ProductCategory pc = this.getProductCategory(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID);
		if (pc == null) {
			return null;
		}
		return pc.getChildProductCategories();
	}
	
	@Override
	public void deleteProductCategory(final int cid) throws ObjectHasSubObjectException {
		ProductCategory productCategory = this.getProductCategory(cid);
		if (productCategory == null) {
			throw new IllegalArgumentException();
		}
		if (!productCategory.isLeafable()) {
			throw new ObjectHasSubObjectException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryDOMapper.deleteByPrimaryKey(cid);
				pdtCategoryAttributeDefRelDOMapper.deleteByCategoryId(cid);
			}
		});
		LOG.info("DELETE Category ID : " + cid);
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
	}

	@Override
	public void forceDeleteProductCategory(final int cid) {
		ProductCategory productCategory = this.getProductCategory(cid);
		if (productCategory == null) {
			throw new IllegalArgumentException();
		}
		final Collection<ProductCategory> allChildProductCategories = productCategory.getAllChildProductCategories();
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				if (allChildProductCategories != null && allChildProductCategories.size() >0) {
					for (ProductCategory pc : allChildProductCategories) {
						pdtCategoryDOMapper.deleteByPrimaryKey(pc.getId());
						pdtCategoryAttributeDefRelDOMapper.deleteByCategoryId(pc.getId());
					}
				}
				pdtCategoryDOMapper.deleteByPrimaryKey(cid);
				pdtCategoryAttributeDefRelDOMapper.deleteByCategoryId(cid);
			}
		});
		//TODO 消息发送集成
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		LOG.info("Force delete all product categories.");
	}
	
	@Override
	public void refreshAllProductCategories() {
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		List<Integer> ids = this.pdtCategoryDOMapper.selectAllIds();
		if (ids != null && ids.size() >0) {
			for (Integer id : ids) {
				this.getProductCategory(id);
				LOG.info("Refresh Product Category ID : " + id);
			}
		}
		LOG.info("Refresh All Product Categories OK.");
	}
	
	@Override
	public ProductCategory appendAttribute(final int cid, final AbstractAttribute attribute) throws ObjectDuplicateException {
		MyAssert.notNull(attribute);
		ProductCategory loadProductCategory = this.getProductCategory(cid);
		if (loadProductCategory == null) {
			throw new IllegalArgumentException();
		}
		if (!attribute.initialable()) {
			PDTCategoryAttributeDefRelDOKey key = new PDTCategoryAttributeDefRelDOKey();
			key.setAttributeDefId(attribute.getId());
			key.setCategoryId(cid);
			PDTCategoryAttributeDefRelDOKey tmpKey = pdtCategoryAttributeDefRelDOMapper.selectByKey(cid, attribute.getId());
			if (tmpKey != null) {
				throw new ObjectDuplicateException();
			}
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				Integer attributeId = null;
				if (!attribute.initialable()) {
					AbstractAttribute tmpAttribute = addAttribute(attribute);
					attributeId = tmpAttribute.getId();
				}else{
					attributeId = attribute.getId();
				}
				PDTCategoryAttributeDefRelDOKey pdtCategoryAttributeDefRelDOKey = new PDTCategoryAttributeDefRelDOKey();
				pdtCategoryAttributeDefRelDOKey.setCategoryId(cid);
				pdtCategoryAttributeDefRelDOKey.setAttributeDefId(attributeId);
				pdtCategoryAttributeDefRelDOMapper.insert(pdtCategoryAttributeDefRelDOKey);
			}
		});
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		return this.getProductCategory(cid);
	}
	
	@Override
	public ProductCategory appendAttributes(final int cid, final Collection<AbstractAttribute> attributes) throws ObjectDuplicateException {
		MyAssert4Business.notEmpty(attributes);
		
		ProductCategory loadProductCategory = this.getProductCategory(cid);
		if (loadProductCategory == null) {
			throw new IllegalArgumentException();
		}
		
		for (AbstractAttribute attribute : attributes) {
			if (!attribute.initialable()) {
				PDTCategoryAttributeDefRelDOKey key = new PDTCategoryAttributeDefRelDOKey();
				key.setAttributeDefId(attribute.getId());
				key.setCategoryId(cid);
				PDTCategoryAttributeDefRelDOKey tmpKey = pdtCategoryAttributeDefRelDOMapper.selectByKey(cid, attribute.getId());
				if (tmpKey != null) {
					throw new ObjectDuplicateException();
				}
			}
		}
		
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				Collection<Integer> attributeIds = new ArrayList<Integer>();
				for (AbstractAttribute attribute : attributes) {
					if (!attribute.initialable()) {
						AbstractAttribute tmpAttribute = addAttribute(attribute);
						attributeIds.add(tmpAttribute.getId());
					}else{
						attributeIds.add(attribute.getId());
					}
				}
				for (Integer attributeId : attributeIds) {
					PDTCategoryAttributeDefRelDOKey pdtCategoryAttributeDefRelDOKey = new PDTCategoryAttributeDefRelDOKey();
					pdtCategoryAttributeDefRelDOKey.setCategoryId(cid);
					pdtCategoryAttributeDefRelDOKey.setAttributeDefId(attributeId);
					pdtCategoryAttributeDefRelDOMapper.insert(pdtCategoryAttributeDefRelDOKey);
				}
			}
		});
		CacheUtil.clearAllProductCategoriesInCache(redisTemplate, JupinRedisCache.jupin);
		return this.getProductCategory(cid);
	}
	
	@Override
	public AbstractAttribute addAttribute(final AbstractAttribute attribute) {
		MyAssert.notNull(attribute);
		if (attribute.getAttributeType().equals(AttributeType.Lang)) {
			final Attr4Lang attr = (Attr4Lang) attribute;
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					PDTAttributeDefDO model = BeanUtil.toPDTAttributeDefDO(attr);
					pdtAttributeDefDOMapper.insert(model);
					attribute.setId(model.getId());
				}
			});
		} else {
			final Attr4Enum attr4Enum = (Attr4Enum) attribute;
			if (!attr4Enum.existEnumValues()) {
				throw new IllegalArgumentException("Enum Attribute Type must has enum values.");
			}
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					// 写入PDT_ATTRIBUTE_DEF
					PDTAttributeDefDO model = BeanUtil.toPDTAttributeDefDO(attr4Enum);
					pdtAttributeDefDOMapper.insert(model);
					attribute.setId(model.getId());
					// 写入PDT_ATTRIBUTE_ENUMVALUE
					for (EnumValue value : attr4Enum.getEnumValues()) {
						PDTAttributeEnumValueDO valueDO = BeanUtil.toPDTAttributeEnumValueDO(value);
						valueDO.setAttributeDefId(attribute.getId());
						pdtAttributeEnumValueDOMapper.insert(valueDO);
						value.setId(valueDO.getId());
					}
				}
			});
		}
		LOG.info("Add Attribute ok. " + ToStringBuilder.reflectionToString(attribute, ToStringStyle.SHORT_PREFIX_STYLE));
		return attribute;
	}

	@Override
	public AbstractAttribute getAttribute(int aid) {
		String key = CacheUtil.buildAttributeKey(aid);
		ServiceUtil<AbstractAttribute, Integer> serviceUtil = new ServiceUtil<AbstractAttribute, Integer>();
		AbstractAttribute attribute = CacheUtil.getAttributeFromCache(aid, redisTemplate, JupinRedisCache.jupin);

		if (attribute != null) {
			LOG.info("Load Attribute from Redis Cache. -> " + key);
			return attribute;
		}
		PDTAttributeDefDO model = this.pdtAttributeDefDOMapper.selectByPrimaryKey(aid);
		if (model == null) {
			return null;
		}
		AttributeType attributeType = EnumUtils.getByCode(model.getAttributeType(), AttributeType.class);
		if (attributeType == null) {
			throw new IllegalArgumentException("Attribute Type' value is not illegal.");
		}
		AbstractAttribute abstractAttribute = null;
		AttributeScope attributeScope = EnumUtils.getByCode(model.getAttributeScope(), AttributeScope.class);
		if (attributeScope == null) {
			throw new IllegalArgumentException("Attribute Scope' value is not illegal.");
		}
		if (attributeType.equals(AttributeType.Lang)) {
			abstractAttribute = BeanUtil.toAttr4Lang(model);
		} else {
			Attr4Enum attr4Enum = BeanUtil.toAttr4Enum(model);
			List<PDTAttributeEnumValueDO> values = this.pdtAttributeEnumValueDOMapper.selectByAttributeDefId(model
					.getId());
			if (CollectionUtils.isEmpty(values)) {
				throw new IllegalArgumentException("Enum values is empty.");
			}
			for (PDTAttributeEnumValueDO valueDO : values) {
				attr4Enum.addEnumValue(BeanUtil.toEnumValue(valueDO));
			}
			abstractAttribute = attr4Enum;
		}
		serviceUtil.setObjectToRedis(redisTemplate, JupinRedisCache.jupin, key, abstractAttribute);
		return abstractAttribute;
	}
	
	@Override
	public List<AbstractAttribute> getAttributeByIds(final List<Integer> ids) {
		MyAssert.notEmpty(ids);
		List<String> redisvalues = this.redisTemplate.executeInRedis(JupinRedisCache.jupin, new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(Jedis jedis) {
				List<byte[]> byteValues = jedis.mget(CacheUtil.getKeies(ids));
				List<String> values = new ArrayList<String>();
				for (byte[] bytevalue : byteValues) {
					if (bytevalue != null) {
						values.add(new String(bytevalue, Constants.SYS_DEFAULT_CHARSET));
					}else{
						values.add(null);
					}
				}
				return values;
			}
		});
		List<AbstractAttribute> values = new ArrayList<AbstractAttribute>();
		for (int i=0;i<redisvalues.size();i++) {
			String rv = redisvalues.get(i);
			if (rv != null) {
				values.add(CacheUtil.getAttributeByJsonValue(rv, redisTemplate, JupinRedisCache.jupin));
			}else{
				values.add(this.getAttribute(ids.get(i)));
			}
		}
		return values;
	}
	
	@Override
	public AbstractAttribute updateBaseAttribute(final AbstractAttribute attribute) {
		final AbstractAttribute loadAttribute = this.getAttribute(attribute.getId());
		if (loadAttribute == null) {
			throw new IllegalArgumentException("Attribute for id : " + attribute.getId() + " is null");
		}
		if (loadAttribute.getAttributeType().equals(AttributeType.Lang)) {
			// Lang 类型
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Attr4Lang attr4Lang = (Attr4Lang) loadAttribute;
					PDTAttributeDefDO model = BeanUtil.toPDTAttributeDefDO(attr4Lang);
					model.setAttributeName(attribute.getAttributeName());
					model.setAttributeDescription(attribute.getDescription());
					model.setAttributeScope(attribute.getAttributeScope().getCode());
					pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
				}
			});
		} else {
			// Enum 类型
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					Attr4Enum attr4Enum = (Attr4Enum) loadAttribute;
					PDTAttributeDefDO model = BeanUtil.toPDTAttributeDefDO(attr4Enum);
					model.setAttributeName(attribute.getAttributeName());
					model.setAttributeDescription(attribute.getDescription());
					model.setAttributeScope(attribute.getAttributeScope().getCode());
					model.setSearchable(TrueFalseUtil.parseInt(((Attr4Enum) attribute).isSearchable()));
					pdtAttributeDefDOMapper.updateByPrimaryKeyWithBLOBs(model);
				}
			});
		}
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = CacheUtil.buildAttributeKey(attribute.getId());
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisCache.jupin, key);
		return this.getAttribute(attribute.getId());
	}

	@Override
	public void deleteAttribute(final int aid) {
		AbstractAttribute attribute = this.getAttribute(aid);
		if (attribute == null) {
			throw new IllegalArgumentException("Attribute Null.");
		}
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = CacheUtil.buildAttributeKey(aid);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtCategoryAttributeDefRelDOMapper.deleteByAttributeDefId(aid);
				pdtAttributeDefDOMapper.deleteByPrimaryKey(aid);
				pdtAttributeEnumValueDOMapper.deleteByAttributeId(aid);
			}
		});
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisCache.jupin, key);
		// TODO 发送删除消息
	}

	@Override
	public void addEnumValue(final int aid, final String value, final int sort) throws ObjectDuplicateException {
		final AbstractAttribute attribute = this.getAttribute(aid);
		if (attribute == null) {
			throw new IllegalArgumentException("Attribute Null.");
		}
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = CacheUtil.buildAttributeKey(aid);
		PDTAttributeEnumValueDO valueDO = this.pdtAttributeEnumValueDOMapper
				.selectByAttributeId_ObjectValue(aid, value);
		if (valueDO != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTAttributeEnumValueDO model = new PDTAttributeEnumValueDO();
				model.setAttributeDefId(aid);
				model.setObjectvalue(value);
				model.setSort(sort);
				pdtAttributeEnumValueDOMapper.insert(model);
			}
		});
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisCache.jupin, key);
	}

	@Override
	public void deleteEnumValue(final int enumid) {
		PDTAttributeEnumValueDO model = this.pdtAttributeEnumValueDOMapper.selectByPrimaryKey(enumid);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtAttributeEnumValueDOMapper.deleteByPrimaryKey(enumid);
			}
		});
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = CacheUtil.buildAttributeKey(model.getAttributeDefId());
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisCache.jupin, key);
	}

	@Override
	public void updateEnumValue(final int enumid, final String objectvalue, final int sort) {
		final PDTAttributeEnumValueDO model = this.pdtAttributeEnumValueDOMapper.selectByPrimaryKey(enumid);
		if (model == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				model.setObjectvalue(objectvalue);
				model.setSort(sort);
				pdtAttributeEnumValueDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = CacheUtil.buildAttributeKey(model.getAttributeDefId());
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisCache.jupin, key);
	}
	
	@Override
	public void initDefaultProductCategory() {
		PDTCategoryDO rootModel = this.pdtCategoryDOMapper.selectByPrimaryKey(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID);
		if (rootModel != null) {
			LOG.info("ROOT Product Category Has been initialized.");
			return;
		}
		
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryDO model = new PDTCategoryDO();
				model.setId(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID);
				model.setCategoryName(Constants.DEFAULT_ROOT_RPODUCT_CATEGORY_NAME);
				model.setSort(0);
				model.setLeafable(TrueFalseUtil.returnFalse());
				pdtCategoryDOMapper.insert(model);
			}
		});
		LOG.info("Default Product Category Initial ok.");
		
	}
	
	@Override
	public Collection<AbstractAttribute> searchAttributes(String key, int limit) {
		List<Integer> ids = this.pdtAttributeDefDOMapper.selectByKey(key, limit > 0 ? new RowBounds(0, limit) : new RowBounds(0, Integer.MAX_VALUE));
		if (ids != null && ids.size() >0) {
			Collection<AbstractAttribute> attributes = new ArrayList<AbstractAttribute>();
			for (Integer id : ids) {
				attributes.add(this.getAttribute(id));
			}
			return attributes;
		}
		return null;
	}
	
	private Collection<ProductCategory> _loadProductCategoryNodePath(int id) {
		if (id <= Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID) {
			return null;
		}
		PDTCategoryDO rootModel = this.pdtCategoryDOMapper.selectByPrimaryKey(id);
		if (rootModel == null || rootModel.getParentId() == null || rootModel.getParentId().equals(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID)) {
			return null;
		}
		Collection<ProductCategory> collection = new ArrayList<ProductCategory>();
		Integer parentId = rootModel.getParentId();
		while (true) {
			PDTCategoryDO model = this.pdtCategoryDOMapper.selectByPrimaryKey(parentId);
			if (model == null) {
				throw new IllegalArgumentException("ID Not found.");
			}
			ProductCategory pc = BeanUtil.toProductCategory(model);
			collection.add(pc);
			parentId = model.getParentId();
			if (model.getParentId() == null || model.getParentId().equals(Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID)) {
				break;
			}
		}
		if (!CollectionUtils.isEmpty(collection)) {
			ProductCategory[] pcs = collection.toArray(new ProductCategory[collection.size()]);
			ArrayUtils.reverse(pcs);
			return Arrays.asList(pcs);
		}
		return null;
	}
	
	private Collection<ProductCategory> _loadLeafProductCategoriesFromDB(int id) {
		if (id <=Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID) {
			id = Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID;
		}
		PDTCategoryDO rootModel = this.pdtCategoryDOMapper.selectByPrimaryKey(id);
		if (rootModel == null) {
			return null;
		}
		if (rootModel.getLeafable().equals(TrueFalseUtil.parseInt(true))) {
			return null;
		}
		Collection<ProductCategory> leafCollection = new ArrayList<ProductCategory>();
		List<PDTCategoryDO> pdtCategoryDOs = this.pdtCategoryDOMapper.selectByParentId(id);
		if (pdtCategoryDOs != null && pdtCategoryDOs.size() >0) {
			for (PDTCategoryDO pdtCategoryDO : pdtCategoryDOs) {
				if (pdtCategoryDO.getLeafable().equals(TrueFalseUtil.returnTrue())) {
					leafCollection.add(BeanUtil.toProductCategory(pdtCategoryDO));
					continue;
				}
				leafCollection.addAll(this._loadLeafProductCategoriesFromDB(pdtCategoryDO.getId()));
			}
		}
		return leafCollection;
	}

	private Collection<ProductCategory> _loadChildProductCategoriesFromDB(int id) {
		if (id <Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID) {
			id = Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID;
		}
		PDTCategoryDO rootModel = this.pdtCategoryDOMapper.selectByPrimaryKey(id);
		if (rootModel == null) {
			return null;
		}
		if (rootModel.getLeafable().equals(TrueFalseUtil.parseInt(true))) {
			return null;
		}
		
		List<PDTCategoryDO> pdtCategoryDOs = this.pdtCategoryDOMapper.selectByParentId(id);
		if (pdtCategoryDOs != null && pdtCategoryDOs.size() >0) {
			Collection<ProductCategory> leafCollection = new ArrayList<ProductCategory>();
			for (PDTCategoryDO pdtCategoryDO : pdtCategoryDOs) {
				leafCollection.add(BeanUtil.toProductCategory(pdtCategoryDO));
			}
			return leafCollection;
		}
		return null;
	}

	private Collection<ProductCategory> _loadAllChildProductCategoriesFromDB(int id) {
		if (id <Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID) {
			id = Constants.DEFAULT_ROOT_PRODUCT_CATEGORY_ID;
		}
		PDTCategoryDO rootModel = this.pdtCategoryDOMapper.selectByPrimaryKey(id);
		if (rootModel == null) {
			return null;
		}
		if (rootModel.getLeafable().equals(TrueFalseUtil.parseInt(true))) {
			return null;
		}
		Collection<ProductCategory> collection = new ArrayList<ProductCategory>();
		List<PDTCategoryDO> pdtCategoryDOs = this.pdtCategoryDOMapper.selectByParentId(id);
		if (pdtCategoryDOs != null && pdtCategoryDOs.size() >0) {
			for (PDTCategoryDO pdtCategoryDO : pdtCategoryDOs) {
				if (pdtCategoryDO.getLeafable().equals(TrueFalseUtil.returnTrue())) {
					collection.add(BeanUtil.toProductCategory(pdtCategoryDO));
					continue;
				}
				collection.add(BeanUtil.toProductCategory(pdtCategoryDO));
				collection.addAll(this._loadAllChildProductCategoriesFromDB(pdtCategoryDO.getId()));
			}
		}
		return collection;
	}
}
