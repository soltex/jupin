/**
 * 
 */
package com.vanstone.jupin.common;


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
	
	/**默认品类根节点*/
	public static final String DEFAULT_PRODUCT_CATEGORY_ROOT_NODE_NAME = "ROOT";
	
	/**品牌名称中的特殊字符进行替换*/
	public static final String BRAND_NAME_CHARS = " ，,#'~!@#$%^&*(){}|.。/?-　";
	
	/**管理员默认页面大小*/
	public static final int ADMIN_DEFAULT_PAGESIZE = 20;
	
}
