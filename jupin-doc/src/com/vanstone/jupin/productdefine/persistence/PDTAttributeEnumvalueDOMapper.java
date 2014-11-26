package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTAttributeEnumvalueDO;

public interface PDTAttributeEnumvalueDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTAttributeEnumvalueDO record);

    int insertSelective(PDTAttributeEnumvalueDO record);

    PDTAttributeEnumvalueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeEnumvalueDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeEnumvalueDO record);

    int updateByPrimaryKey(PDTAttributeEnumvalueDO record);
}