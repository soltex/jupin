/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.services;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;

/**
 * 属性业务API
 * @author shipeng
 */
public interface AttributeService {
	
	/**
	 * 添加文本类型属性
	 * @param attr4Text
	 * @return
	 */
	Attr4Text addAttr4Text(@NotNull Attr4Text attr4Text);
	
	/**
	 * 添加枚举类型
	 * @param attr4Enum
	 * @param objectValues
	 * @return
	 */
	Attr4Enum addAttr4Enum(@NotNull Attr4Enum attr4Enum);
	
	/**
	 * 新增枚举值
	 * @param attr4Enum
	 * @param objectValue
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Attr4Enum appendAttr4EnumValue(@NotNull Attr4Enum attr4Enum, String objectValue, Integer sort) throws ObjectDuplicateException;
	
	/**
	 * 更新枚举值信息
	 * @param attr4EnumValue
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Attr4Enum updateAttr4EnumValue(@NotNull Attr4EnumValue attr4EnumValue) throws ObjectDuplicateException;
	
	/**
	 * 删除枚举值,当枚举值不存在了，一并删除属性
	 * @param enumValueId
	 * @return
	 */
	void deleteAttr4EnumValue(int enumValueId);
	
	/**
	 * 获取枚举值
	 * @param enumValueID
	 * @return
	 */
	Attr4EnumValue getAttr4EnumValue(int enumValueID);
	
	/**
	 * 更新文本属性信息
	 * @param attr4Text
	 * @return
	 */
	Attr4Text updateAttr4Text(@NotNull Attr4Text attr4Text);
	
	/**
	 * 更新基本信息
	 * @param attr4Enum
	 * @return
	 */
	Attr4Enum updateBaseAttr4Enum(@NotNull Attr4Enum attr4Enum);
	
	/**
	 * 获取属性
	 * @param id
	 * @return
	 */
	AbstractAttribute getAttribute(int id);
	
	/**
	 * 通过一组ID获取属性Map
	 * @param ids
	 * @return
	 */
	Map<Integer, AbstractAttribute> getAttributesByIDsMap(@NotNull Collection<Integer> ids);
	
	/**
	 * 刷新缓冲中的Attribute
	 * @param id
	 * @return
	 */
	AbstractAttribute refreshAttribute(int id);
	
	/**
	 * 检索属性
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<AbstractAttribute> getAttributesByCondition(AttributeCondition condition, int offset, int limit);
	
	/**
	 * 检索条件数量
	 * @param condition
	 * @return
	 */
	int getTotalAttributesByCondition(AttributeCondition condition);
	
	/**
	 * 删除属性
	 * @param attribute
	 * @throws ExistProductsNotAllowWriteException
	 */
	void deleteAttribute(@NotNull AbstractAttribute attribute) throws ExistProductsNotAllowWriteException;
}
