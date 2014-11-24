/**
 * 
 */
package com.vanstone.jupin.common;

import org.apache.commons.lang.StringUtils;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;

/**
 * jupin常量信息
 * @author shipeng
 */
public class Constants {
	
	/**系统默认排序初始值*/
	public static final int SYS_DEFAULT_SORT = 0;
	
	/**默认根节点值*/
	public static final String ROOT_PRODUCT_CATEGORY_NODE_ID = null;
	
	public static final String ZK_LOCK_MUTEXT_NODE_PATH_PREFIX  = "/vanstone/jupin/mutex";
	
	/**锁等待时间*/
	public static final int ZK_LOCK_WAITING_TIME = 0;
	
	/**业务执行等待时间 单位 秒*/
	public static final int ZK_BUSINESS_EXECUTE_WAITING_TIME = 1;
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
		if (node.startsWith("/")) {
			node = "/" + node;
		}
		return ZK_LOCK_MUTEXT_NODE_PATH_PREFIX + node;
	}
	
	/**
	 * 建立ZK锁信号量路径
	 * @param businessObject
	 */
	public static final String buildZKLockMutexNodePath(AbstractBusinessObject businessObject) {
		MyAssert4Business.objectInitialized(businessObject);
		String node = businessObject.getKey();
		return ZK_LOCK_MUTEXT_NODE_PATH_PREFIX + "/" + node;
	}
	
}
