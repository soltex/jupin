/**
 * 
 */
package com.vanstone.jupin.common;

import com.vanstone.business.VanstoneException;

/**
 * @author shipeng
 *
 */
public class JupinException extends VanstoneException {
		
	private static final long serialVersionUID = 8642228317187421531L;
	
	public JupinException() {
		super();
	}
	
	public JupinException(String message) {
		super(message);
	}

	public JupinException(Throwable cause) {
		super(cause);
	}

	public JupinException(String message, Throwable cause) {
		super(message, cause);
	}

	public Throwable fillInStackTrace() {
		return this;
	}
}
