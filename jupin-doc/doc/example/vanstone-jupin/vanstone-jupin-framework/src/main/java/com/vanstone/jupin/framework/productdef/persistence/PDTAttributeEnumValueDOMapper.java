package com.vanstone.jupin.framework.productdef.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeEnumValueDO;

@MyBatisRepository
public interface PDTAttributeEnumValueDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTAttributeEnumValueDO record);

    int insertSelective(PDTAttributeEnumValueDO record);

    PDTAttributeEnumValueDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeEnumValueDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeEnumValueDO record);

    int updateByPrimaryKey(PDTAttributeEnumValueDO record);
    
    List<PDTAttributeEnumValueDO> selectByAttributeDefId(Integer attributeDefId);
    
    int deleteByAttributeId(Integer attributeId);
    
    PDTAttributeEnumValueDO selectByAttributeId_ObjectValue(@Param("attributeId")Integer attributeId, @Param("objectValue")String objectValue);
    
}