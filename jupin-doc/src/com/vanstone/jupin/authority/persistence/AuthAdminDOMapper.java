package com.vanstone.jupin.authority.persistence;

import com.vanstone.jupin.authority.persistence.object.AuthAdminDO;

public interface AuthAdminDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthAdminDO record);

    int insertSelective(AuthAdminDO record);

    AuthAdminDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthAdminDO record);

    int updateByPrimaryKey(AuthAdminDO record);
}