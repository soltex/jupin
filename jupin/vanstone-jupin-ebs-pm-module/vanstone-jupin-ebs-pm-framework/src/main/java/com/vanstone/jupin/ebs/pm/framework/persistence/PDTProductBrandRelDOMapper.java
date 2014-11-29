package com.vanstone.jupin.ebs.pm.framework.persistence;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTProductBrandRelDOKey;

@MyBatisRepository
public interface PDTProductBrandRelDOMapper {
    int deleteByPrimaryKey(PDTProductBrandRelDOKey key);

    int insert(PDTProductBrandRelDOKey record);

    int insertSelective(PDTProductBrandRelDOKey record);
}