/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vanstone.business.CommonDateUtil;
import com.vanstone.business.lang.EnumUtils;
import com.vanstone.business.serialize.GsonCreator;
import com.vanstone.common.MyAssert;
import com.vanstone.jupin.common.Constants;

/**
 * 消息帮助类
 * @author shipeng
 */
public class MessageHelper {
	
	/**
	 * 创建Message
	 * @param title
	 * @param content
	 * @return
	 */
	public static Message createNewMessage(MessageLevel messageLevel, String title, String content, MessageBox messageBox) {
		MyAssert.hasText(content);
		if (messageLevel == null) {
			messageLevel = MessageLevel.Info;
		}
		if (messageBox == null) {
			messageBox = new MessageBoxImpl(Constants.DEFAULT_MESSGEBOX_GROUP, Constants.DEFAULT_MESSGEBOX_NAME);
		}
		MessageEntity messageEntity = new MessageEntity(messageLevel, title, content, messageBox, null);
		return messageEntity;
	}
	
	/**
	 * 创建Message
	 * @param title
	 * @param content
	 * @param messageBox
	 * @return
	 */
	public static Message createNewMessage(String title, String content, MessageBox messageBox) {
		return createNewMessage(null, title, content, messageBox);
	}
	
	/**
	 * 创建Message
	 * @param content
	 * @param messageBox
	 * @return
	 */
	public static Message createNewMessage(String content, MessageBox messageBox) {
		return createNewMessage(null, null, content, messageBox);
	}
	
	/**
	 * 创建Message,默认Info级别，省略title，写入默认信箱中
	 * @param content
	 * @return
	 */
	public static Message createNewMessage(String content) {
		return createNewMessage(null, null, content, null);
	}
	
	/**
	 * 创建Message
	 * @param messageLevel
	 * @param content
	 * @return
	 */
	public static Message createNewMessage(MessageLevel messageLevel, String content) {
		return createNewMessage(messageLevel, null, content, null); 
	}
	
	/**
	 * 解析Json为Message
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Message parseMessageByJson(String json) {
		MyAssert.hasText(json);
		Gson gson = GsonCreator.create();
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		String id = jsonObject.get("id") != null ? jsonObject.get("id").getAsString() : null;
		String createTime = jsonObject.get("createTime") != null ? jsonObject.get("createTime").getAsString() : null;
		Integer messageLevelCode = jsonObject.get("messageLevel") != null ? jsonObject.get("messageLevel").getAsInt() : null;
		String title = jsonObject.get("title") != null ? jsonObject.get("title").getAsString() : null;
		String content = jsonObject.get("content") != null ? jsonObject.get("content").getAsString() : null;
		String messageBoxGroup = jsonObject.get("messageBoxGroup") != null ? jsonObject.get("messageBoxGroup").getAsString() : null;
		String messageBoxName = jsonObject.get("messageBoxName") != null ? jsonObject.get("messageBoxName").getAsString() : null;
		JsonObject paramsJsonObject = jsonObject.getAsJsonObject("params");
		Map<String, Object> params = paramsJsonObject != null ? gson.fromJson(paramsJsonObject, LinkedHashMap.class) : null;
		
		MessageBox messageBox = new MessageBoxImpl(messageBoxGroup, messageBoxName);
		MessageEntity messageEntity = new MessageEntity((MessageLevel)EnumUtils.getByCode(messageLevelCode, MessageLevel.class), title, content, messageBox, params);
		messageEntity.setId(id);
		messageEntity.setCreateTime(createTime != null ? CommonDateUtil.string2Date(createTime, CommonDateUtil.PATTERN_STANDARD) : null);
		return messageEntity;
	}
	
}
