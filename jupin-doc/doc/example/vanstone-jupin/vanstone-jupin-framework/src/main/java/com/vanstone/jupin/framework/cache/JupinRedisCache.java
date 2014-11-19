/**
 * 
 */
package com.vanstone.jupin.framework.cache;

import com.vanstone.redis.RedisIdDefine;

/**
 * @author shipeng
 *
 */
public enum JupinRedisCache implements RedisIdDefine {
	
	jupin;
	;

	@Override
	public String getId() {
		return this.toString();
	}
	
}
