/**
 * 
 */
package com.vanstone.jupin.authority.services.impl;

import com.vanstone.business.lang.EnumUtils;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminRole;
import com.vanstone.jupin.authority.AdminState;
import com.vanstone.jupin.authority.persistence.object.AuthAdminDO;

/**
 * @author shipeng
 */
public class BeanUtil {
	
	public static Admin toAdmin(AuthAdminDO authAdminDO) {
		AdminRole adminRole = EnumUtils.getByCode(authAdminDO.getAdminRole(), AdminRole.class);
		if (adminRole == null) {
			throw new IllegalArgumentException();
		}
		AdminState adminState = EnumUtils.getByCode(authAdminDO.getAdminState(), AdminState.class);
		
		Admin admin = new Admin(adminRole);
		admin.setId(authAdminDO.getId());
		admin.setSysInsertTime(authAdminDO.getSysInsertTime());
		admin.setSysUpdateTime(authAdminDO.getSysUpdateTime());
		admin.setAdminName(authAdminDO.getAdminName());
		admin.setPassword(authAdminDO.getAdminPwd());
		admin.setEmail1(authAdminDO.getEmail1());
		admin.setEmail2(authAdminDO.getEmail2());
		admin.setEmail3(authAdminDO.getEmail3());
		admin.setEmail4(authAdminDO.getEmail4());
		admin.setAdminState(adminState);
		admin.setLastLoginTime(authAdminDO.getLastLoginTime());
		return admin;
	}
	
	public static AuthAdminDO toAuthAdminDO(Admin admin) {
		AuthAdminDO model = new AuthAdminDO();
		model.setId(admin.getId());
		model.setSysInsertTime(admin.getSysInsertTime());
		model.setSysUpdateTime(admin.getSysUpdateTime());
		model.setAdminName(admin.getAdminName());
		model.setAdminPwd(admin.getPassword());
		model.setEmail1(admin.getEmail1());
		model.setEmail2(admin.getEmail2());
		model.setEmail3(admin.getEmail3());
		model.setEmail4(admin.getEmail4());
		model.setAdminRole(admin.getAdminRole().getCode());
		model.setAdminState(admin.getAdminState().getCode());
		model.setLastLoginTime(admin.getLastLoginTime());
		return model;
	}
	
}
