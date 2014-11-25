/**
 * 
 */
package com.vanstone.jupin.productdefine.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.def.BusinessObjectKeyBuilder;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.jupin.productdefine.Brand;
import com.vanstone.jupin.productdefine.ProductCategory;
import com.vanstone.jupin.productdefine.persistence.PDTBrandDOMapper;
import com.vanstone.jupin.productdefine.persistence.PDTProductBrandRelDOMapper;
import com.vanstone.jupin.productdefine.persistence.object.PDTBrandDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTProductBrandRelDOKey;
import com.vanstone.jupin.productdefine.services.BrandService;
import com.vanstone.jupin.productdefine.services.CategoryHasProductsException;
import com.vanstone.jupin.productdefine.services.MustLeafNodeofProductCategoryException;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisTemplate;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * @author shipeng
 */
@Service("brandService")
public class BrandServiceImpl extends DefaultBusinessService implements BrandService {

	/***/
	private static final long serialVersionUID = 1563959938577671056L;
	
	private static Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private PDTBrandDOMapper pdtBrandDOMapper;
	@Autowired
	private PDTProductBrandRelDOMapper pdtProductBrandRelDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ProductDefineCommonService commonService;
	
	@Override
	public Brand addBrand(final Brand brand) throws ObjectDuplicateException {
		PDTBrandDO tempModel = this.pdtBrandDOMapper.selectByBrandName(brand.getBrandName());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTBrandDO model  = BeanUtil.toPDTBrandDO(brand);
				pdtBrandDOMapper.insert(model);
				brand.setId(model.getId());
			}
		});
		return brand;
	}
	
	@Override
	public Brand addBrand(final Brand brand, final Collection<ProductCategory> productCategories) throws MustLeafNodeofProductCategoryException, ObjectDuplicateException {
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
					PDTProductBrandRelDOKey key = new PDTProductBrandRelDOKey();
					key.setBrandId(brand.getId());
					key.setCategoryId(category.getId());
					pdtProductBrandRelDOMapper.insert(key);
				}
				return brand;
			}
		});
	}
	
	/**
	 * 验证节点 
	 * @param productCategories
	 * @return
	 * @throws MustLeafNodeofProductCategoryException
	 */
	private void _validateProdudctCategories(Collection<ProductCategory> productCategories) throws MustLeafNodeofProductCategoryException {
		if (productCategories == null || productCategories.size() <=0) {
			return;
		}
		Collection<Integer> productCategoryIds = new ArrayList<Integer>();
		for (ProductCategory category : productCategories) {
			if (!category.isLeafable()) {
				throw new MustLeafNodeofProductCategoryException();
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
		return ZKUtil.executeMutex(Constants.buildZKLockMutexNodePath(BusinessObjectKeyBuilder.class2key(Brand.class, id)), new InterProcessMutexCallback<Brand>() {
			@Override
			public Brand doInAcquireMutex(CuratorFramework curatorFramework) {
				PDTBrandDO pdtBrandDO = pdtBrandDOMapper.selectByPrimaryKey(id);
				if (pdtBrandDO == null) {
					return null;
				}
				Brand brand = BeanUtil.toBrand(pdtBrandDO);
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
					String key = BusinessObjectKeyBuilder.class2key(Brand.class, id);
					keies.add(key);
				}
				List<String> values = jedis.mget(keies.toArray(new String[keies.size()]));
				int index = 0;
				for (Integer id : ids) {
					String value = values.get(index);
					Brand brand = null;
					if (value != null && value.length() > 0) {
						Gson gson = GsonCreator.create();
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
	public void deleteBrandLogoImage(final int id) {
		final Brand brand = this.getBrand(id);
		if (brand == null) {
			throw new IllegalArgumentException();
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
	}
	
	@Override
	public Brand updateBrandBaseInfo(final int brandId, final String brandName, final String brandNameEN, final String content, final boolean systemable) throws ObjectDuplicateException {
		final Brand loadBrand = this.getBrand(brandId);
		if (loadBrand == null) {
			throw new IllegalArgumentException();
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
		return loadBrand;
	}
	
	@Override
	public Brand updateBrandLogoInfo(final int brandId, final ImageBean imageBean) {
		final Brand loadBrand = this.getBrand(brandId);
		if (loadBrand == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadBrand.setLogoImage(imageBean);
				pdtBrandDOMapper.updateLogoInfo(brandId, imageBean.getWeedFile().getFileid(), imageBean.getWeedFile().getExtName(), imageBean.getWidth(), imageBean.getHeight());
			}
		});
		return loadBrand;
	}
	
	@Override
	public void deleteBrand(final int brandId) throws CategoryHasProductsException {
		Brand loadBrand = this.getBrand(brandId);
		if (loadBrand == null) {
			throw new IllegalArgumentException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtBrandDOMapper.deleteByPrimaryKey(brandId);
			}
		});
		ServiceUtil<Brand, Integer> serviceUtil = new ServiceUtil<Brand, Integer>();
		serviceUtil.deleteFromRedis(redisTemplate, JupinRedisRef.Jupin_Core, loadBrand);
		if (loadBrand.getLogoImage() != null) {
			WeedFSClient weedFSClient = new WeedFSClient();
			weedFSClient.delete(loadBrand.getLogoImage().getWeedFile().getFileid());
			LOG.info("DELETE WeedFS {}", loadBrand.getLogoImage().getWeedFile().getFileid());
		}
	}
	
	@Override
	public void forceDeleteBrand(int brandId) {
		// TODO 强制删除Brand，暂时不实现
		
	}

	@Override
	public Collection<Brand> getBrandsWithStat(ProductCategory productCategory, String key, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalBrands(ProductCategory productCategory, String key) {
		return 0;
	}
	
}
