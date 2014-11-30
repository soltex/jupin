/**
 * 
 */
package com.vanstone.jupin.ebs.pm.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.PinyinUtil;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ebs.pm.GsonCreatorOfPD;
import com.vanstone.jupin.ebs.pm.PDCache;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTBrandDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.PDTCategoryBrandRelDOMapper;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTBrandDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTCategoryBrandRelDOKey;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.QueryBrandStatResultMap;
import com.vanstone.jupin.ebs.pm.productdefine.Brand;
import com.vanstone.jupin.ebs.pm.productdefine.ProductCategory;
import com.vanstone.jupin.ebs.pm.productdefine.services.BrandService;
import com.vanstone.jupin.ebs.pm.productdefine.services.CategoryMustLeafNodeException;
import com.vanstone.jupin.ebs.pm.productdefine.services.DefineCommonService;
import com.vanstone.jupin.ebs.pm.productdefine.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisTemplate;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * @author shipeng
 */
@Service("brandService")
@Validated
public class BrandServiceImpl extends DefaultBusinessService implements BrandService {

	/***/
	private static final long serialVersionUID = 1563959938577671056L;
	
	private static Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private PDTBrandDOMapper pdtBrandDOMapper;
	@Autowired
	private PDTCategoryBrandRelDOMapper pdtCategoryBrandRelDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private DefineCommonService defineCommonService;
	
	@Override
	public Brand addBrand(final Brand brand) throws ObjectDuplicateException {
		PDTBrandDO tempModel = this.pdtBrandDOMapper.selectByBrandName(brand.getBrandName());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				brand.setBrandNamefirstLetter(PinyinUtil.firstLetterOfString(brand.getBrandName()) != null ? PinyinUtil.firstLetterOfString(brand.getBrandName()).charAt(0) : null);
				String pinyin = PinyinUtil.cnstr2pinyinstr(brand.getBrandName());
				if (pinyin != null && !pinyin.equals("")) {
					pinyin = StringUtils.replaceChars(pinyin, Constants.BRAND_NAME_CHARS, "");
					brand.setBrandNamePinyin(pinyin);
				}
				PDTBrandDO model  = BeanUtil.toPDTBrandDO(brand);
				pdtBrandDOMapper.insert(model);
				brand.setId(model.getId());
			}
		});
		defineCommonService.clearProductDefineCache();
		return brand;
	}
	
	@Override
	public Brand addBrand(final Brand brand, final Collection<ProductCategory> productCategories) throws CategoryMustLeafNodeException, ObjectDuplicateException {
		PDTBrandDO tempModel = this.pdtBrandDOMapper.selectByBrandName(brand.getBrandName());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		_validateProdudctCategories(productCategories);
		return this.execute(new TransactionCallback<Brand>() {
			@Override
			public Brand doInTransaction(TransactionStatus arg0) {
				PDTBrandDO model  = BeanUtil.toPDTBrandDO(brand);
				pdtBrandDOMapper.insert(model);
				brand.setId(model.getId());
				for (ProductCategory category : productCategories) {
					PDTCategoryBrandRelDOKey key = new PDTCategoryBrandRelDOKey();
					key.setBrandId(brand.getId());
					key.setCategoryId(category.getId());
					pdtCategoryBrandRelDOMapper.insert(key);
				}
				defineCommonService.clearProductDefineCache();
				return brand;
			}
		});
	}
	
	/**
	 * 验证节点 
	 * @param productCategories
	 * @return
	 * @throws CategoryMustLeafNodeException
	 */
	private void _validateProdudctCategories(Collection<ProductCategory> productCategories) throws CategoryMustLeafNodeException {
		if (productCategories == null || productCategories.size() <=0) {
			return;
		}
		Collection<Integer> productCategoryIds = new ArrayList<Integer>();
		for (ProductCategory category : productCategories) {
			if (!category.isLeafable()) {
				throw new CategoryMustLeafNodeException();
			}
			if (productCategoryIds.contains(category.getId())) {
				LOG.error("Duplicate category id has been contain, {}" , category.getId());
				throw new IllegalArgumentException("Duplicate category id has been contain");
			}
			productCategoryIds.add(category.getId());
		}
	}
	
	@Override
	public Brand getBrand(final int id) {
		final String key = PDCache.getBrandKey(id);
		final ServiceUtil<Brand, String> serviceUtil = new ServiceUtil<Brand, String>();
		Brand loadBrand = serviceUtil.getObjectFromRedisByKey(redisTemplate, JupinRedisRef.Jupin_Core, Brand.class, key);
		if (loadBrand != null) {
			return loadBrand;
		}
		return ZKUtil.executeMutex(key, new InterProcessMutexCallback<Brand>() {
			@Override
			public Brand doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTBrandDO pdtBrandDO = pdtBrandDOMapper.selectByPrimaryKey(id);
				if (pdtBrandDO == null) {
					return null;
				}
				Brand brand = BeanUtil.toBrand(pdtBrandDO);
				serviceUtil.setObjectToRedis(redisTemplate, JupinRedisRef.Jupin_Core, key, brand);
				return brand;
			}
			@Override
			public Brand doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getBrand(id);
			}
		});
	}
	
	@Override
	public Brand getBrandAndValidate(int id) {
		Brand brand = this.getBrand(id);
		if (brand == null) {
			throw new IllegalArgumentException();
		}
		return brand;
	}
	
	@Override
	public Map<Integer, Brand> getBrandsMap(final Collection<Integer> ids) {
		if (ids == null || ids.size() <=0) {
			return null;
		}
		return this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Map<Integer, Brand>>() {
			@Override
			public Map<Integer, Brand> doInRedis(Jedis jedis) {
				Collection<String> keies = new ArrayList<String>();
				Map<Integer, Brand> dataMap = new LinkedHashMap<Integer, Brand>();
				for (Integer id : ids) {
					String key = PDCache.getBrandKey(id);
					keies.add(key);
				}
				List<String> values = jedis.mget(keies.toArray(new String[keies.size()]));
				int index = 0;
				for (Integer id : ids) {
					String value = values.get(index);
					Brand brand = null;
					if (value != null && value.length() > 0) {
						Gson gson = GsonCreatorOfPD.create();
						brand = gson.fromJson(value, Brand.class);
					}else{
						brand = getBrand(id);
					}
					if (brand != null) {
						dataMap.put(id, brand);
					}
					index++;
				}
				return dataMap != null && dataMap.size() >0 ? dataMap : null;
			}
		});
	}
	
	@Override
	public void deleteBrandLogoImage(final int id) throws ExistProductsNotAllowWriteException{
		final Brand brand = this.getBrandAndValidate(id);
		if (!defineCommonService.validateAllowUDOperateBrand(id)) {
			throw new ExistProductsNotAllowWriteException();
		}
		if (brand.getLogoImage() != null) {
			this.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus arg0) {
					pdtBrandDOMapper.updateLogoInfo(id, null, null, null, null);
				}
			});
			WeedFSClient weedFSClient = new WeedFSClient();
			weedFSClient.delete(brand.getLogoImage().getWeedFile().getFileid());
			LOG.info("DELETE WeedFS {}", brand.getLogoImage().getWeedFile().getFileid());
		}
		defineCommonService.clearProductDefineCache();
	}
	
	@Override
	public Brand updateBrandBaseInfo(final int brandId, final String brandName, final String brandNameEN, final String content, final boolean systemable) throws ObjectDuplicateException, ExistProductsNotAllowWriteException {
		final Brand loadBrand = this.getBrandAndValidate(brandId);
		if (!defineCommonService.validateAllowUDOperateBrand(brandId)) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadBrand.setBrandName(brandName);
				loadBrand.setBrandNameEN(brandNameEN);
				loadBrand.setContent(content);
				loadBrand.setSystemable(systemable);
				PDTBrandDO model = BeanUtil.toPDTBrandDO(loadBrand);
				pdtBrandDOMapper.updateByPrimaryKeyWithBLOBs(model);
			}
		});
		defineCommonService.clearProductDefineCache();
		return loadBrand;
	}
	
	@Override
	public Brand updateBrandLogoInfo(final int brandId, final ImageBean imageBean)  throws ExistProductsNotAllowWriteException {
		final Brand loadBrand = this.getBrandAndValidate(brandId);
		if (!defineCommonService.validateAllowUDOperateBrand(brandId)) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadBrand.setLogoImage(imageBean);
				pdtBrandDOMapper.updateLogoInfo(brandId, imageBean.getWeedFile().getFileid(), imageBean.getWeedFile().getExtName(), imageBean.getWidth(), imageBean.getHeight());
			}
		});
		defineCommonService.clearProductDefineCache();
		return loadBrand;
	}
	
	@Override
	public void deleteBrand(final int brandId) throws ExistProductsNotAllowWriteException {
		final Brand loadBrand = this.getBrandAndValidate(brandId);
		if (!defineCommonService.validateAllowUDOperateBrand(brandId)) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtBrandDOMapper.deleteByPrimaryKey(brandId);
				pdtCategoryBrandRelDOMapper.deleteByBrandId(brandId);
			}
		});
		defineCommonService.clearProductDefineCache();
		if (loadBrand.getLogoImage() != null) {
			WeedFSClient weedFSClient = new WeedFSClient();
			weedFSClient.delete(loadBrand.getLogoImage().getWeedFile().getFileid());
			LOG.info("DELETE WeedFS {}", loadBrand.getLogoImage().getWeedFile().getFileid());
		}
	}
	
	@Override
	public Collection<Brand> getBrandsWithStat(ProductCategory productCategory, String key, int offset, int limit) {
		Integer[] categoryIDs = null;
		if (productCategory != null) {
			Set<Integer> tempIDs = new LinkedHashSet<Integer>();
			if (productCategory.getAllChildProductCategories() != null && productCategory.getAllChildProductCategories().size() >0) {
				for (ProductCategory pc : productCategory.getAllChildProductCategories()) {
					tempIDs.add(pc.getId());
				}
			}
			tempIDs.add(productCategory.getId());
			categoryIDs = tempIDs.toArray(new Integer[tempIDs.size()]);
		}
		List<QueryBrandStatResultMap> rms = this.pdtBrandDOMapper.selectByCategoryIDs_Key(categoryIDs, key, new RowBounds(offset, limit));
		if (rms == null || rms.size() <=0) {
			return null;
		}
		Collection<Brand> brands = new ArrayList<Brand>();
		for (QueryBrandStatResultMap rm : rms) {
			Brand brand = BeanUtil.toBrand(rm);
			brands.add(brand);
		}
		return brands;
	}
	
	@Override
	public int getTotalBrands(ProductCategory productCategory, String key) {
		Integer[] categoryIDs = null;
		if (productCategory != null) {
			Set<Integer> tempIDs = new LinkedHashSet<Integer>();
			if (productCategory.getAllChildProductCategories() != null && productCategory.getAllChildProductCategories().size() >0) {
				for (ProductCategory pc : productCategory.getAllChildProductCategories()) {
					tempIDs.add(pc.getId());
				}
			}
			tempIDs.add(productCategory.getId());
			categoryIDs = tempIDs.toArray(new Integer[tempIDs.size()]);
		}
		return this.pdtBrandDOMapper.selectTotalByCategoryIDs_Key(categoryIDs, key);
	}
	
	@Override
	public Collection<Brand> getBrandsByPrefix(String prefix, int limit) {
		List<PDTBrandDO> models = this.pdtBrandDOMapper.selectByPinyinKey(prefix, new RowBounds(0, limit));
		if (models == null || models.size() <= 0) {
			return null;
		}
		Collection<Brand> brands = new ArrayList<Brand>();
		for (PDTBrandDO model : models) {
			brands.add(BeanUtil.toBrand(model));
		}
		return brands;
	}
	
	@Override
	public void appendBrandToProductCategory(final ProductCategory productCategory, final Brand brand) throws ObjectDuplicateException, CategoryMustLeafNodeException {
		MyAssert4Business.objectInitialized(productCategory);
		MyAssert4Business.objectInitialized(brand);
		if (!productCategory.isLeafable()) {
			throw new CategoryMustLeafNodeException();
		}
		PDTCategoryBrandRelDOKey relDOKey = this.pdtCategoryBrandRelDOMapper.selectByPrimaryKey(productCategory.getId(), brand.getId());
		if (relDOKey != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTCategoryBrandRelDOKey model = new PDTCategoryBrandRelDOKey();
				model.setBrandId(brand.getId());
				model.setCategoryId(productCategory.getId());
				pdtCategoryBrandRelDOMapper.insert(model);
			}
		});
		this.defineCommonService.clearProductDefineCache();
	}
	
	@Override
	public void deleteBrandFromProductCategory(final ProductCategory productCategory, final Brand brand) throws ExistProductsNotAllowWriteException {
		MyAssert4Business.objectInitialized(productCategory);
		MyAssert4Business.objectInitialized(brand);
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
	
}
