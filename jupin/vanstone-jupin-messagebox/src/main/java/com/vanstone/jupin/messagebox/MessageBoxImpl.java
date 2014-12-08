/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vanstone.common.MyAssert;

/**
 * 信箱实现类
 * @author shipeng
 */
public class MessageBoxImpl implements MessageBox {
	
	private Name name;
	
	public MessageBoxImpl(Name name) {
		MyAssert.notNull(name);
		this.name = name;
	}
	
	public MessageBoxImpl(String group, String strName) {
		MyAssert.hasText(group);
		MyAssert.hasText(strName);
		this.name = new NameImpl(group, strName);
	}
	
	@Override
	public Name getName() {
		return this.name;
	}
	
	@Override
	public long size() {
		return PersistenceManager.getInstance().getMessageCount(this.getName().getKey());
	}
	
	@Override
	public Collection<Message> getMessages() {
		List<String> values = PersistenceManager.getInstance().getMessageValues(this.getName().getKey());
		if (values == null || values.size() <=0) {
			return null;
		}
		Collection<Message> messages = new ArrayList<Message>();
		for (String value : values) {
			Message message = MessageHelper.parseMessageByJson(value);
			messages.add(message);
		}
		return messages;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.framework.messagebox.MessageBox#read()
	 */
	@Override
	public Message read() {
		PersistenceManager persistenceManager = PersistenceManager.getInstance();
		return persistenceManager.popMessage(this.getName().getKey());
	}
	
}
