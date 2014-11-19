/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import com.vanstone.business.lang.BaseEnum;

/**
 * @author shipeng
 *
 */
public enum AttributeScope implements BaseEnum<Integer>{
	
	Product("产品级", 0 ), Sku("库存实例", 1);
	
	private String desc;
	private Integer code;
	
	private AttributeScope(String desc, Integer code) {
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
