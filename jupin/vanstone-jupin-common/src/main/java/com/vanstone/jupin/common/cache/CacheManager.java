/**
 * 
 */
package com.vanstone.jupin.common.cache;

/**
 * 缓冲管理器
 * @author shipeng
 */
public class CacheManager {
	
	private static class CacheManagerInstance {
		private static final CacheManager instance = new CacheManager();
	}
	
	private CacheManager() {}
	
	/**
	 * 获取CacheManager
	 * @return
	 */
	public static CacheManager getCacheManager() {
		return CacheManagerInstance.instance;
	}
	
	
}
