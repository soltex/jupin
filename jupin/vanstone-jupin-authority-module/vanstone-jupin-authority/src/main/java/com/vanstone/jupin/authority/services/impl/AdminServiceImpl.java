package com.vanstone.jupin.authority.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.session.RowBounds;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.validation.annotation.Validated;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.dal.id.IDBuilder;
import com.vanstone.framework.business.services.DefaultBusinessService;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.authority.AdminRole;
import com.vanstone.jupin.authority.AdminState;
import com.vanstone.jupin.authority.persistence.AuthAdminDOMapper;
import com.vanstone.jupin.authority.persistence.object.AuthAdminDO;
import com.vanstone.jupin.authority.services.AdminService;

/**
 * AdminServiceImpl
 */
@Service("adminService")
@Validated
public class AdminServiceImpl extends DefaultBusinessService implements AdminService {

	/***/
	private static final long serialVersionUID = 941836141790438991L;
	
	private static Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AuthAdminDOMapper authAdminDOMapper;
	
	@Override
	public Admin addAdmin(@NotNull final Admin admin) throws ObjectDuplicateException {
		Admin tempAdmin = this.getAdminByAdminName(admin.getAdminName());
		if (tempAdmin != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				admin.setId(IDBuilder.base58Uuid());
				admin.setSysInsertTime(new Date());
				admin.setSysUpdateTime(new Date());
				AuthAdminDO model = BeanUtil.toAuthAdminDO(admin);
				authAdminDOMapper.insert(model);
			}
		});
		LOG.debug("Add Admin Success : {}", admin.getAdminName());
		return admin;
	}
	
	@Override
	public Admin getAdmin(@NotBlank String id) {
		AuthAdminDO model = this.authAdminDOMapper.selectByPrimaryKey(id);
		if (model == null) {
			return null;
		}
		return BeanUtil.toAdmin(model);
	}
	
	@Override
	public Admin getAdminByAdminName(@NotBlank String adminName) {
		AuthAdminDO model = this.authAdminDOMapper.selectByAdminName(adminName);
		if (model == null) {
			return null;
		}
		return BeanUtil.toAdmin(model);
	}
	
	@Override
	public Admin updateAdminBaseInfo(@NotNull final Admin admin) {
		MyAssert4Business.objectInitialized(admin);
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				admin.setSysUpdateTime(new Date());
				AuthAdminDO model = new AuthAdminDO();
				model.setId(admin.getId());
				model.setSysUpdateTime(admin.getSysUpdateTime());
				model.setEmail1(admin.getEmail1());
				model.setEmail2(admin.getEmail2());
				model.setEmail3(admin.getEmail3());
				model.setEmail4(admin.getEmail4());
				authAdminDOMapper.updateBaseInfo(model);
			}
		});
		return admin;
	}
	
	@Override
	public Admin updateAdminName(@NotNull final Admin admin, @NotBlank final String adminName) throws ObjectDuplicateException {
		AuthAdminDO tempModel = this.authAdminDOMapper.selectByAdminName_NotSelf(adminName, admin.getId());
		if (tempModel != null) {
			throw new ObjectDuplicateException();
		}
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO model = new AuthAdminDO();
				model.setId(admin.getId());
				model.setSysUpdateTime(new Date());
				model.setAdminName(adminName);
				authAdminDOMapper.updateAdminName(model);
			}
		});
		return this.getAdmin(admin.getId());
	}
	
	@Override
	public Admin updatePassword(@NotNull final Admin admin, @NotBlank final String newPassword) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO model = new AuthAdminDO();
				model.setId(admin.getId());
				model.setSysUpdateTime(new Date());
				model.setAdminPwd(newPassword);
				authAdminDOMapper.updatePassword(model);
			}
		});
		return this.getAdmin(admin.getId());
	}
	
	@Override
	public Admin updateAdminState(@NotNull final Admin admin, @NotNull final AdminState adminState) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO model = new AuthAdminDO();
				model.setId(admin.getId());
				model.setSysUpdateTime(new Date());
				model.setAdminState(adminState.getCode());
				authAdminDOMapper.updateAdminState(model);
			}
		});
		return this.getAdmin(admin.getId());
	}
	
	@Override
	public Admin updateAdminRole(@NotNull final Admin admin, @NotNull final AdminRole adminRole) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO authAdminDO = new AuthAdminDO();
				authAdminDO.setId(admin.getId());
				authAdminDO.setAdminRole(adminRole.getCode());
				authAdminDO.setSysUpdateTime(new Date());
				authAdminDOMapper.updateAdminRole(authAdminDO);
			}
		});
		return this.getAdmin(admin.getId());
	}

	@Override
	public Admin updateLastLoginTime(@NotNull final Admin admin, @NotNull final Date lastLoginTime) {
		this.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				AuthAdminDO model = new AuthAdminDO();
				model.setId(admin.getId());
				model.setLastLoginTime(lastLoginTime);
				authAdminDOMapper.updateByPrimaryKeySelective(model);
				admin.setLastLoginTime(lastLoginTime);
			}
		});
		return admin;
	}

	@Override
	public Collection<Admin> getAdmins(String key, int offset, int limit) {
		List<AuthAdminDO> authAdminDOs = this.authAdminDOMapper.selectByKey(key, new RowBounds(offset, limit));
		if (authAdminDOs == null) {
			return null;
		}
		Collection<Admin> admins = new ArrayList<Admin>();
		for (AuthAdminDO model : authAdminDOs) {
			admins.add(BeanUtil.toAdmin(model));
		}
		return admins;
	}
	
	@Override
	public int getTotalAdmins(String key) {
		return authAdminDOMapper.selectTotalByKey(key);
	}
	
}
