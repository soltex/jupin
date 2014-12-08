/**
 * 
 */
package com.vanstone.jupin.admin.webapp.commmon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.jupin.admin.webapp.AdminBaseAction;
import com.vanstone.jupin.business.sdk.adminservice.messagebox.MessageNotificationManager;
import com.vanstone.jupin.messagebox.Message;

/**
 * @author shipeng
 */
@Controller("messageBoxAction")
@RequestMapping("/common/messagebox")
public class MessageBoxAction extends AdminBaseAction {
	
	@Autowired
	private MessageNotificationManager messageNotificationManager;
	
	@RequestMapping(value="/read-messages",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String readAllMessage() {
		Message message = messageNotificationManager.readMessage();
		if (message != null) {
			return message.toJson();
		}
		return null;
	}
	
}
