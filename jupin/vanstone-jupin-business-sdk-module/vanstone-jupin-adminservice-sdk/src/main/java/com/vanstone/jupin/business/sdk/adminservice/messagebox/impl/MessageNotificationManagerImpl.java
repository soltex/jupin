/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.messagebox.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.business.sdk.adminservice.messagebox.MessageNotificationManager;
import com.vanstone.jupin.messagebox.Constants;
import com.vanstone.jupin.messagebox.Message;
import com.vanstone.jupin.messagebox.MessageBox;
import com.vanstone.jupin.messagebox.MessageBoxManager;

/**
 * @author shipeng
 */
@Service("messageNotificationManager")
public class MessageNotificationManagerImpl extends DefaultBusinessService implements MessageNotificationManager {
	
	/***/
	private static final long serialVersionUID = -2269243299994038851L;
	
	@Override
	public Message readMessage() {
		return readMessage(null, null);
	}
	
	@Override
	public Collection<Message> readAllMessages() {
		return readAllMessages(null, null);
	}
	
	@Override
	public Message readMessage(String messageBoxGroup, String messageBoxName) {
		if (messageBoxGroup == null) {
			messageBoxGroup = Constants.DEFAULT_MESSGEBOX_GROUP;
		}
		if (messageBoxName == null || messageBoxName.equals("")) {
			messageBoxName = Constants.DEFAULT_MESSGEBOX_NAME;
		}
		MessageBox messageBox = MessageBoxManager.getInstance().getMessageBox(messageBoxGroup, messageBoxName);
		if (messageBox == null) {
			return null;
		}
		return messageBox.read();
	}

	@Override
	public Collection<Message> readAllMessages(String messageBoxGroup, String messageBoxName) {
		if (messageBoxGroup == null) {
			messageBoxGroup = Constants.DEFAULT_MESSGEBOX_GROUP;
		}
		if (messageBoxName == null || messageBoxName.equals("")) {
			messageBoxName = Constants.DEFAULT_MESSGEBOX_NAME;
		}
		MessageBox messageBox = MessageBoxManager.getInstance().getMessageBox(messageBoxGroup, Constants.DEFAULT_MESSGEBOX_NAME);
		if (messageBox == null) {
			return null;
		}
		Collection<Message> messages = new ArrayList<Message>();
		while (true) {
			Message message = messageBox.read();
			if (message == null) {
				break;
			}
			messages.add(message);
		}
		if (messages == null || messages.size() <= 0) {
			return null;
		}
		return messages;
	}

}
