/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.productdefine.attr.sku.Color;

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
	Color addColor(@NotNull Color color) throws ObjectDuplicateException;
	
	/**
	 * 更新颜色代码
	 * @param skuColor
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Color updateColor(@NotNull Color skuColor) throws ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
	/**
	 * 获取颜色代码值
	 * @param id
	 * @return
	 */
	Color getColor(int id);
	
	/**
	 * 获取颜色代码并验证
	 * @param id
	 * @return
	 */
	Color getColorAndValidate(int id);
	
	/**
	 * 删除颜色代码
	 * @param id
	 * @throws ObjectHasSubObjectException
	 */
	void deleteColor(int id) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 获取颜色列表，排序通过排序字段以及自然ID进行排序(将整个列表放入到缓冲中)
	 * @return
	 */
	Collection<Color> getColors();
	
	/**
	 * 刷新代码表
	 */
	int refreshColorTable();
	
}
