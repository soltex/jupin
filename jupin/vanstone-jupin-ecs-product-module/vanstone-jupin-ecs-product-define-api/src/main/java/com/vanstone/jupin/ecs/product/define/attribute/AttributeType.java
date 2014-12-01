/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 */
public enum AttributeType implements BaseEnum<Integer> {
	
	Text("文本属性",0), Enum("枚举属性",1);
	
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
