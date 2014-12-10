/**
 * 
 */
package com.vanstone.jupin.business.sdk.common;

import java.util.Collection;

import com.vanstone.jupin.messagebox.Message;

/**
 * @author shipeng
 */
public interface MessageNotificationManager {
	
	public static final String SERVICE = "messageNotificationManager";
	
	/**
	 * 读取消息-默认信箱内
	 * @return
	 */
	Message readMessage();
	
	/**
	 * 读取全部消息-默认信箱内
	 * @return
	 */
	Collection<Message> readAllMessages();
	
	/**
	 * 读取消息
	 * @param messageBoxGroup
	 * @param messageBoxName
	 * @return
	 */
	Message readMessage(String messageBoxGroup, String messageBoxName);
	
	/**
	 * 读取全部消息
	 * @param messageBoxGroup
	 * @param messageBoxName
	 * @return
	 */
	Collection<Message> readAllMessages(String messageBoxGroup, String messageBoxName);
	
}
