package com.vanstone.jupin.ecs.product.framework.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryAttributeDefRelDO;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryAttributeDefRelDOKey;

@MyBatisRepository
public interface PDTCategoryAttributeDefRelDOMapper {
    int deleteByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int insert(PDTCategoryAttributeDefRelDO record);

    int insertSelective(PDTCategoryAttributeDefRelDO record);

    PDTCategoryAttributeDefRelDO selectByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int updateByPrimaryKeySelective(PDTCategoryAttributeDefRelDO record);

    int updateByPrimaryKey(PDTCategoryAttributeDefRelDO record);
    
    int deleteByProductCategoryID_AttributeID(@Param("categoryId")Integer productCategoryId, @Param("attributeDefId")Integer attributeId);
    
    List<PDTCategoryAttributeDefRelDO> selectByCategoryID(@Param("categoryId")Integer productCategoryId);
    
    List<PDTCategoryAttributeDefRelDO> selectByCategoryIDs(@Param("categoryIds")Integer[] productCategoryIds);
    
}