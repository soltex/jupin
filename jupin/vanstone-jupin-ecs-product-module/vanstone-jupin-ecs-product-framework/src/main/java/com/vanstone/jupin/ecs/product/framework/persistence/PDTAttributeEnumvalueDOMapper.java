package com.vanstone.jupin.ecs.product.framework.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTAttributeEnumvalueDO;

@MyBatisRepository
public interface PDTAttributeEnumvalueDOMapper {
	
    int deleteByPrimaryKey(Integer id);
    
    int deleteByAttributeID(@Param("attributeId")Integer attributeId);
    
    int insert(PDTAttributeEnumvalueDO record);

    int insertSelective(PDTAttributeEnumvalueDO record);

    PDTAttributeEnumvalueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeEnumvalueDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeEnumvalueDO record);

    int updateByPrimaryKey(PDTAttributeEnumvalueDO record);

    List<PDTAttributeEnumvalueDO> selectByAttributeDefId(@Param("attributeDefId")Integer attributeDefId);
    
    PDTAttributeEnumvalueDO selectByAttributeDefId_AttributeName(@Param("attributeDefId")Integer attributeDefId, @Param("objecttext")String objectext);
    
    Integer selectMaxSortValueByAttributeDefId(@Param("attributeDefId")Integer attributeDefId);
    
    PDTAttributeEnumvalueDO selectByAttributeDefId_AttributeName_NotSelf(@Param("attributeDefId")Integer attributeDefId, @Param("attributeName")String attributeName, @Param("id")Integer id);
    
}