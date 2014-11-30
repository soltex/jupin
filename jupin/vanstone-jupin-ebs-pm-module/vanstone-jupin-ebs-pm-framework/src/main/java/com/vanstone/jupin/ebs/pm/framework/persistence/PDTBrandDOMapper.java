package com.vanstone.jupin.ebs.pm.framework.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.PDTBrandDO;
import com.vanstone.jupin.ebs.pm.framework.persistence.object.QueryBrandStatResultMap;

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
    
    List<QueryBrandStatResultMap> selectByCategoryIDs_Key(@Param("categoryIDs")Integer[] categoryIDs, @Param("key")String key, RowBounds rowBounds);
    
    int selectTotalByCategoryIDs_Key(@Param("categoryIDs")Integer[] categoryIDs, @Param("key")String key);
    
    List<PDTBrandDO> selectByPinyinKey(@Param("pinyinKey")String pinyinKey, RowBounds rowBounds);
    
}