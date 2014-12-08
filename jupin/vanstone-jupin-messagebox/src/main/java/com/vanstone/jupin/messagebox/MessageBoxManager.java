/**
 * 
 */
package com.vanstone.jupin.messagebox;



/**
 * 信箱全局管理器
 * @author shipeng
 */
public class MessageBoxManager {
	
	public static class MessageBoxManagerInstance {
		private static final MessageBoxManager instance = new MessageBoxManager();
	}
	
	private MessageBoxManager() {}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static MessageBoxManager getInstance() {
		return MessageBoxManagerInstance.instance;
	}
	
	/**
	 * 获取信箱数量
	 * @return
	 */
	public long getTotalMessageBoxs() {
		return PersistenceManager.getInstance().getMessageBoxCount();
	}
	
	/**
	 * 获取站内信
	 * @param group
	 * @param name
	 * @return
	 */
	public MessageBox getMessageBox(String group, String name) {
		return new MessageBoxImpl(group, name);
	}
	
}
