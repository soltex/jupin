/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute;

import java.util.Set;

import com.vanstone.common.MyAssert;


/**
 * @author shipeng
 */
public class AttributeBuilder {
	
	/**
	 * 创建文本类型属性
	 * @param attributeName 属性名称
	 * @param listshowable 是否列表显示
	 * @param requiredable 是否必填
	 * @return
	 */
	public static Attr4Text newText(String attributeName, boolean listshowable, boolean requiredable) {
		Attr4Text attr4Text = new Attr4Text();
		attr4Text.setAttributeName(attributeName);
		attr4Text.setListshowable(listshowable);
		attr4Text.setRequiredable(requiredable);
		return attr4Text;
	}
	
	/**
	 * 创建枚举类型基本属性
	 * @return
	 */
	public static Attr4Enum newBaseEnum(String attributeName, boolean listshowable, boolean requiredable, boolean searchable, boolean multiselectable, Set<String> values) {
		MyAssert.notEmpty(values);
		Attr4Enum attr = new Attr4Enum();
		attr.setAttributeName(attributeName);
		attr.setListshowable(listshowable);
		attr.setRequiredable(requiredable);
		attr.setSearchable(searchable);
		attr.setMultiselectable(multiselectable);
		int index = 0;
		for (String value : values) {
			attr.putValue(index, value);
			index++;
		}
		return attr;
	}
	
}
