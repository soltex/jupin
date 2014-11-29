package com.vanstone.jupin.productdefine.persistence;

import java.util.List;

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

    int updateByPrimaryKeyWithBLOBs(PDTCategoryDO record);

    int updateByPrimaryKey(PDTCategoryDO record);
    
    int updateCoverImage(@Param("id")Integer id, @Param("coverFileId")String coverFileId,@Param("coverFileExt")String coverFileExt, @Param("coverFileWidth")Integer coverFileWidth, @Param("coverFileHeight")Integer coverFileHeight);
    
    int updateProductCategoryBaseInfo(@Param("categoryName")String categoryName,@Param("description")String description, @Param("categoryBindPage")String categoryBindPage,@Param("sort")Integer sort,@Param("formTemplate")String formTemplate,@Param("id")Integer id);
    
    int updateParentCategoryID(@Param("id")Integer id, @Param("parentId")Integer parentID);
    
    int updateLeafable(@Param("id")Integer id, @Param("leafable")Integer leafable);
    
    List<PDTCategoryDO> selectByParentID(@Param("parentId")Integer parentID);
    
    List<PDTCategoryDO> selectByColorable_ExistProduct(@Param("colorable")Integer colorable, @Param("existProduct")Integer existProduct);
    
    List<PDTCategoryDO> selectBySizeTemplateId_ExistProduct(@Param("sizeTemplateId")Integer sizeTemplateId, @Param("existProduct")Integer existProduct);
    
    List<PDTCategoryDO> selectByBrandId_ExistProduct(@Param("brandId")Integer brandId, @Param("existProduct")Integer existProduct);
    
    List<Integer> selectLevel1Category();
    
}