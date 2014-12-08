/**
 * 
 */
package com.vanstone.jupin.business.sdk.common;

import java.util.Collection;

import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.messagebox.Message;

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
	ProductCategoryDetail getAndValidateProductCategoryDetail(int id);
	
	/**
	 * 读取消息-默认信箱内
	 * @return
	 */
	Message readMessage();
	
	/**
	 * 读取全部消息-默认信箱内
	 * @return
	 */
	Collection<Message> readAllMessages();
	
	/**
	 * 读取消息
	 * @param messageBoxGroup
	 * @param messageBoxName
	 * @return
	 */
	Message readMessage(String messageBoxGroup, String messageBoxName);
	
	/**
	 * 读取全部消息
	 * @param messageBoxGroup
	 * @param messageBoxName
	 * @return
	 */
	Collection<Message> readAllMessages(String messageBoxGroup, String messageBoxName);
}
