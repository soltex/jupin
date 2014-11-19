/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;

/**
 * 抽象属性类型
 * @author shipeng
 */
public abstract class AbstractAttribute extends AbstractBusinessObject {

	/** */
	private static final long serialVersionUID = 4996732526020817178L;
	
	/** 自然ID*/
	private Integer id;
	/** 属性名称*/
	private String attributeName;
	/** 属性描述*/
	private String description;
	/** 属性类型 Lang | Enum*/
	private AttributeType attributeType;
	/** 属性范围 Product | SKU*/
	private AttributeScope attributeScope;
	/**  是否显示在列表上*/
	private boolean listShowable = false;
	
	public AbstractAttribute(AttributeType attributeType, AttributeScope attributeScope) {
		MyAssert.notNull(attributeType);
		MyAssert.notNull(attributeScope);
		this.attributeType = attributeType;
		this.attributeScope = attributeScope;
	}
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public AttributeScope getAttributeScope() {
		return attributeScope;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public boolean isListShowable() {
		return listShowable;
	}

	public void setListShowable(boolean listShowable) {
		this.listShowable = listShowable;
	}
	
}
