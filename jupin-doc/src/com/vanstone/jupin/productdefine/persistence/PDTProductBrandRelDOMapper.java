package com.vanstone.jupin.productdefine.persistence;

import com.vanstone.jupin.productdefine.persistence.object.PDTProductBrandRelDOKey;

public interface PDTProductBrandRelDOMapper {
    int deleteByPrimaryKey(PDTProductBrandRelDOKey key);

    int insert(PDTProductBrandRelDOKey record);

    int insertSelective(PDTProductBrandRelDOKey record);
}