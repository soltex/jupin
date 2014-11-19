/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum AttributeType  implements BaseEnum<Integer>{
	
	Lang("基础类型",0 ), Enum("枚举类型", 1);
	
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
