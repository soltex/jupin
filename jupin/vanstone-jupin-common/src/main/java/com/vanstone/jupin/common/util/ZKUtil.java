/**
 * 
 */
package com.vanstone.jupin.common.util;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.common.MyAssert;
import com.vanstone.zk.ZKManager;

/**
 * @author shipeng
 */
public class ZKUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(ZKUtil.class);
	
	/**
	 * ZK中执行信号量等待
	 * @param curatorFramework
	 * @param mutexValue
	 * @param waitTime
	 * @param callback
	 * @return
	 */
	public static <T extends Object> T executeMutex(CuratorFramework curatorFramework, String mutexValue, Integer waitTime, InterProcessMutexCallback<T> callback) {
		MyAssert.hasText(mutexValue);
		MyAssert.notNull(curatorFramework);
		if (waitTime == null || waitTime <0) {
			waitTime = 0;
		}
		MyAssert.notNull(callback);
		LOG.info("Start mutex ,{}" , mutexValue);
		InterProcessMutex mutex = new InterProcessMutex(curatorFramework, mutexValue);
		try {
			if (!mutex.acquire(waitTime, TimeUnit.SECONDS)) {
				LOG.info("Not Acequire mutex,{}",mutexValue);
				return callback.doInNotAcquireMutex(curatorFramework);
			}
			LOG.info("Acequire mutex,{}",mutexValue);
			return callback.doInAcquireMutex(curatorFramework);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ZK Mutex Acquire Error ,{}" , mutexValue);
			return null;
		} finally {
			try {
				mutex.release();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("ZK Release Mutex Acquire Error, {}" , mutexValue);
				return  null;
			}
		}
	}
	
	/**
	 * 使用内置ZKManager管理，并不等待立即返回
	 * @param mutexValue
	 * @param callback
	 * @return
	 */
	public static <T extends Object> T executeMutex(String mutexValue, InterProcessMutexCallback<T> callback) {
		CuratorFramework _curatorFramework = ZKManager.getInstance().getCuratorFramework();
		return executeMutex(_curatorFramework, mutexValue, null, callback);
	}
	
}
