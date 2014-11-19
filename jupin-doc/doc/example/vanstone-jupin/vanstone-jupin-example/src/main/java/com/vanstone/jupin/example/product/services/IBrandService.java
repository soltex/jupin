/**
 * 
 */
package com.vanstone.jupin.example.product.services;

import java.util.Collection;
import java.util.Map;

import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.example.product.Brand;
import com.vanstone.jupin.framework.common.ImageObject;

/**
 * 品牌业务方法
 * @author shipeng
 */
public interface IBrandService {
	
	public static final String SERVICE = "brandService";
	
	/**
	 * 添加品牌
	 * @param brand
	 * @return
	 */
	Brand addBrand(Brand brand);
	
	/**
	 * 获取品牌
	 * @param id
	 * @return
	 */
	Brand getBrand(int id);
	
	/**
	 * 通过Ids获取Map<Integer, Brand>
	 * @param ids
	 * @return
	 */
	Map<Integer, Brand> getBrandsMapByIds(Collection<Integer> ids);
	
	/**
	 * 更新品牌信息
	 * @param brand
	 * @return
	 */
	Brand updateBaseBrandInfo(Brand brand);
	
	/**
	 * 更新Logo信息
	 * @param brandId
	 * @param imageObject
	 * @return
	 */
	Brand updateLogoBrand(int brandId, ImageObject imageObject);
	
	/**
	 * 删除品牌信息
	 * @param id
	 * @throws ObjectHasSubObjectException
	 */
	void deleteBrand(int id) throws ObjectHasSubObjectException;
	
	/**
	 * 获取品牌列表
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Brand getBrands(String key, int offset ,int limit);
	
	/**
	 * 获取品牌数量
	 * @param key
	 * @return
	 */
	int getTotalBrands(String key);
	
}
