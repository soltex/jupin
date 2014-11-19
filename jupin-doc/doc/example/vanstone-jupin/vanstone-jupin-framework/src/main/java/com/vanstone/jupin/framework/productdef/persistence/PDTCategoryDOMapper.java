package com.vanstone.jupin.framework.productdef.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTCategoryDO;

@MyBatisRepository
public interface PDTCategoryDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTCategoryDO record);

    int insertSelective(PDTCategoryDO record);

    PDTCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTCategoryDO record);

    int updateByPrimaryKey(PDTCategoryDO record);
    
    int updateCoverImageObject(@Param("id")Integer id, @Param("coverFileId")String coverFileId, 
    		@Param("coverFileWidth")Integer coverFileWidth, @Param("coverFileHeight")Integer coverFileHeight, @Param("coverFileExt")String coverFileExt);
    
    List<PDTCategoryDO> selectByParentId(@Param("parentId")Integer parentId);
    
    int updateBaseInfo(@Param("id")Integer id, @Param("categortName")String categoryName, @Param("description")String description, @Param("categoryBindPage")String categoryBindPage
    		, @Param("formTemplate")String formTemplate, @Param("sort")Integer sort);
    
    List<Integer> selectAllIds();
    
}