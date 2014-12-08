/**
 * 
 */
package com.vanstone.jupin.messagebox;

import com.vanstone.business.lang.BaseEnum;

/**
 * 消息级别
 * @author shipeng
 */
public enum MessageLevel implements BaseEnum<Integer> {
	
	Debug("调试消息",0),Info("信息消息",10),Warning("警告消息",20), Error("错误消息",30);
	;

	private String desc;
	private Integer code;
	
	private MessageLevel(String desc, Integer code) {
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
