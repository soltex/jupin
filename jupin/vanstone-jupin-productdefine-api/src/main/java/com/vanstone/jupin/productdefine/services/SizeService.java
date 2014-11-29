/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
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
	 * @param systemable 系统内置
	 * @param waistlineable 腰围
	 * @param weightable	体重
	 * @param hipable	臀围
	 * @param chestable	胸围
	 * @param heightable	身高
	 * @param shoulderable	肩宽
	 * @param sizes 尺寸集合
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SizeTemplate addSizeTemplate(@NotNull String templateName, String content, boolean systemable, boolean waistlineable,boolean weightable,boolean hipable,boolean chestable, boolean heightable,boolean shoulderable,@NotEmpty Collection<Size> sizes) throws ObjectDuplicateException;
	
	/**
	 * 更新SizeTemplate基本信息
	 * @param id
	 * @param templateName
	 * @param content
	 * @return
	 * @throws ExistProductsNotAllowWriteException
	 */
	SizeTemplate updateBaseSizeTemplateInfo(int id, String templateName, String content) throws ObjectHasSubObjectException, ExistProductsNotAllowWriteException;
	
	/**
	 * 更新尺码详情信息
	 * @param id
	 * @param systemable
	 * @param waistlineable
	 * @param weightable
	 * @param hipable
	 * @param chestable
	 * @param heightable
	 * @param shoulderable
	 * @param sizes
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SizeTemplate updateSizeTemplate(int id, boolean systemable, boolean waistlineable,boolean weightable,boolean hipable,boolean chestable, boolean heightable,boolean shoulderable,@NotEmpty Collection<Size> sizes) throws ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
	/**
	 * 删除尺码模板
	 * @param sizeTemplateId
	 * @return
	 * @throws ExistProductsNotAllowWriteException
	 */
	void deleteSizeTemplate(int sizeTemplateId) throws ExistProductsNotAllowWriteException;
	
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
	Size addSize(Size size) throws ObjectDuplicateException,ExistProductsNotAllowWriteException;
	
	/**
	 * 更新尺码信息
	 * @param size
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Size updateSize(Size size) throws ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
	/**
	 * 删除尺码
	 * @param sizeId
	 */
	void deleteSize(int sizeId) throws ExistProductsNotAllowWriteException;
	
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
	
	/**
	 * 通过Id获取Size
	 * @param sizeId
	 * @return
	 */
	Size getSize(int sizeId);
	
	/**
	 * 获取并验证
	 * @param id
	 * @return
	 */
	SizeTemplate getSizeTemplateAndValidate(int id);
	
}
