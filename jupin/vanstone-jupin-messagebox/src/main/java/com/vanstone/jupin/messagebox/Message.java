/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Date;
import java.util.Map;

/**
 * 消息体
 * 
 * @author shipeng
 */
public interface Message {

	/**
	 * 获取信箱
	 * 
	 * @return
	 */
	MessageBox getMessageBox();

	/**
	 * 获取自然ID,自动生成ID
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 获取消息标题
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * 获取消息内容
	 * 
	 * @return
	 */
	String getContent();

	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	Date getCreateTime();

	/**
	 * 获取消息级别
	 * 
	 * @return
	 */
	MessageLevel getMessageLevel();

	/**
	 * 发送消息
	 * 
	 * @return
	 */
	void send();

	/**
	 * 获取扩展参数
	 * 
	 * @return
	 */
	Map<String, Object> getParams();
	
	/**
	 * 转换为Message Json Entity
	 * 
	 * @return
	 */
	String toJson();
	
	/**
	 * 转换为Map
	 * @return
	 */
	Map<String, Object> toJsonMap();
	
	/**
	 * 添加扩展参数
	 * @param key
	 * @param value
	 */
	void addParam(String key, Object value);
	
	/**
	 * 添加扩展参数
	 * @param params
	 */
	void addParams(Map<String, Object> params);
	
}
