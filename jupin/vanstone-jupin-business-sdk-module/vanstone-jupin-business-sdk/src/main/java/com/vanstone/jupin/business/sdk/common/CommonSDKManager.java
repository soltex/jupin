/**
 * 
 */
package com.vanstone.jupin.business.sdk.common;

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
	Color getAndValidateColor(int id);
	
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
	
}
