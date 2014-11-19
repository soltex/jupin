/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.common.MyAssert;


/**
 * 属性值
 * @author shipeng
 */
public abstract class AbstractAttributeValue {
	
	/** 属性类型 */
	private AttributeType attributeType;
	
	public AbstractAttributeValue(AttributeType attributeType) {
		MyAssert.notNull(attributeType);
		this.attributeType = attributeType;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}
	
}
