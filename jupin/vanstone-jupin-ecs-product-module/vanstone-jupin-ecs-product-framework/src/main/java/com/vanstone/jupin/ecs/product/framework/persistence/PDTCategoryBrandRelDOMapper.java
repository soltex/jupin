package com.vanstone.jupin.ecs.product.framework.persistence;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTCategoryBrandRelDOKey;

@MyBatisRepository
public interface PDTCategoryBrandRelDOMapper {
    int deleteByPrimaryKey(PDTCategoryBrandRelDOKey key);

    int insert(PDTCategoryBrandRelDOKey record);

    int insertSelective(PDTCategoryBrandRelDOKey record);
    
    int deleteByBrandId(@Param("id")Integer brandId);
    
    PDTCategoryBrandRelDOKey selectByPrimaryKey(@Param("categoryId")Integer categoryID,@Param("brandId")Integer brandId);
    
}