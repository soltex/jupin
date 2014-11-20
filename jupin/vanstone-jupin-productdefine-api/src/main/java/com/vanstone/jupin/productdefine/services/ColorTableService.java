/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.productdefine.attr.sku.SkuColor;

/**
 * 颜色表业务API
 * @author shipeng
 */
public interface ColorTableService {
	
	/***/
	public static final String SERVICE = "colorTableService";
	
	/**
	 * 添加颜色代码
	 * @param color
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SkuColor addSkuColor(SkuColor color) throws ObjectDuplicateException;
	
	/**
	 * 更新颜色代码
	 * @param skuColor
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SkuColor updateSkuColor(SkuColor skuColor) throws ObjectDuplicateException;
	
	/**
	 * 获取颜色代码值
	 * @param id
	 * @return
	 */
	SkuColor getSkuColor(int id);
	
	/**
	 * 删除颜色代码
	 * @param id
	 * @throws ObjectHasSubObjectException
	 */
	void deleteSkuColor(int id) throws ObjectHasSubObjectException;
	
	/**
	 * 强制删除颜色代码
	 * @param id
	 */
	void forceDeleteSkuColor(int id);
	
	/**
	 * 获取颜色列表，排序通过排序字段以及自然ID进行排序
	 * @return
	 */
	Collection<SkuColor> getSkuColors();
	
}
