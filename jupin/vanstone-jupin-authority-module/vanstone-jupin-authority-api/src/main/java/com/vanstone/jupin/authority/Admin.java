/**
 * 
 */
package com.vanstone.jupin.authority;

import java.util.Date;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;

/**
 * 管理员账户
 * @author shipeng
 */
public class Admin extends AbstractBusinessObject {
	
	/***/
	private static final long serialVersionUID = -4445695556097091342L;
		
	/**管理员ID*/
	private String id;
	/**管理员名称*/
	private String adminName;
	/**管理员密码*/
	private String password;
	/**写入时间*/
	private Date sysInsertTime;
	/**更新时间*/
	private Date sysUpdateTime;
	/**邮件地址*/
	private String email1;
	private String email2;
	private String email3;
	private String email4;
	/**管理员角色*/
	private AdminRole adminRole;
	/**管理员状态*/
	private AdminState adminState = AdminState.Active;
	/**上次登录时间*/
	private Date lastLoginTime;
	
	public Admin(AdminRole adminRole) {
		MyAssert.notNull(adminRole);
		this.adminRole = adminRole;
	}
	
	@Override
	public String getId() {
		return id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSysInsertTime() {
		return sysInsertTime;
	}

	public void setSysInsertTime(Date sysInsertTime) {
		this.sysInsertTime = sysInsertTime;
	}

	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getEmail4() {
		return email4;
	}

	public void setEmail4(String email4) {
		this.email4 = email4;
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	public AdminState getAdminState() {
		return adminState;
	}

	public void setAdminState(AdminState adminState) {
		this.adminState = adminState;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
