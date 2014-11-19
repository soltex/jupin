/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 枚举属性类型
 * @author shipeng
 */
public class Attr4Enum extends AbstractAttribute {

	/** */
	private static final long serialVersionUID = -1865429896509640107L;
	
	/** 是否参加检索*/
	private boolean searchable = false;
	/** 枚举属性值*/
	private Set<EnumValue> enumValues = new LinkedHashSet<EnumValue>();
	
	public Attr4Enum(AttributeScope attributeScope) {
		super(AttributeType.Enum, attributeScope);
	}
	
	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public Set<EnumValue> getEnumValues() {
		return enumValues;
	}

	/**
	 * 添加EnumValue
	 * @param enumValue
	 */
	public void addEnumValue(EnumValue enumValue) {
		this.enumValues.add(enumValue);
	}
	
	/**
	 * 添加EnumValues
	 * @param enumValues
	 */
	public void addEnumValues(Collection<EnumValue> enumValues) {
		this.enumValues.addAll(enumValues);
	}
	
	/**
	 * 是否存在EnumValues
	 * @return
	 */
	public boolean existEnumValues() {
		return this.enumValues.size() != 0;
	}
	
}
