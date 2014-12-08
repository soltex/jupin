/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.common.MyAssert;

/**
 * 消息实体
 * @author shipeng
 */
public class MessageEntity extends AbstractMessage {

	/** 消息级别 */
	private MessageLevel messageLevel;
	/** 消息标题 */
	private String title;
	/** 消息内容 */
	private String content;
	/** 信箱 */
	private MessageBox messageBox;
	/** 消息扩展属性 */
	private Map<String, Object> params = new LinkedHashMap<String, Object>();

	public MessageEntity(MessageLevel messageLevel, String title,
			String content, MessageBox messageBox, Map<String, Object> params) {
		MyAssert.hasText(content);
		if (messageLevel == null) {
			messageLevel = MessageLevel.Info;
		}
		this.messageLevel = messageLevel;
		this.title = title;
		this.content = content;
		if (messageBox == null) {
			messageBox = new MessageBoxImpl(Constants.DEFAULT_MESSGEBOX_GROUP,
					Constants.DEFAULT_MESSGEBOX_NAME);
		}
		this.messageBox = messageBox;
		if (params != null) {
			this.params = params;
		}
	}

	@Override
	public MessageBox getMessageBox() {
		return this.messageBox;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public Map<String, Object> getParams() {
		return this.params;
	}

	public void addParam(String key, Object value) {
		MyAssert.hasText(key);
		this.params.put(key, value);
	}

	public void addParams(Map<String, Object> params) {
		MyAssert.notEmpty(params);
		this.params.putAll(params);
	}

	@Override
	public MessageLevel getMessageLevel() {
		return this.messageLevel;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
