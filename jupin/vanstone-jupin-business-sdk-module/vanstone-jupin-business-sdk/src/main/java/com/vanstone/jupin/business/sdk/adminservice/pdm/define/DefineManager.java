/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.pdm.define;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.fs.FSFile;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.ecs.product.define.Brand;
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
	
	/**
	 * 批量更新尺码信息
	 * @param sizeTemplateId
	 * @param systemable
	 * @param waistlineable
	 * @param weightable
	 * @param hipable
	 * @param chestable
	 * @param heightable
	 * @param shoulderable
	 * @param sizeBeans
	 * @throws ObjectDuplicateException
	 * @throws ExistProductsNotAllowWriteException
	 */
	void updateSizes(int sizeTemplateId, boolean systemable,boolean waistlineable,boolean weightable,boolean hipable,boolean chestable,boolean heightable,boolean shoulderable,@Null Collection<SizeBean> sizeBeans) throws ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
	/**
	 * 检索Brand列表
	 * @param productCategoryID
	 * @param key
	 * @param pageno
	 * @param size
	 * @return
	 */
	PageInfo<Brand> searchBrands(Integer productCategoryID, String key, int pageno, int size);
	
	/**
	 * 添加品牌信息
	 * @param brandName
	 * @param brandNameEN
	 * @param logoMultipartFile
	 * @param content
	 * @return
	 * @throws ImageFormatException
	 * @throws ObjectDuplicateException
	 */
	Brand addBrand(String brandName, String brandNameEN, MultipartFile logoMultipartFile, String content) throws ImageFormatException, ObjectDuplicateException;
	
	/**
	 * 批量导出品牌信息
	 * @param fsFile 数据文件
	 * @param asyn 是否异步执行
	 * @return
	 */
	ImportBrandResultBean batchImportBrands(@NotNull FSFile fsFile,boolean asyn) throws ImportBrandFileFormatException;
	
}
