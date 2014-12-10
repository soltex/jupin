/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.CommonDateUtil;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.adminservice.auth.sdk.AuthConf;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminState;
import com.vanstone.jupin.authority.services.AdminService;
import com.vanstone.jupin.business.sdk.adminservice.auth.AuthorizationException;
import com.vanstone.jupin.business.sdk.adminservice.auth.AuthorizationException.ErrorCode;
import com.vanstone.jupin.business.sdk.adminservice.auth.AuthorizationManager;
import com.vanstone.jupin.common.Constants;

/**
 * @author shipeng
 */
@Service("authorizationManager")
public class AuthorizationManagerImpl extends DefaultBusinessService implements AuthorizationManager {
	
	/***/
	private static final long serialVersionUID = 3291711956103211339L;
	
	private static Logger LOG = LoggerFactory.getLogger(AuthorizationManagerImpl.class);
	
	@Autowired
	private AdminService adminService;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#login(java.lang.String, java.lang.String, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Admin login(@NotBlank String adminName, @NotBlank String adminPassword, @NotNull HttpServletRequest servletRequest, @NotNull HttpServletResponse servletResponse) throws AuthorizationException {
		//验证
		Admin admin = this.adminService.getAdminByAdminName(adminName);
		if (admin == null) {
			throw AuthorizationException.create(ErrorCode.Admin_Name_Not_Found);
		}
		if (!admin.getPassword().equals(adminPassword)) {
			throw AuthorizationException.create(ErrorCode.Admin_Name_Password_Not_Match);
		}
		if (admin.getAdminState().equals(AdminState.Forbidden)) {
			throw AuthorizationException.create(ErrorCode.Admin_Forbidden);
		}
		//登录时间写入
		admin = this.adminService.updateLastLoginTime(admin, new Date());
		//Session范围写入
		servletRequest.setAttribute(Constants.ADMIN_IN_SESSIO_NAME, admin);
		LOG.info("{} login admin system. {}", admin.getAdminName(), CommonDateUtil.date2String(admin.getLastLoginTime(), CommonDateUtil.PATTERN_STANDARD));
		return admin;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#logout(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void logout(@NotNull HttpServletRequest servletRequest) {
		servletRequest.getSession().invalidate();
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#getCurrentLoggedAdmin(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Admin getCurrentLoggedAdmin(@NotNull HttpServletRequest servletRequest) {
		HttpSession session = servletRequest.getSession();
		Admin admin = (Admin)session.getAttribute(Constants.ADMIN_IN_SESSIO_NAME);
		return admin;
	}
	
	@Override
	public boolean hasLogined(@NotNull HttpServletRequest servletRequest) {
		Admin admin = this.getCurrentLoggedAdmin(servletRequest);
		if (admin == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean allowAccess(@NotNull HttpServletRequest servletRequest) {
		if (this.hasLogined(servletRequest)) {
			return true;
		}
		String requestURI = servletRequest.getRequestURI();
		boolean exclude = AuthConf.getInstance().isExclude(requestURI);
		if (exclude) {
			return true;
		}
		return false;
	}
	
}
