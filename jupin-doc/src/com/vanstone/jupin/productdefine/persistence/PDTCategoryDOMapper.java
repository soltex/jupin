package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryDO;

public interface PDTCategoryDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTCategoryDO record);

    int insertSelective(PDTCategoryDO record);

    PDTCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTCategoryDO record);

    int updateByPrimaryKey(PDTCategoryDO record);
}