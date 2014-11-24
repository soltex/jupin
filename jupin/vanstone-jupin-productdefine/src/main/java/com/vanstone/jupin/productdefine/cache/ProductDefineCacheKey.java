/**
 * 
 */
package com.vanstone.jupin.productdefine.cache;

/**
 * @author shipeng
 */
public class ProductDefineCacheKey {
	
	/**根节点*/
	public static final String PRODUCT_CATEGORY_ROOT_NODE_PREFIX = "PC_ROOT_";
	
	/**颜色表集合Key-写入到Hash中*/
	public static final String COLOR_TABLE_LIST_KEY = "COLOR_LIST";
	
	/**尺码表集合模板前缀*/
	public static final String SIZE_TEMLATE_PREFIX = "SIZE_TEMPALTE_";
	
	/**
	 * 获取尺码模板Cache KEY
	 * @param id
	 * @return
	 */
	public static String getSizeTemplateKey(int id) {
		if (id <=0) {
			throw new IllegalArgumentException();
		}
		return SIZE_TEMLATE_PREFIX + id;
	}
}
