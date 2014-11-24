package com.vanstone.jupin.productdefine.persistence;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuSizeTemplateDO;

@MyBatisRepository
public interface PDTSkuSizeTemplateDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTSkuSizeTemplateDO record);

    int insertSelective(PDTSkuSizeTemplateDO record);

    PDTSkuSizeTemplateDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTSkuSizeTemplateDO record);

    int updateByPrimaryKeyWithBLOBs(PDTSkuSizeTemplateDO record);

    int updateByPrimaryKey(PDTSkuSizeTemplateDO record);
    
    PDTSkuSizeTemplateDO selectByTemplateName(@Param("templateName")String templateName);
    
    PDTSkuSizeTemplateDO selectByTemplateName_NotSelf(@Param("id") Integer id, @Param("templateName")String templateName);
    
}