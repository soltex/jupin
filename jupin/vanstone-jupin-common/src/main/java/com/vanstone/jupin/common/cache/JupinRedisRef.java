/**
 * 
 */
package com.vanstone.jupin.common.cache;

import com.vanstone.redis.RedisIdDefine;

/**
 * @author shipeng
 *
 */
public enum JupinRedisRef implements RedisIdDefine {
	
	Jupin_Core,Jupin_MessageBox;
	
	@Override
	public String getId() {
		return this.toString();
	}
	
}
