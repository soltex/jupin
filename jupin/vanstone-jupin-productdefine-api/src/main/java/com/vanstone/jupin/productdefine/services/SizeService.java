/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.productdefine.attr.sku.Size;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplateWrapBean;

/**
 * 包含Size表API以及Size Template API
 * @author shipeng
 */
public interface SizeService {
	
	/**SERVICE*/
	public static final String SERVICE = "sizeService";
	
	/**
	 * 添加尺寸模板
	 * @param templateName
	 * @param content
	 * @param sizes
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SizeTemplate addSizeTemplate(String templateName, String content, Collection<Size> sizes) throws ObjectDuplicateException;
	
	/**
	 * 更新尺码模板信息
	 * @param sizeTemplate
	 * @param sizes
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SizeTemplate updateSizeTemplate(SizeTemplate sizeTemplate, Collection<Size> sizes) throws ObjectDuplicateException;
	
	/**
	 * 删除尺码模板
	 * @param sizeTemplateId
	 * @return
	 * @throws CategoryHasProductsException
	 */
	SizeTemplate deleteSizeTemplate(int sizeTemplateId) throws CategoryHasProductsException;
	
	/**
	 * 获取尺码模板列表
	 * @return
	 */
	Collection<SizeTemplate> getSizeTemplates();
	
	/**
	 * 添加尺码
	 * @param size
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Size addSize(Size size) throws ObjectDuplicateException;
	
	/**
	 * 更新尺码信息
	 * @param size
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Size updateSize(Size size) throws ObjectDuplicateException, CategoryHasProductsException;
	
	/**
	 * 删除尺码
	 * @param sizeId
	 */
	void deleteSize(int sizeId) throws CategoryHasProductsException;
	
	/**
	 * 获取尺码模板列表（并带有统计信息，以及尺码信息）
	 * @return
	 */
	Collection<SizeTemplateWrapBean> getSizeTemplatesWithStat();
	
	/**
	 * 获取尺寸模板详情(放置到缓冲）
	 * @param id
	 * @return
	 */
	SizeTemplate getSizeTemplate(int id);
	
}
