/**
 * 
 */
package com.vanstone.jupin.business.sdk.common;

import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;
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
	
	/**
	 * 获取AbstractAttribute对象并验证
	 * @param id
	 * @return
	 */
	AbstractAttribute getAttributeAndValidate(int id);
	
	/**
	 * 获取Text属性
	 * @param id
	 * @return
	 */
	Attr4Text getAttr4TextAndValidate(int id);
	
	/**
	 * 获取Enum属性
	 * @param id
	 * @return
	 */
	Attr4Enum getAttr4EnumAndValidate(int id);
	
	/**
	 * 获取EnumValue值并验证
	 * @param id
	 * @return
	 */
	Attr4EnumValue getAttr4EnumValueAndValidate(int id);
	
}
