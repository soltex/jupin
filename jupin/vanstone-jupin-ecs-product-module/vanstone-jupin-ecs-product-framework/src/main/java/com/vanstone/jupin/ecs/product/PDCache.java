/**
 * 
 */
package com.vanstone.jupin.ecs.product;

/**
 * @author shipeng
 */
public class PDCache {
	
	/**品类缓冲前缀*/
	public static final String PRODUCT_CATEGORY_CACHE_PREFIX = "PC_";
	
	/**根节点*/
	public static final String PRODUCT_CATEGORY_ROOT_NODE = PRODUCT_CATEGORY_CACHE_PREFIX + "ROOT";
	
	/**属性缓冲前缀*/
	public static final String PRODUCT_CATEGORY_ATTRIBUTE_PREFIX = PRODUCT_CATEGORY_CACHE_PREFIX + "ATTR_";
	
	/**枚举属性值缓冲前缀*/
	public static final String PRODUCT_CATEGORY_ATTRIBUTE_VALUE_PREFIX = PRODUCT_CATEGORY_ATTRIBUTE_PREFIX + "VAL_";
	
	/**颜色表集合Key-写入到Hash中*/
	public static final String COLOR_TABLE_LIST_KEY = "COLOR_LIST";
	
	/**尺码表集合模板前缀*/
	public static final String SIZE_CACHE_PREFIX = "SIZE_CACHE_";
	
	/**品牌缓冲定义前缀*/
	public static final String BRAND_CACHE_PREFIX = "BRAND_CACHE_";
	
	/**
	 * 获取尺码Key
	 * @param id
	 * @return
	 */
	public static String getSizeKey(int id) {
		return SIZE_CACHE_PREFIX + id;
	}
	
	/**
	 * 获取品类Key前缀
	 * @param id
	 * @return
	 */
	public static String getCategoryKey(int id) {
		return PRODUCT_CATEGORY_CACHE_PREFIX + id;
	}
	
	/**
	 * 获取品类根节点Key
	 * @return
	 */
	public static String getRootCategoryKey() {
		return PRODUCT_CATEGORY_ROOT_NODE;
	}
	
	/**
	 * 获取品类属性Key
	 * @param attributeID
	 * @return
	 */
	public static String getAttributeKey(int attributeID) {
		return PRODUCT_CATEGORY_ATTRIBUTE_PREFIX + attributeID;
	}
	
	/**
	 * 获取枚举属性值缓冲Key
	 * @param enumValueID
	 * @return
	 */
	public static String getEnumAttributeValueKey(int enumValueID) {
		return PRODUCT_CATEGORY_ATTRIBUTE_VALUE_PREFIX + enumValueID;
	}
	
	/**
	 * 获取品牌缓冲KEY
	 * @param brandID
	 * @return
	 */
	public static String getBrandKey(int brandID) {
		return BRAND_CACHE_PREFIX + brandID;
	}
	
}
