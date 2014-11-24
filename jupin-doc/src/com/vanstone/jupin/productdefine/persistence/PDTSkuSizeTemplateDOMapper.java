package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTemplateDO;

public interface PDTSkuSizeTemplateDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTSkuSizeTemplateDO record);

    int insertSelective(PDTSkuSizeTemplateDO record);

    PDTSkuSizeTemplateDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTSkuSizeTemplateDO record);

    int updateByPrimaryKeyWithBLOBs(PDTSkuSizeTemplateDO record);

    int updateByPrimaryKey(PDTSkuSizeTemplateDO record);
}