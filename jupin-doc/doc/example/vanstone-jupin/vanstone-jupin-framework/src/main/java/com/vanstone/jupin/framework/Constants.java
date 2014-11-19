/**
 * 
 */
package com.vanstone.jupin.framework;

import java.nio.charset.Charset;

/**
 * @author shipeng
 *
 */
public class Constants {
	
	/** 默认根节点产品分类ID值*/
	public static final int DEFAULT_ROOT_PRODUCT_CATEGORY_ID = 1;
	
	/** 默认根节点产品分类名称 */
	public static final String DEFAULT_ROOT_RPODUCT_CATEGORY_NAME = "默认根栏目";
	
	/** 产品属性Redis KEY前缀 */
	public static final String PRODUCT_ATTRIBUTE_CACHE_PREFIX ="jupin.productdef.attribute.id_";
	
	/** 品类Redis KEY 前缀 */
	public static final String PRODUCT_CATEGORY_CACHE_PREFIX = "jupin.productdef.category.id_";
	
	/** 系统默认编码字符串 */
	public static final String SYS_DEFAULT_CHARSET_STRING = "UTF-8";
	
	/** 系统默认编码Charset */
	public static final Charset SYS_DEFAULT_CHARSET = Charset.forName(SYS_DEFAULT_CHARSET_STRING);
	
}
