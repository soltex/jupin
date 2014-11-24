/**
 * 
 */
package com.vanstone.jupin.common.util;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author shipeng
 *
 */
public interface InterProcessMutexCallback<T extends Object> {
	
	/**
	 *获取到锁执行代码区
	 * @param curatorFramework
	 * @return
	 */
	public T doInAcquireMutex(CuratorFramework curatorFramework);
	
	/**
	 * 未获取到锁执行代码区
	 * @param curatorFramework
	 * @return
	 */
	public T doInNotAcquireMutex(CuratorFramework curatorFramework);
	
}
