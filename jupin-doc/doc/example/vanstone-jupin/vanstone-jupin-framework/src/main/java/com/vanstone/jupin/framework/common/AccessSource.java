/**
 * 
 */
package com.vanstone.jupin.framework.common;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum AccessSource implements BaseEnum<Integer> {
	
	Web("站点",1), Weixin("微信终端",2), App("移动终端",3);
	
	private String desc;
	private Integer code;
	
	private AccessSource(String desc, Integer code) {
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
