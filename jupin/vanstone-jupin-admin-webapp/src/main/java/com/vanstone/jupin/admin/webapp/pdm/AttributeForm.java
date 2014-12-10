/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import com.vanstone.jupin.admin.webapp.AdminBaseForm;

/**
 * @author shipeng
 */
public class AttributeForm extends AdminBaseForm {
	
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	//=========================attribute form fields==========================//
	/**ID*/
	private Integer id;
	/**属性名称*/
	private String attributeName;
	/**属性描述*/
	private String attributeDescription;
	/**属性类型*/
	private Integer attributeTypeCode;
	/**是否显示在列表页上*/
	private boolean listshowable = false;
	/**是否为必填项*/
	private boolean requiredable=false;
	/**非多选*/
	private boolean multiselectable = false;
	/**是否用于搜索*/
	private boolean searchable = false;
	/**枚举值*/
	private String enumValues;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getAttributeTypeCode() {
		return attributeTypeCode;
	}

	public void setAttributeTypeCode(Integer attributeTypeCode) {
		this.attributeTypeCode = attributeTypeCode;
	}

	public boolean isListshowable() {
		return listshowable;
	}

	public void setListshowable(boolean listshowable) {
		this.listshowable = listshowable;
	}

	public boolean isRequiredable() {
		return requiredable;
	}

	public void setRequiredable(boolean requiredable) {
		this.requiredable = requiredable;
	}

	public boolean isMultiselectable() {
		return multiselectable;
	}

	public void setMultiselectable(boolean multiselectable) {
		this.multiselectable = multiselectable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}
	
}
