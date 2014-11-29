/**
 * 
 */
package com.vanstone.jupin.ebs.pm.productdefine;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 */
public enum CategoryState implements BaseEnum<Integer> {
	
	Waiting("待上线",0),Active("启用",1), Forbit("禁用",10);
	
	private String desc;
	private Integer code;
	
	private CategoryState(String desc, Integer code) {
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
