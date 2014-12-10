/**
 * 
 */
package com.vanstone.jupin.authority.services;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminRole;
import com.vanstone.jupin.authority.AdminState;

/**
 * @author shipeng
 */
public interface AdminService {
	
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Admin addAdmin(@NotNull Admin admin) throws ObjectDuplicateException;
	
	/**
	 * 获取管理员信息
	 * @param id
	 * @return
	 */
	Admin getAdmin(@NotBlank String id);
	
	/**
	 * 更新管理员
	 * @param adminName
	 * @return
	 */
	Admin getAdminByAdminName(@NotBlank String adminName);
	
	/**
	 * 更新管理员基本信息
	 * @param admin
	 * @return
	 */
	Admin updateAdminBaseInfo(@NotNull Admin admin);
	
	/**
	 * 更改AdminName
	 * @param admin
	 * @param adminName
	 * @return
	 */
	Admin updateAdminName(@NotNull Admin admin, @NotBlank String adminName) throws ObjectDuplicateException ;
	
	/**
	 * 更新管理员密码
	 * @param admin
	 * @return
	 */
	Admin updatePassword(@NotNull Admin admin, @NotBlank String newPassword);
	
	/**
	 * 更新管理员状态
	 * @param admin
	 * @return
	 */
	Admin updateAdminState(@NotNull Admin admin, @NotNull AdminState adminState);
	
	/**
	 * 更新管理员角色
	 * @param admin
	 * @return
	 */
	Admin updateAdminRole(@NotNull Admin admin, @NotNull AdminRole adminRole);
	
	/**
	 * 更新管理员登录时间
	 * @param admin
	 * @return
	 */
	Admin updateLastLoginTime(@NotNull Admin admin, @NotNull Date lastLoginTime);
	
	/**
	 * 检索Admins
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<Admin> getAdmins(String key, int offset, int limit);
	
	/**
	 * 检索管理员数量
	 * @param key
	 * @return
	 */
	int getTotalAdmins(String key);
	
}
