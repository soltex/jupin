package com.vanstone.jupin.ebs.pm.framework.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTSkuSizeTableDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.QuerySizeTemplateDOWithSizeTableResultMap;

@MyBatisRepository
public interface PDTSkuSizeTableDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTSkuSizeTableDO record);

    int insertSelective(PDTSkuSizeTableDO record);

    PDTSkuSizeTableDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTSkuSizeTableDO record);

    int updateByPrimaryKey(PDTSkuSizeTableDO record);
    
    int deleteBySizeTemplateId(@Param("sizeTemplateId")Integer sizeTemplateId);
    
    PDTSkuSizeTableDO selectBySizeTemplateId_SizeName(@Param("sizeTemplateId")Integer sizeTemplateId, @Param("sizeName")String sizeName);
    
    PDTSkuSizeTableDO selectBySizeTemplateId_SizeName_NotSelf(@Param("sizeTemplateId")Integer sizeTemplateId, @Param("sizeName")String sizeName,@Param("id")Integer id);
    
    List<QuerySizeTemplateDOWithSizeTableResultMap> selectSizeTemplate_SizeTable_ResultMap();
    
}