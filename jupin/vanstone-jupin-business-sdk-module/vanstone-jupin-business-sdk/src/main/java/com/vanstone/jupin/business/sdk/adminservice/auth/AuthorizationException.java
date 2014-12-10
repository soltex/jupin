/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.auth;

import com.vanstone.business.lang.BaseEnum;
import com.vanstone.common.MyAssert;
import com.vanstone.jupin.common.JupinException;

/**
 * @author shipeng
 *
 */
public class AuthorizationException extends JupinException {

	/***/
	private static final long serialVersionUID = -4921431306101344306L;
	
	private ErrorCode errorCode;
	
	public AuthorizationException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * 创建AuthorityException
	 * @param errorCode
	 * @return
	 */
	public static AuthorizationException create(ErrorCode errorCode) {
		MyAssert.notNull(errorCode);
		return new AuthorizationException(errorCode);
	}
	
	public static enum ErrorCode implements BaseEnum<String> {
		
		/**管理员不存在*/
		Admin_Name_Not_Found,
		
		/**管理员用户名密码不匹配*/
		Admin_Name_Password_Not_Match,
		
		/**管理员被禁止*/
		Admin_Forbidden
		;
		
		@Override
		public String getCode() {
			return this.toString();
		}
		
		@Override
		public String getDesc() {
			return this.toString();
		}
		
	}
}
