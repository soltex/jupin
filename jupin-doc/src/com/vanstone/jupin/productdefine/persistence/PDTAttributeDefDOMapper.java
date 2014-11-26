package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTAttributeDefDO;

public interface PDTAttributeDefDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTAttributeDefDO record);

    int insertSelective(PDTAttributeDefDO record);

    PDTAttributeDefDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeDefDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeDefDO record);

    int updateByPrimaryKey(PDTAttributeDefDO record);
}