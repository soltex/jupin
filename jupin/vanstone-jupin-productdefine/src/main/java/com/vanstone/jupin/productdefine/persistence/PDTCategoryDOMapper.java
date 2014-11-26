package com.vanstone.jupin.productdefine.persistence;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.productdefine.persistence.object.PDTCategoryDO;
@MyBatisRepository
public interface PDTCategoryDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTCategoryDO record);

    int insertSelective(PDTCategoryDO record);

    PDTCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTCategoryDO record);

    int updateByPrimaryKey(PDTCategoryDO record);
    
    int updateCoverImage(@Param("id")Integer id, @Param("coverFileId")String coverFileId, @Param("coverFileExt")String coverFileExt, @Param("coverFileWidth")Integer coverFileWidth, @Param("coverFileHeight")Integer coverFileHeight);
    
}