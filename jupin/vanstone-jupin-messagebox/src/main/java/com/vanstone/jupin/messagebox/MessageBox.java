/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Collection;

/**
 * 信箱
 * @author shipeng
 */
public interface MessageBox {
	
	/**
	 * 获取信箱命名
	 * @return
	 */
	Name getName();
	
	/**
	 * 读取最新消息
	 * @return
	 */
	Message read();
	
	/**
	 * 消息数量
	 * @return
	 */
	long size();
	
	/**
	 * 全部消息列表
	 * @return
	 */
	Collection<Message> getMessages();
	
}
