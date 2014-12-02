/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.pdm.define;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.ExistProductsNotAllowWriteException;

/**
 * @author shipeng
 *
 */
public interface DefineManager {

	public static final String SERVICE = "defineManager";
	
	/**
	 * 添加尺码模板
	 * @param templateName	
	 * @param content
	 * @param systemable
	 * @param waistlineable
	 * @param weightable
	 * @param hipable
	 * @param chestable
	 * @param heightable
	 * @param shoulderable
	 * @param sizeBeans
	 * @return
	 * @throws ObjectDuplicateException
	 */
	SizeTemplate addSizeTemplate(@NotNull String templateName, String content, boolean systemable, boolean waistlineable,boolean weightable,boolean hipable,boolean chestable, boolean heightable,boolean shoulderable,@NotEmpty Collection<SizeBean> sizeBeans) throws ObjectDuplicateException;
	
	/**
	 * 增加尺码信息
	 * @param sizeBean
	 * @return
	 * @throws ObjectDuplicateException
	 * @throws ExistProductsNotAllowWriteException
	 */
	Size addSize(SizeBean sizeBean) throws ObjectDuplicateException,ExistProductsNotAllowWriteException;
	
	/**
	 * 更新尺码信息
	 * @param sizeBean
	 * @return
	 * @throws ObjectDuplicateException
	 * @throws ExistProductsNotAllowWriteException
	 */
	Size updateSize(SizeBean sizeBean) throws ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
}
