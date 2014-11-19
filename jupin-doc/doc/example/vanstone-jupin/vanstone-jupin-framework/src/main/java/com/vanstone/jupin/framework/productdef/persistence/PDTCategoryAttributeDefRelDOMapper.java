package com.vanstone.jupin.framework.productdef.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTCategoryAttributeDefRelDOKey;

@MyBatisRepository
public interface PDTCategoryAttributeDefRelDOMapper {
    int deleteByPrimaryKey(PDTCategoryAttributeDefRelDOKey key);

    int insert(PDTCategoryAttributeDefRelDOKey record);

    int insertSelective(PDTCategoryAttributeDefRelDOKey record);
    
    int deleteByAttributeDefId(Integer attributeId);
    
    List<Integer> selectAttributeIdsByCategoryIds(@Param("categoryIds")List<Integer> cids);
    
    PDTCategoryAttributeDefRelDOKey selectByKey(@Param("categoryId")Integer categoryId, @Param("attributeId")Integer attributeId);
    
    int deleteByCategoryId(Integer cid);
    
}