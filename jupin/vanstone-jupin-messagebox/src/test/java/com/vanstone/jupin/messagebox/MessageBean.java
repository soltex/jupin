/**
 * 
 */
package com.vanstone.jupin.messagebox;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

/**
 * @author shipeng
 *
 */
public class MessageBean implements MessageSourceAware {

	private MessageSource messageSource;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void print(String str) {
		messageSource.getMessage("sdf", null, Locale.CHINA);
		
	}
}
