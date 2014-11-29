/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.productdefine;

import com.vanstone.common.util.web.PageInfo;
import com.vanstone.jupin.productdefine.ProductCategory;

/**
 * 品类管理 
 * @author shipeng
 */
public interface ProductDefineManager {
	
	/**
	 * 刷新全部品类定义 <br >
	 * 同步分页刷新整体品类定义 : <br >
	 * 1. 品类定义
	 * 2. 品牌定义
	 * 3. 属性定义
	 * 4. 颜色表定义
	 * 5. 尺码表定义
	 */
	void refreshAllCategories();
	
	/**
	 * 检索品类信息
	 * @param key 包含品类名称，id号码
	 * @param pageno
	 * @return
	 */
	PageInfo<ProductCategory> searchProductCategories(String key, int pageno);
	
}
