/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.pdm;

import com.vanstone.jupin.common.JupinException;

/**
 * @author shipeng
 */
public class ImportBrandFileFormatException extends JupinException {

	/***/
	private static final long serialVersionUID = 2401180864113665182L;
	
	public ImportBrandFileFormatException() {
		super();
	}
	
	public ImportBrandFileFormatException(String message) {
		super(message);
	}

	public ImportBrandFileFormatException(Throwable cause) {
		super(cause);
	}
}
