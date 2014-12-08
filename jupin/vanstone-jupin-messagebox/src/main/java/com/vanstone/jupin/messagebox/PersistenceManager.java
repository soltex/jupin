/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.vanstone.common.MyAssert;
import com.vanstone.framework.business.ServiceManagerFactory;
import com.vanstone.jupin.common.cache.JupinRedisRef;
import com.vanstone.redis.RedisCallback;
import com.vanstone.redis.RedisCallbackWithoutResult;
import com.vanstone.redis.RedisTemplate;

/**
 * 持久管理器
 * @author shipeng
 */
public class PersistenceManager {
	
	public static final class PersistenceManagerInstance {
		private static final PersistenceManager instance = new PersistenceManager();
	}
	
	private PersistenceManager(){}
	
	public static PersistenceManager getInstance() {
		return PersistenceManagerInstance.instance;
	}
	
	/**
	 * 写入消息队列
	 * @param message
	 * @return
	 */
	public void pushMessage(final String key, final String messageJson) {
		MyAssert.hasText(key);
		MyAssert.hasText(messageJson);
		RedisTemplate redisTemplate = getRedisTemplateAndValidate();
		redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				jedis.lpush(key, messageJson);
			}
		});
	}
	
	/**
	 * 弹出消息队列
	 * @param key
	 */
	public Message popMessage(final String key) {
		MyAssert.hasText(key);
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		return redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallback<Message>() {
			@Override
			public Message doInRedis(Jedis jedis) {
				String value = jedis.rpop(key);
				if (value == null || value.equals("")) {
					return null;
				}
				return MessageHelper.parseMessageByJson(value);
			}
		});
	}
	
	/**
	 * 获取信箱数量
	 * @return
	 */
	public int getMessageBoxCount() {
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		return redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallback<Integer>() {
			@Override
			public Integer doInRedis(Jedis jedis) {
				Set<String> keies = jedis.keys(Constants.MESSAGE_BOX_PREFIX + "*");
				if (keies != null && keies.size() >0) {
					return keies.size();
				}
				return 0;
			}
		});
	}
	
	/**
	 * 获取信箱数量
	 * @param messageBox
	 * @return
	 */
	public long getMessageCount(final String key) {
		MyAssert.hasText(key);
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		return redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallback<Long>() {
			@Override
			public Long doInRedis(Jedis jedis) {
				return jedis.llen(key);
			}
		});
	}
	
	/**
	 * 获取Message Values
	 * @param key
	 * @return
	 */
	public List<String> getMessageValues(final String key) {
		MyAssert.hasText(key);
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		return redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(Jedis jedis) {
				return jedis.lrange(key, 0, -1);
			}
		});
	}
	
	/**
	 * 获取信箱Keies值
	 * @return
	 */
	public Collection<String> getMessageBoxKeies() {
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		return redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallback<Collection<String>>() {
			@Override
			public Collection<String> doInRedis(Jedis jedis) {
				Set<String> keies = jedis.keys(Constants.MESSAGE_BOX_PREFIX + "*");
				return keies;
			}
		});
	}
	
	/**
	 * 通过Key删除
	 * @param key
	 */
	public void deleteBykey(final String key) {
		MyAssert.hasText(key);
		RedisTemplate redisTemplate = this.getRedisTemplateAndValidate();
		redisTemplate.executeInRedis(JupinRedisRef.Jupin_MessageBox, new RedisCallbackWithoutResult() {
			@Override
			public void doInRedisWithoutResult(Jedis jedis) {
				jedis.del(key);
			}
		});
	}
	
	/**
	 * 获取RedisTemplate
	 * @return
	 */
	public RedisTemplate getRedisTemplateAndValidate() {
		RedisTemplate redisTemplate = ServiceManagerFactory.getInstance().getService("redisTemplate");
		if (redisTemplate == null) {
			throw new IllegalArgumentException();
		}
		return redisTemplate;
	}
}
