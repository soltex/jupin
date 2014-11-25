package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.productdefine.persistence.object.PDTProductBrandRelDOKey;

@MyBatisRepository
public interface PDTProductBrandRelDOMapper {
    int deleteByPrimaryKey(PDTProductBrandRelDOKey key);

    int insert(PDTProductBrandRelDOKey record);

    int insertSelective(PDTProductBrandRelDOKey record);
}