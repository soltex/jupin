package com.vanstone.jupin.authority.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.authority.persistence.object.AuthAdminDO;

@MyBatisRepository
public interface AuthAdminDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthAdminDO record);

    int insertSelective(AuthAdminDO record);

    AuthAdminDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthAdminDO record);

    int updateByPrimaryKey(AuthAdminDO record);
    
    AuthAdminDO selectByAdminName(@Param("adminName")String adminName);
    
    AuthAdminDO selectByAdminName_NotSelf(@Param("adminName")String adminName, @Param("id")String id);
    
    int updateBaseInfo(AuthAdminDO authAdminDO);
    
    int updateAdminName(AuthAdminDO authAdminDO);
    
    int updateAdminRole(AuthAdminDO authAdminDO);
    
    int updateAdminState(AuthAdminDO authAdminDO);
    
    int updatePassword(AuthAdminDO authAdminDO);
    
    List<AuthAdminDO> selectByKey(@Param("key")String key, RowBounds rowBounds);
    
    int selectTotalByKey(@Param("key")String key);
}