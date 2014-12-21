/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import com.vanstone.business.lang.BaseEnum;

/**
 * 库存状态
 * @author shipeng
 */
public enum SkuState implements BaseEnum<Integer> {
	
	Waiting_Release("等待上架",0), Release("已上架",1), Down("下架",10);
	;
	
	private String desc;
	private Integer code;
	
	private SkuState(String desc, Integer code) {
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
