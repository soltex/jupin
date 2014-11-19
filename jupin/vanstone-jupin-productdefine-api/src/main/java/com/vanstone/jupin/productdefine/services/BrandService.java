/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.productdefine.Brand;
import com.vanstone.jupin.productdefine.ProductCategory;

/**
 * 品牌业务API
 * @author shipeng
 */
public interface BrandService {
	
	/**
	 * 品牌名称
	 * @param brand
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Brand addBrand(Brand brand) throws ObjectDuplicateException;
	
	/**
	 * 添加品牌信息
	 * @param brand
	 * @param productCategory
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Brand addBrand(Brand brand, ProductCategory productCategory) throws ObjectDuplicateException;
	
	/**
	 * 通过ID获取品牌信息
	 * @param id
	 * @return
	 */
	Brand getBrand(int id);
	
	/**
	 * 获取品牌列表
	 * @param brandId
	 * @return
	 */
	Collection<Brand> getBrandsByLeafBrand(int brandId);
	
	/**
	 * 获取品牌列表
	 * @param brandId
	 * @return
	 */
	Collection<Brand> getBrandsByBrand(int brandId);
	
	/**
	 * 更新品牌基本信息
	 * @param brandId
	 * @param brandName
	 * @param brandNameEN
	 * @param content
	 * @param systemable
	 * @return
	 */
	Brand updateBrandBaseInfo(int brandId, String brandName, String brandNameEN, String content, boolean systemable);
	
	/**
	 * 更新品牌基本信息
	 * @param brandId
	 * @param imageBean
	 * @return
	 */
	Brand updateBrandLogoInfo(int brandId, ImageBean imageBean);
	
	/**
	 * 删除品牌信息
	 * @param brandId
	 * @throws ObjectHasSubObjectException
	 */
	void deleteBrand(int brandId) throws ObjectHasSubObjectException;
	
	/**
	 * 强制删除品牌信息
	 * @param brandId
	 */
	void forceDeleteBrand(int brandId);
	
}
