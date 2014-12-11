/**
 * 
 */
package com.vanstone.jupin.common.util;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;
import com.vanstone.jupin.common.Constants;
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
		String newMutexValue = buildZKLockMutexNodePath(mutexValue);
		InterProcessMutex mutex = new InterProcessMutex(curatorFramework, newMutexValue);
		try {
			if (!mutex.acquire(waitTime, TimeUnit.SECONDS)) {
				LOG.info("Not Acequire mutex,{}",newMutexValue);
				return callback.doInNotAcquireMutex(curatorFramework);
			}
			LOG.info("Acequire mutex,{}",newMutexValue);
			return callback.doInAcquireMutex(curatorFramework);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("ZK Mutex Acquire Error ,{}" , newMutexValue);
			return null;
		} finally {
			if (mutex != null) {
				try {
					if (mutex.isAcquiredInThisProcess()) {
						mutex.release();
					}
				} catch (Exception e) {
					e.printStackTrace();
					LOG.error("ZK Release Mutex Acquire Error, {}" , newMutexValue);
					return  null;
				} finally {
					mutex = null;
				}
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
	
	/**
	 * 建立ZK锁信号量路径
	 * @param node
	 * @return
	 */
	public static String buildZKLockMutexNodePath(String node) {
		MyAssert.hasText(node);
		if (node.endsWith("/")) {
			node = StringUtils.substring(node, 0, node.length()-1);
		}
		if (!node.startsWith("/")) {
			node = "/" + node;
		}
		return Constants.ZK_LOCK_MUTEXT_NODE_PATH_PREFIX + node;
	}
	
	/**
	 * 建立ZK锁信号量路径
	 * @param businessObject
	 */
	public static final String buildZKLockMutexNodePath(AbstractBusinessObject businessObject) {
		MyAssert4Business.objectInitialized(businessObject);
		String node = businessObject.getKey();
		return buildZKLockMutexNodePath("/" + node);
	}
	
}
