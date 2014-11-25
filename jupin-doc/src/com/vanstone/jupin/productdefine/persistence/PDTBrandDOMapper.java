package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTBrandDO;

public interface PDTBrandDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTBrandDO record);

    int insertSelective(PDTBrandDO record);

    PDTBrandDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTBrandDO record);

    int updateByPrimaryKeyWithBLOBs(PDTBrandDO record);

    int updateByPrimaryKey(PDTBrandDO record);
}