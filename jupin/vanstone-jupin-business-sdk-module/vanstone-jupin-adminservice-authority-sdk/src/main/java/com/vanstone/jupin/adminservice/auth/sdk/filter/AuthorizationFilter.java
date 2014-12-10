/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vanstone.framework.business.ServiceManagerFactory;
import com.vanstone.jupin.adminservice.auth.sdk.AuthConf;
import com.vanstone.jupin.business.sdk.adminservice.auth.AuthorizationManager;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.util.ServletUtil;
import com.vanstone.webframework.dwz.ViewCommandHelper;
import com.vanstone.webframework.dwz.ViewCommandObject;

/**
 * @author shipeng
 */
public class AuthorizationFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		AuthorizationManager authorizationManager = ServiceManagerFactory.getInstance().getService(AuthorizationManager.SERVICE);
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		boolean isAllow = authorizationManager.allowAccess(servletRequest);
		if (isAllow) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		if (AuthUtil.isAjax(servletRequest)) {
			//AJAX操作, 超时
			ViewCommandObject viewCommandObject = ViewCommandHelper.createRedirectObject(AuthConf.getInstance().getLoginPage());
			ServletUtil.write(servletResponse, viewCommandObject.toJsonString(), Constants.SYSTEM_CHARSET_STRING);
			return;
		}else{
			servletResponse.sendRedirect(AuthConf.getInstance().getLoginPage());
			return;
		}
//		Admin admin = authorizationManager.getCurrentLoggedAdmin(servletRequest);
//		if (admin == null) {
//			//未登录
//			if (AuthUtil.isAjax(servletRequest)) {
//				//Ajax操作
//				if (servletRequest.getRequestURI().indexOf("login") != -1 || servletRequest.getRequestURI().indexOf("logout") != -1) {
//					//登录或者登出操作  
//					chain.doFilter(servletRequest, servletResponse);
//					return;
//				}
//				//非登录登出页面
//				ServletUtil.write(servletResponse, DWZObject.createRedirectObject("/login.jhtml").toJsonString());
//				return;
//			}else {
//				//普通操作
//				if (servletRequest.getRequestURI().indexOf("login") != -1 || servletRequest.getRequestURI().indexOf("logout") != -1) {
//					//登录或者登出操作  
//					chain.doFilter(servletRequest, servletResponse);
//					return;
//				}
//				//非登录登出页面
//				servletResponse.sendRedirect("/login.jhtml");
//				return;
//			}
//		}else{
//			//已登录
//			chain.doFilter(servletRequest, servletResponse);
//		}
	}
	
	@Override
	public void destroy() {}
	
}
