/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.auth;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminRole;
import com.vanstone.jupin.authority.AdminState;

/**
 * 管理员权限管理
 * @author shipeng
 */
public interface AdminManager {
	
	public static final String SERVICE = "adminManager";
	
	/**
	 * 初始化默认Admin
	 */
	Admin initialDefaultAdmin();
	
	/**
	 * 检索管理员列表
	 * @param key
	 * @param pageno
	 * @param size
	 * @return
	 */
	PageInfo<Admin> searchAdmins(String key, int pageno, int size);
	
	/**
	 * 更新管理员帐户名
	 * @param id
	 * @param newAdminName
	 * @return
	 */
	Admin updateAdminName(@NotBlank String id, @NotBlank String newAdminName) throws ObjectDuplicateException;
	
	/**
	 * 更新密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	Admin updatePassword(@NotBlank String id, @NotBlank String newPassword);
	
	/**
	 * 更新角色信息
	 * @param id
	 * @param adminRole
	 * @return
	 */
	Admin updateAdminRole(@NotBlank String id, @NotNull AdminRole adminRole);
	
	/**
	 * 更新状态信息
	 * @param id
	 * @param adminState
	 * @return
	 */
	Admin updateAdminState(@NotBlank String id, @NotNull AdminState adminState);
	
}
