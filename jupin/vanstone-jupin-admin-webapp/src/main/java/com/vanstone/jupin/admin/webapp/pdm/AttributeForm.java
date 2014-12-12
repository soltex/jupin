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
	private Boolean listshowable;
	/**是否为必填项*/
	private Boolean requiredable;
	/**非多选*/
	private Boolean multiselectable;
	/**是否用于搜索*/
	private Boolean searchable;
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

	public Boolean getListshowable() {
		return listshowable;
	}

	public void setListshowable(Boolean listshowable) {
		this.listshowable = listshowable;
	}

	public Boolean getRequiredable() {
		return requiredable;
	}

	public void setRequiredable(Boolean requiredable) {
		this.requiredable = requiredable;
	}

	public Boolean getMultiselectable() {
		return multiselectable;
	}

	public void setMultiselectable(Boolean multiselectable) {
		this.multiselectable = multiselectable;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public String getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(String enumValues) {
		this.enumValues = enumValues;
	}

	//=========================enum value form fields==========================//
	private Integer valueId;
	private String objectText;
	
	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public String getObjectText() {
		return objectText;
	}

	public void setObjectText(String objectText) {
		this.objectText = objectText;
	}

}
