/**
 * 
 */
package com.vanstone.jupin.common;

import java.nio.charset.Charset;



/**
 * jupin常量信息
 * @author shipeng
 */
public class Constants {
	
	/*=========================================== Common ===========================================*/
	
	/**全局系统编码*/
	public static final String SYSTEM_CHARSET_STRING = "UTF-8";
	
	/**全局系统编码Charset类型*/
	public static final Charset SYSTEM_CHARSET = Charset.forName(SYSTEM_CHARSET_STRING);
	
	/**系统默认排序初始值*/
	public static final int SYS_DEFAULT_SORT = 0;
	
	/**默认根节点值*/
	public static final String ROOT_PRODUCT_CATEGORY_NODE_ID = null;
	
	/**信号量节点前缀路径*/
	public static final String ZK_LOCK_MUTEXT_NODE_PATH_PREFIX  = "/vanstone/jupin/mutex";
	
	/**锁等待时间*/
	public static final int ZK_LOCK_WAITING_TIME = 0;
	
	/**业务执行等待时间 单位 秒*/
	public static final int ZK_BUSINESS_EXECUTE_WAITING_TIME =2;
	
	/**默认品类根节点*/
	public static final String DEFAULT_PRODUCT_CATEGORY_ROOT_NODE_NAME = "ROOT";
	
	/**品牌名称中的特殊字符进行替换*/
	public static final String BRAND_NAME_CHARS = " ，,#'~!@#$%^&*(){}|.。/?-　";
	
	/**管理员默认页面大小*/
	public static final int ADMIN_DEFAULT_PAGESIZE = 20;
	
	/*===========================================MessageBox===========================================*/
	
	/**默认信箱名称*/
	public static final String DEFAULT_MESSGEBOX_NAME = "DEFAULT_NAME";
	
	/**默认信箱组名称*/
	public static final String DEFAULT_MESSGEBOX_GROUP = "DEFAULT_GROUP";
	
	/**信箱前缀*/
	public static final String MESSAGE_BOX_PREFIX = "MB:";
	
	/**信箱分组前缀*/
	public static final String GROUP_NAME_PREFIX = "G:";
	
	/**信箱名称前缀*/
	public static final String NAME_PREFIX = "NAME:";
	
	/*===========================================Authority===========================================*/
	
	/**管理员用户名*/
	public static final String DEFAULT_ADMIN_NAME = "admin";
	
	/**管理员密码*/
	public static final String DEFAULT_ADMIN_PASSWORD = "admin";

	/**Jupin Admin Session Admin 名称*/
	public static final String ADMIN_IN_SESSIO_NAME = "jupin.adminservice.session.admin";
	
}
