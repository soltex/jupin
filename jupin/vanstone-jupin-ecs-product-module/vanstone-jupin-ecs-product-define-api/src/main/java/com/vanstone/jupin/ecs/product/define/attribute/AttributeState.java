/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum AttributeState implements BaseEnum<Integer> {
	
	Active("启用",1), Forbit("禁用",0);
	;
	
	private String desc;
	private Integer code;
	
	private AttributeState(String desc, Integer code) {
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
