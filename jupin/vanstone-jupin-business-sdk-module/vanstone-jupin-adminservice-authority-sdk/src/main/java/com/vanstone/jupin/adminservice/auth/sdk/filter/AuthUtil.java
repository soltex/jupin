/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shipeng
 *
 */
public class AuthUtil {
	
	/**
	 * 是否为ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		if (request != null && ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) || request.getParameter("ajax") != null))
			return true;
		return false;
	}
	
}
