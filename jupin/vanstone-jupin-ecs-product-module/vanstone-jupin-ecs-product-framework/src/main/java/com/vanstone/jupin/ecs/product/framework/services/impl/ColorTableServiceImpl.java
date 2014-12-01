/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.def.BusinessObjectKeyBuilder;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.util.InterProcessMutexCallback;
import com.vanstone.jupin.common.util.ZKUtil;
import com.vanstone.jupin.ecs.product.PDCache;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.services.ColorTableService;
import com.vanstone.jupin.ecs.product.define.services.DefineCommonService;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTSkuColorTableDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTSkuColorTableDO;
import com.vanstone.jupin.framework.cache.JupinRedisRef;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("colorTableService")
@Validated
public class ColorTableServiceImpl extends DefaultBusinessService implements ColorTableService {
	
	/***/
	private static final long serialVersionUID = 1494174772850428828L;
	
	@Autowired
	private PDTSkuColorTableDOMapper pdtSkuColorTableDOMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private DefineCommonService defineCommonService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.ColorTableService#addSkuColor(com.vanstone.jupin.productdefine.attr.sku.Color)
	 */
	@Override
	public Color addColor(final Color color) throws ObjectDuplicateException {
		PDTSkuColorTableDO model = this.pdtSkuColorTableDOMapper.selectByColorName(color.getColorName());
		if (model != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				PDTSkuColorTableDO model = BeanUtil.toPDTSkuColorTableDO(color);
				pdtSkuColorTableDOMapper.insert(model);
				color.setId(model.getId());
			}
		});
		refreshColorTable();
		return color;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.ColorTableService#updateSkuColor(com.vanstone.jupin.productdefine.attr.sku.Color)
	 */
	@Override
	public Color updateColor(final Color skuColor) throws ObjectDuplicateException, ExistProductsNotAllowWriteException{
		MyAssert4Business.objectInitialized(skuColor);
		final Color loadColor = this.getColorAndValidate(skuColor.getId());
		if (!defineCommonService.validateAllowUDOperateColor()) {
			throw new ExistProductsNotAllowWriteException();
		}
		PDTSkuColorTableDO _tempModel = this.pdtSkuColorTableDOMapper.selectByColorName_NotSelf(skuColor.getColorName(), skuColor.getId());
		if (_tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				loadColor.setColorCSS(skuColor.getColorCSS());
				loadColor.setColorName(skuColor.getColorName());
				loadColor.setColorRGB(skuColor.getColorRGB());
				pdtSkuColorTableDOMapper.updateByPrimaryKey(BeanUtil.toPDTSkuColorTableDO(loadColor));
			}
		});
		refreshColorTable();
		return loadColor;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.ColorTableService#getSkuColor(int)
	 */
	@Override
	public Color getColor(final int id) {
		for (int i=0;i<2;i++) {
			Color loadColor = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Color>() {
				@Override
				public Color doInRedis(Jedis jedis) {
					String value = jedis.hget(PDCache.COLOR_TABLE_LIST_KEY, BusinessObjectKeyBuilder.class2key(Color.class, id));
					if (value != null) {
						Gson gson = GsonCreator.create();
						return gson.fromJson(value, Color.class);
					}
					return null;
				}
			});
			if (loadColor != null) {
				return loadColor;
			}
			refreshColorTable();
		}
		return null;
	}
	
	@Override
	public Color getColorAndValidate(int id) {
		Color color = this.getColor(id);
		if (color == null) {
			throw new IllegalArgumentException();
		}
		return color;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.ColorTableService#deleteSkuColor(int)
	 */
	@Override
	public void deleteColor(final int id) throws ExistProductsNotAllowWriteException {
		this.getColorAndValidate(id);
		if (!defineCommonService.validateAllowUDOperateColor()) {
			throw new ExistProductsNotAllowWriteException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				pdtSkuColorTableDOMapper.deleteByPrimaryKey(id);
			}
		});
		refreshColorTable();
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.productdefine.services.ColorTableService#getSkuColors()
	 */
	@Override
	public Collection<Color> getColors() {
		Collection<Color> loadColors = this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallback<Collection<Color>>() {
			@Override
			public Collection<Color> doInRedis(Jedis jedis) {
				boolean exist = jedis.exists(PDCache.COLOR_TABLE_LIST_KEY);
				if (!exist) {
					return null;
				}
				List<String> values = jedis.hvals(PDCache.COLOR_TABLE_LIST_KEY);
				if (values == null || values.size() <=0) {
					return null;
				}
				Collection<Color> colors = new ArrayList<Color>();
				for (String value : values) {
					Gson gson = GsonCreator.create();
					colors.add(gson.fromJson(value, Color.class));
				}
				return colors;
			}
		});
		if (loadColors != null) {
			return loadColors;
		}
		return ZKUtil.executeMutex(PDCache.COLOR_TABLE_LIST_KEY, new InterProcessMutexCallback<Collection<Color>>() {
			
			@Override
			public Collection<Color> doInAcquireMutex(CuratorFramework curatorFramework) {
				//获取到锁
				List<PDTSkuColorTableDO> models = pdtSkuColorTableDOMapper.selectAll();
				if (models == null || models.size() <=0) {
					return null;
				}
				final Collection<Color> colors = new ArrayList<Color>();
				for (PDTSkuColorTableDO model : models) {
					colors.add(BeanUtil.toColor(model));
				}
				redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
					@Override
					public void doInRedisWithoutResult(Jedis jedis) {
						for (Color color : colors) {
							Gson gson = GsonCreator.create();
							jedis.hset(PDCache.COLOR_TABLE_LIST_KEY, BusinessObjectKeyBuilder.class2key(Color.class, color.getId()), gson.toJson(color));
						}
					}
				});
				return colors;
			}
			
			@Override
			public Collection<Color> doInNotAcquireMutex(CuratorFramework curatorFramework) {
				try {
					TimeUnit.SECONDS.sleep(Constants.ZK_BUSINESS_EXECUTE_WAITING_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return getColors();
			}
		});
	}
	
	@Override
	public int refreshColorTable() {
		this.defineCommonService.clearColorTableCache();
		Collection<Color> colors = this.getColors();
		if (colors != null) {
			return colors.size();
		}
		return 0;
	}
}
