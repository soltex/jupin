/**
 * 
 */
package com.vanstone.jupin.business.sdk.common;

import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;

/**
 * @author shipeng
 *
 */
public interface CommonSDKManager {
	
	/**
	 * 获取并验证当前Color
	 * @param id
	 * @return
	 */
	Color getColorAndValidate(int id);
	
	/**
	 * 获取并验证
	 * @param id
	 * @return
	 */
	SizeTemplate getSizeTemplateAndValidate(int id);
	
	/**
	 * 获取并验证
	 * @param id
	 * @return
	 */
	Size getSizeAndValidate(int id);
	
	/**
	 * 获取品牌信息并验证
	 * @param id
	 * @return
	 */
	Brand getBrandAndValidate(int id);
	
	/**
	 * 获取并且验证ProductCategoryDetail
	 * @param id
	 * @return
	 */
	ProductCategoryDetail getProductCategoryDetailAndValidate(int id);
	
	/**
	 * 获取Admin对象并验证
	 * @param id
	 * @return
	 */
	Admin getAdminAndValidate(String id);
	
}
