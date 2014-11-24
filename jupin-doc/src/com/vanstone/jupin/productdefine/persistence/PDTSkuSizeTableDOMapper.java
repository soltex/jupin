package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTableDO;

public interface PDTSkuSizeTableDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTSkuSizeTableDO record);

    int insertSelective(PDTSkuSizeTableDO record);

    PDTSkuSizeTableDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTSkuSizeTableDO record);

    int updateByPrimaryKey(PDTSkuSizeTableDO record);
}