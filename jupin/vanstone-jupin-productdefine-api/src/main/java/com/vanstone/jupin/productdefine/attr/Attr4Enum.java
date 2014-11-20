/**
 * 
 */
package com.vanstone.jupin.productdefine.attr;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 枚举类型属性
 * @author shipeng
 */
public class Attr4Enum extends AbstractAttribute {

	/***/
	private static final long serialVersionUID = -5751625577347383251L;
	
	/**非多选*/
	private boolean multiselectable = false;
	/**是否用于搜索*/
	private boolean searchable = false;
	/**枚举值*/
	private Map<Integer, String> values = new LinkedHashMap<Integer, String>();
	
	protected Attr4Enum() {
		super(AttributeType.Enum_Attribute);
	}
	
	protected Attr4Enum(boolean usable) {
		super(AttributeType.Enum_Attribute, usable);
	}
	
	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public boolean isMultiselectable() {
		return multiselectable;
	}

	public void setMultiselectable(boolean multiselectable) {
		this.multiselectable = multiselectable;
	}

	public Map<Integer, String> getValues() {
		return values;
	}
	
	public void putValue(Integer id, String value) {
		this.values.put(id, value);
	}
	
	public void putValues(Map<Integer, String> values) {
		this.values.putAll(values);
	}
	
	public void clearValues() {
		this.values.clear();
	}
	
}
