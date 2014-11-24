/**
 * 
 */
package com.vanstone.jupin.framework.cache;

import com.vanstone.redis.RedisIdDefine;

/**
 * @author shipeng
 *
 */
public enum JupinRedisRef implements RedisIdDefine {
	
	Jupin_Core;

	@Override
	public String getId() {
		return this.toString();
	}
	
}
