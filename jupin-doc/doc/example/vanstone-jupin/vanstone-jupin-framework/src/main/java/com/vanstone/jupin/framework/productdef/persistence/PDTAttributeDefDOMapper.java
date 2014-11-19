package com.vanstone.jupin.framework.productdef.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.framework.productdef.persistence.object.PDTAttributeDefDO;

@MyBatisRepository
public interface PDTAttributeDefDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTAttributeDefDO record);

    int insertSelective(PDTAttributeDefDO record);

    PDTAttributeDefDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTAttributeDefDO record);

    int updateByPrimaryKeyWithBLOBs(PDTAttributeDefDO record);

    int updateByPrimaryKey(PDTAttributeDefDO record);
    
    List<Integer> selectByKey(@Param("key")String key,RowBounds rowBounds);
    
}