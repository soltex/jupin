/**
 * 
 */
package com.vanstone.jupin.productdefine.attr;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * 基础属性类型
 * @author shipeng
 */
public abstract class AbstractAttribute extends AbstractBusinessObject {
	
	/***/
	private static final long serialVersionUID = 1742726427880998440L;
	
	/**ID*/
	private Integer id;
	/**属性名称*/
	private String attributeName;
	/**属性描述*/
	private String attributeDescription;
	/**属性类型*/
	private AttributeType attributeType = AttributeType.Common_Attribute;
	/**是否显示在列表页上*/
	private boolean listshowable = false;
	/**是否已使用*/
	private boolean usable = false;
	/**是否为必填项*/
	private boolean requiredable=false;
	
	protected AbstractAttribute(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	protected AbstractAttribute(AttributeType attributeType, boolean usable) {
		this(attributeType);
		this.usable = usable;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeDescription() {
		return attributeDescription;
	}

	public void setAttributeDescription(String attributeDescription) {
		this.attributeDescription = attributeDescription;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}

	public boolean isRequiredable() {
		return requiredable;
	}

	public void setRequiredable(boolean requiredable) {
		this.requiredable = requiredable;
	}

	public boolean isListshowable() {
		return listshowable;
	}

	public void setListshowable(boolean listshowable) {
		this.listshowable = listshowable;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
