/**
 * 
 */
package com.vanstone.jupin.common;

/**
 * 图片格式错误异常
 * @author shipeng
 */
public class ImageFormatException extends JupinException {

	private static final long serialVersionUID = 2494854025951206963L;
	
	public ImageFormatException() {
		super();
	}
	
	public ImageFormatException(String message) {
		super(message);
	}
	
	public ImageFormatException(Exception e) {
		super(e);
	}
	
}
