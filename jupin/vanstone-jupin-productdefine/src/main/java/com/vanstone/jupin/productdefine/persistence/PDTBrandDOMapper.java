package com.vanstone.jupin.productdefine.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.productdefine.persistence.object.PDTBrandDO;

@MyBatisRepository
public interface PDTBrandDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTBrandDO record);

    int insertSelective(PDTBrandDO record);

    PDTBrandDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTBrandDO record);

    int updateByPrimaryKeyWithBLOBs(PDTBrandDO record);

    int updateByPrimaryKey(PDTBrandDO record);
    
    PDTBrandDO selectByBrandName(@Param("brandName")String brandName);
    
    PDTBrandDO selectByBrandName_NotSelf(@Param("id")Integer id, @Param("brandName")String brandName);
    
    int updateLogoInfo(@Param("id")Integer id, @Param("logoFileId")String logoFileId, @Param("logoFileExt")String logoFileExt, @Param("logoWidth")Integer logoWidth, @Param("logoHeight")Integer logoHeight);
    
    List<PDTBrandDO> selectByCategoryIDs(@Param("categoryIDs")Integer[] categoryIDs);
    
}