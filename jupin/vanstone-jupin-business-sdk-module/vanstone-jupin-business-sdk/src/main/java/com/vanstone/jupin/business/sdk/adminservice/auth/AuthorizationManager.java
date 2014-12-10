/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.vanstone.jupin.authority.Admin;

/**
 * 认证管理器
 * @author shipeng
 */
public interface AuthorizationManager {
	
	public static final String SERVICE = "authorizationManager";
	/**
	 * 管理员登录
	 * @param adminName
	 * @param adminPassword
	 * @param servletResponse
	 * @throws AuthorityExceptionl
	 */
	Admin login(@NotBlank String adminName, @NotBlank String adminPassword, @NotNull HttpServletRequest servletRequest ,@NotNull HttpServletResponse servletResponse) throws AuthorizationException;
	
	/**
	 * 管理员退出
	 * @param servletRequest
	 */
	void logout(@NotNull HttpServletRequest servletRequest);
	
	/**
	 * 获取已登录用户
	 * @param servletRequest
	 */
	Admin getCurrentLoggedAdmin(@NotNull HttpServletRequest servletRequest);
	
	/**
	 * 判断是否允许访问
	 * @param servletRequest
	 * @return
	 */
	boolean allowAccess(@NotNull HttpServletRequest servletRequest);
	
	/**
	 * 是否已经登录
	 * @param servletRequest
	 * @return
	 */
	boolean hasLogined(@NotNull HttpServletRequest servletRequest);
	
}
