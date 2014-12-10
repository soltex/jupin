/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.services;

import org.apache.commons.lang.StringUtils;

import com.vanstone.jupin.common.entity.AbstractCondition;
import com.vanstone.jupin.ecs.product.define.attribute.AttributeType;

/**
 * 是否包含检索条件
 * @author shipeng
 */
public class AttributeCondition extends AbstractCondition {
	/**关键字*/
	private String key;
	/**属性类型*/
	private AttributeType attributeType;
	/**是否显示在列表页上*/
	private Boolean listshowable;
	/**是否为必填项*/
	private Boolean requiredable;
	
	
	//============================enum====================
	/**非多选*/
	private Boolean multiselectable;
	/**是否用于搜索*/
	private Boolean searchable;
	
	
	
	
	
	
	private AttributeCondition() {}
	
	@Override
	public boolean isEmpty() {
		if (this.attributeType.equals(AttributeType.Text)) {
			if (StringUtils.isEmpty(this.getKey()) && this.getListshowable() == null && this.getRequiredable() == null) {
				return true;
			}
			return false;
		}else {
			if (StringUtils.isEmpty(this.getKey()) && this.getListshowable() == null && this.getRequiredable() == null && this.getMultiselectable() == null && this.getSearchable() == null) {
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 创建文本类型检索条件
	 * @param key
	 * @param listshowable
	 * @param requiredable
	 * @return
	 */
	public static AttributeCondition createTextTypeCondition(String key,Boolean listshowable, Boolean requiredable) {
		AttributeCondition condition = new AttributeCondition();
		condition.attributeType = AttributeType.Text;
		condition.key = key;
		condition.listshowable = listshowable;
		condition.requiredable = requiredable;
		return condition;
	}
	
	/**
	 * 创建枚举类型的检索条件
	 * @param key
	 * @param listshowable
	 * @param requiredable
	 * @param multiselectable
	 * @param searchable
	 * @return
	 */
	public static AttributeCondition createEnumTypeConditon(String key, Boolean listshowable, Boolean requiredable, Boolean multiselectable,Boolean searchable) {
		AttributeCondition condition = new AttributeCondition();
		condition.attributeType = AttributeType.Enum;
		condition.key = key;
		condition.listshowable = listshowable;
		condition.requiredable = requiredable;
		condition.multiselectable = multiselectable;
		condition.searchable = searchable;
		return condition;
	}
	
	public String getKey() {
		return key;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public Boolean getListshowable() {
		return listshowable;
	}

	public Boolean getRequiredable() {
		return requiredable;
	}

	public Boolean getMultiselectable() {
		return multiselectable;
	}

	public Boolean getSearchable() {
		return searchable;
	}

}
