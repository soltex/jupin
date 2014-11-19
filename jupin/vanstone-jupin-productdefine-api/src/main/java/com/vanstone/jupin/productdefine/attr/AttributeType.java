/**
 * 
 */
package com.vanstone.jupin.productdefine.attr;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 */
public enum AttributeType implements BaseEnum<Integer> {
	
	Common_Attribute("普通属性",0), Enum_Attribute("枚举属性",1);
	
	private String desc;
	private Integer code;
	
	private AttributeType(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}
	
	@Override
	public Integer getCode() {
		return this.code;
	}
	
	@Override
	public String getDesc() {
		return this.desc;
	}
	
}
