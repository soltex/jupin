package com.vanstone.jupin.ecs.product.framework.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.ecs.product.framework.persistence.object.PDTAttributeDefDO;

@MyBatisRepository
public interface PDTAttributeDefDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTAttributeDefDO record);

    int insertSelective(PDTAttributeDefDO record);

    PDTAttributeDefDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeDefDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeDefDO record);

    int updateByPrimaryKey(PDTAttributeDefDO record);
    
    List<Integer> selectIDsByCategoryIDs_Searchable(@Param("categoryIds")Integer[] categoryIds, @Param("searchable")boolean searchable);
    
    List<Integer> selectIDsByCondition(PDTAttributeDefDO condition, RowBounds rowBounds);
    
    int selectCountByCondition(PDTAttributeDefDO condition);
}