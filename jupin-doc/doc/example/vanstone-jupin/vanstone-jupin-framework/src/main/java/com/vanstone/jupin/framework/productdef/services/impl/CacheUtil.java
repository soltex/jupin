/**
 * 
 */
package com.vanstone.jupin.framework.productdef.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.common.MyAssert;
import com.vanstone.framework.business.services.ServiceUtil;
import com.vanstone.jupin.framework.Constants;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.Attr4Enum;
import com.vanstone.jupin.framework.productdef.Attr4Lang;
import com.vanstone.jupin.framework.productdef.AttributeType;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisIdDefine;
import com.vanstone.redis.RedisTemplate;

/**
 * Product Def 模块缓冲定义
 * 
 * @author shipeng
 */
public class CacheUtil {

	private static Logger LOG = LoggerFactory.getLogger(CacheUtil.class);
	
	/**
	 * 建立Attribute 缓冲 Key
	 * 
	 * @param aid
	 * @return
	 */
	public static String buildAttributeKey(int aid) {
		return Constants.PRODUCT_ATTRIBUTE_CACHE_PREFIX + aid;
	}

	/**
	 * 通过AttriuteId获取属性
	 * 
	 * @param aid
	 * @param redisTemplate
	 * @param redisIdDefine
	 * @return
	 */
	public static AbstractAttribute getAttributeFromCache(int aid, RedisTemplate redisTemplate, RedisIdDefine redisIdDefine) {
		ServiceUtil<AbstractAttribute, String> serviceUtil = new ServiceUtil<AbstractAttribute, String>();
		String key = buildAttributeKey(aid);
		String value = serviceUtil.getRedisValue(redisTemplate, redisIdDefine, key);
		if (value == null || "".equals(value)) {
			return null;
		}
		return getAttributeByJsonValue(value, redisTemplate, redisIdDefine);
	}
	
	/**
	 * 通过Json获取AbstractAttribute
	 * @param value
	 * @param redisTemplate
	 * @param redisIdDefine
	 * @return
	 */
	public static AbstractAttribute getAttributeByJsonValue(String value, RedisTemplate redisTemplate, RedisIdDefine redisIdDefine) {
		if (value == null || "".equals(value)) {
			return null;
		}
		Gson gson = GsonCreator.create();
		JsonObject jsonObject = gson.fromJson(value, JsonObject.class);
		String strAttributeType = jsonObject.get("attributeType").getAsString();
		LOG.debug("attribute type value = " + strAttributeType);
		AttributeType attributeType = Enum.valueOf(AttributeType.class, strAttributeType);
		if (attributeType.equals(AttributeType.Lang)) {
			// Lang类型
			return gson.fromJson(value, Attr4Lang.class);
		} else {
			return gson.fromJson(value, Attr4Enum.class);
		}
	}
	
	/**
	 * 建立品类缓冲前缀
	 * @param cid
	 * @return
	 */
	public static final String buildCategoryKey(int cid) {
		return Constants.PRODUCT_CATEGORY_CACHE_PREFIX + cid;
	}
	
	/**
	 * 清理全部定义在Redis中的品类定义
	 * @param redisTemplate
	 * @param redisIdDefine
	 */
	public static void clearAllProductCategoriesInCache(RedisTemplate redisTemplate, RedisIdDefine redisIdDefine) {
		MyAssert.notNull(redisTemplate);
		MyAssert.notNull(redisIdDefine);
		redisTemplate.executeInRedis(redisIdDefine, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys(Constants.PRODUCT_CATEGORY_CACHE_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					jedis.del(keies.toArray(new String[keies.size()]));
					LOG.info("Clear All Product Categories in redis.");
				}
			}
		});
	}
	
	/**
	 * 清理存在于缓冲中的
	 * @param redisTemplate
	 * @param redisIdDefine
	 */
	public static void clearAllProductAttributesInCache(RedisTemplate redisTemplate, RedisIdDefine redisIdDefine) {
		MyAssert.notNull(redisTemplate);
		MyAssert.notNull(redisIdDefine);
		redisTemplate.executeInRedis(redisIdDefine, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				Set<String> keies = jedis.keys(Constants.PRODUCT_ATTRIBUTE_CACHE_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					jedis.del(new String[keies.size()]);
					LOG.info("Clear All Attributes in redis.");
				}
			}
		});
	}
	
	public static byte[][] getKeies(List<Integer> ids) {
		List<byte[]> keies = new ArrayList<byte[]>();
		for (Integer id : ids) {
			keies.add(buildAttributeKey(id).getBytes(Constants.SYS_DEFAULT_CHARSET));
		}
		return keies.toArray(new byte[ids.size()][]);
	}
	
}
