/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vanstone.business.CommonDateUtil;
import com.vanstone.dal.id.IDBuilder;

/**
 * 抽象Message对象
 * @author shipeng
 */
public abstract class AbstractMessage implements Message {
	
	/** 消息ID */
	protected String id;
	/** 消息产生时间 */
	protected Date createTime;
	
	@Override
	public void send() {
		this.id = IDBuilder.base58Uuid();
		this.createTime = new Date();
		PersistenceManager.getInstance().pushMessage(this.getMessageBox().getName().getKey(), this.toJson());
	}
	
	/**
	 * 转换为Json字符串
	 * @return
	 */
	@Override
	public String toJson() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Map<String, Object> dataMap = this.toJsonMap();
		return gson.toJson(dataMap);
	}
	
	@Override
	public Map<String, Object> toJsonMap() {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		dataMap.put("id", this.getId());
		dataMap.put("createTime", getCreateTime() != null ? CommonDateUtil.date2String(getCreateTime(), CommonDateUtil.PATTERN_STANDARD) : null);
		dataMap.put("messageLevel", getMessageLevel() != null ? getMessageLevel().getCode() : null);
		dataMap.put("title", getTitle());
		dataMap.put("content", getContent());
		dataMap.put("messageBoxGroup", getMessageBox().getName().getMessageBoxGroup());
		dataMap.put("messageBoxName", getMessageBox().getName().getMessageBoxName());
		if (this.getParams() != null && this.getParams().size() >0) {
			dataMap.put("params", this.getParams());
		}
		return dataMap;
	}

	public String getId() {
		return id;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
}
