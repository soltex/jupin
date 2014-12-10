/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk.impl;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.common.util.web.PageUtil;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminRole;
import com.vanstone.jupin.authority.AdminState;
import com.vanstone.jupin.authority.services.AdminService;
import com.vanstone.jupin.business.sdk.adminservice.auth.AdminManager;
import com.vanstone.jupin.business.sdk.common.CommonSDKManager;
import com.vanstone.jupin.common.Constants;

/**
 * @author shipeng
 */
@Service("adminManager")
public class AdminManagerImpl extends DefaultBusinessService implements AdminManager {
	
	/***/
	private static final long serialVersionUID = -9010010930519314870L;
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommonSDKManager commonSDKManager;
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#initialDefaultAdmin()
	 */
	@Override
	public Admin initialDefaultAdmin() {
		Admin admin = adminService.getAdminByAdminName(Constants.DEFAULT_ADMIN_NAME);
		if (admin != null) {
			return admin;
		}
		admin = new Admin(AdminRole.Admin);
		admin.setAdminName(Constants.DEFAULT_ADMIN_NAME);
		admin.setPassword(Constants.DEFAULT_ADMIN_PASSWORD);
		try {
			admin = this.adminService.addAdmin(admin);
		} catch (ObjectDuplicateException e) {
		}
		return admin;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#searchAdmins(java.lang.String, int, int)
	 */
	@Override
	public PageInfo<Admin> searchAdmins(String key, int pageno, int size) {
		int allrows = this.adminService.getTotalAdmins(key);
		PageUtil<Admin> pageUtil = new PageUtil<Admin>(allrows, pageno, size);
		PageInfo<Admin> pageInfo = pageUtil.getPageInfo();
		Collection<Admin> admins = this.adminService.getAdmins(key, pageUtil.getOffset(), pageUtil.getSize());
		if (admins != null && admins.size() >0) {
			pageInfo.addObjects(admins);
		}
		return pageInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#updateAdminName(java.lang.String, java.lang.String)
	 */
	@Override
	public Admin updateAdminName(@NotBlank String id, @NotBlank String newAdminName) throws ObjectDuplicateException {
		Admin admin = commonSDKManager.getAdminAndValidate(id);
		return this.adminService.updateAdminName(admin, newAdminName);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#updatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public Admin updatePassword(@NotBlank String id, @NotBlank String newPassword) {
		Admin admin = commonSDKManager.getAdminAndValidate(id);
		return this.adminService.updatePassword(admin, newPassword);
	}
	
	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#updateAdminRole(java.lang.String, com.vanstone.jupin.authority.AdminRole)
	 */
	@Override
	public Admin updateAdminRole(@NotBlank String id, @NotNull AdminRole adminRole) {
		Admin admin = commonSDKManager.getAdminAndValidate(id);
		return this.adminService.updateAdminRole(admin, adminRole);
	}

	/* (non-Javadoc)
	 * @see com.vanstone.jupin.business.sdk.adminservice.authority.AdminAuthManager#updateAdminState(java.lang.String, com.vanstone.jupin.authority.AdminState)
	 */
	@Override
	public Admin updateAdminState(@NotBlank String id, @NotNull AdminState adminState) {
		Admin admin = commonSDKManager.getAdminAndValidate(id);
		return this.adminService.updateAdminState(admin, adminState);
	}
	
}
