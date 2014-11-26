package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryAttributeDefRelDOKey;

public interface PDTCategoryAttributeDefRelDOMapper {
    int deleteByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int insert(PDTCategoryAttributeDefRelDOKey record);

    int insertSelective(PDTCategoryAttributeDefRelDOKey record);
}