package com.vanstone.jupin.productdefine.persistence;

import org.apache.ibatis.annotations.Param;

import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryAttributeDefRelDO;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryAttributeDefRelDOKey;

public interface PDTCategoryAttributeDefRelDOMapper {
    int deleteByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int insert(PDTCategoryAttributeDefRelDO record);

    int insertSelective(PDTCategoryAttributeDefRelDO record);

    PDTCategoryAttributeDefRelDO selectByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int updateByPrimaryKeySelective(PDTCategoryAttributeDefRelDO record);

    int updateByPrimaryKey(PDTCategoryAttributeDefRelDO record);
    
    int deleteByProductCategoryID_AttributeID(@Param("categoryId")Integer productCategoryId, @Param("attributeDefId")Integer attributeId);
    
}