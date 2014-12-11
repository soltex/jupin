/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.services.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import redis.clients.jedis.Jedis;

import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.common.cache.JupinRedisRef;
import com.vanstone.jupin.common.util.BoolUtil;
import com.vanstone.jupin.ecs.product.PDCache;
import com.vanstone.jupin.ecs.product.define.services.DefineCommonService;
import com.vanstone.jupin.ecs.product.framework.persistence.PDTCategoryDOMapper;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryDO;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * @author shipeng
 */
@Service("defineCommonService")
@Validated
public class DefineCommonServiceImpl extends DefaultBusinessService implements DefineCommonService {
	
	/***/
	private static final long serialVersionUID = -6551215833173153318L;
	
	private static Logger LOG = LoggerFactory.getLogger(DefineCommonServiceImpl.class);
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PDTCategoryDOMapper pdtCategoryDOMapper;
	
	@Override
	public void clearCategoryDefineCache() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys(PDCache.PRODUCT_CATEGORY_CACHE_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					jedis.del(keies.toArray(new String[keies.size()]));
					LOG.info("Clear All Category Define Cache In Redis.");
				}
			}
		});
	}
	
	@Override
	public void clearColorTableCache() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				if (jedis.exists(PDCache.COLOR_TABLE_LIST_KEY)) {
					jedis.del(PDCache.COLOR_TABLE_LIST_KEY);
					LOG.info("Clear Color Table Cache In Redis.");
				}
			}
		});
	}

	@Override
	public void clearSizeTableCache() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys(PDCache.SIZE_CACHE_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					jedis.del(keies.toArray(new String[keies.size()]));
					LOG.info("Clear All Size Define Cache In Redis.");
				}
			}
		});
	}

	@Override
	public void clearBrandCache() {
		this.redisTemplate.executeInRedis(JupinRedisRef.Jupin_Core, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys(PDCache.BRAND_CACHE_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					jedis.del(keies.toArray(new String[keies.size()]));
					LOG.info("Clear All Brand Define Cache In Redis.");
				}
			}
		});
	}

	@Override
	public void clearProductDefineCache() {
		clearCategoryDefineCache();
		clearColorTableCache();
		clearSizeTableCache();
		clearBrandCache();
	}

	@Override
	public boolean validateAllowUDOperateColor() {
		List<PDTCategoryDO> tempModels = this.pdtCategoryDOMapper.selectByColorable_ExistProduct(BoolUtil.parseBoolean(true), BoolUtil.parseBoolean(true));
		if (tempModels != null && tempModels.size() >0) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean validateAllowUDOperateSizeTemplate(int sizeTemplateID) {
		List<PDTCategoryDO> tempModels = this.pdtCategoryDOMapper.selectBySizeTemplateId_ExistProduct(sizeTemplateID, BoolUtil.parseBoolean(true));
		if (tempModels != null && tempModels.size() >0) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean validateAllowUDOperateBrand(int brandID) {
		List<PDTCategoryDO> tempModels = this.pdtCategoryDOMapper.selectByBrandId_ExistProduct(brandID, BoolUtil.parseBoolean(true));
		if (tempModels != null && tempModels.size() >0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validateAllowUDOperateAttribute(int attributeID) {
		List<PDTCategoryDO> tempModels = this.pdtCategoryDOMapper.selectByAttributeId_ExistProduct(attributeID, BoolUtil.parseBoolean(true));
		if (tempModels != null && tempModels.size() >0) {
			return false;
		}
		return true;
	}
	
}
