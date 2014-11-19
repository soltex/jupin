/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.business.lang.BaseEnum;

/**
 * 栏目状态,是否启用
 * @author shipeng
 */
public enum CategoryStatus implements BaseEnum<Integer> {
	
	Active("启用",1 ), Forbit("禁用", 1);
	
	private String desc;
	private Integer code;
	
	private CategoryStatus(String desc, Integer code) {
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
