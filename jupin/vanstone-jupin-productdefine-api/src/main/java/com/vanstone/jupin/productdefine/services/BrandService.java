/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.NotNull;

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
	
	/**SERVICE*/
	public static final String SERVICE = "brandService";
	
	/**
	 * 品牌名称
	 * @param brand
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Brand addBrand(@NotNull Brand brand) throws ObjectDuplicateException;
	
	/**
	 * 添加品牌信息
	 * @param brand
	 * @param productCategories
	 * @return
	 */
	Brand addBrand(@NotNull Brand brand, Collection<ProductCategory> productCategories) throws CategoryMustLeafNodeException, ObjectDuplicateException;
	
	/**
	 * 通过ID获取品牌信息（放入到缓冲中）
	 * @param id
	 * @return
	 */
	Brand getBrand(int id);
	
	/**
	 * 获取Brand Map
	 * @param ids
	 * @return
	 */
	Map<Integer, Brand> getBrandsMap(@NotNull Collection<Integer> ids);
	
	/**
	 * 删除品牌Logo信息
	 * @param id
	 */
	void deleteBrandLogoImage(int id);
	
	/**
	 * 更新品牌基本信息
	 * @param brandId
	 * @param brandName
	 * @param brandNameEN
	 * @param content
	 * @param systemable
	 * @return
	 */
	Brand updateBrandBaseInfo(int brandId, String brandName, String brandNameEN, String content, boolean systemable) throws ObjectDuplicateException;
	
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
	void deleteBrand(int brandId) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 强制删除品牌信息
	 * @param brandId
	 */
	void forceDeleteBrand(int brandId);
	
	/**
	 * 获取品牌列表（包含统计信息，用作后台统计使用，不进入缓冲）
	 * @param productCategory
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<Brand> getBrandsWithStat(ProductCategory productCategory, String key, int offset ,int limit);
	
	/**
	 * 获取品牌数量
	 * @param productCategory
	 * @param key
	 * @return
	 */
	int getTotalBrands(ProductCategory productCategory, String key);
	
}
