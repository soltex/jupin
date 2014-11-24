package com.vanstone.jupin.productdefine.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vanstone.dal.mybatis.MyBatisRepository;
import com.vanstone.jupin.productdefine.persistence.object.PDTSkuColorTableDO;

@MyBatisRepository
public interface PDTSkuColorTableDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PDTSkuColorTableDO record);

    int insertSelective(PDTSkuColorTableDO record);

    PDTSkuColorTableDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PDTSkuColorTableDO record);

    int updateByPrimaryKey(PDTSkuColorTableDO record);
    
    PDTSkuColorTableDO selectByColorName(@Param("colorName")String colorName);
    
    PDTSkuColorTableDO selectByColorName_NotSelf(@Param("colorName")String colorName,@Param("id")Integer id);
    
    List<PDTSkuColorTableDO> selectAll();
    
}