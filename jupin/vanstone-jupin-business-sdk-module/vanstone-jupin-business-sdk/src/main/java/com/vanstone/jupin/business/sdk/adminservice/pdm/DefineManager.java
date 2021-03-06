/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.pdm;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.common.util.web.PageInfo;
import com.vanstone.fs.FSFile;
import com.vanstone.jupin.common.ImageFormatException;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;
import com.vanstone.jupin.ecs.product.define.services.AttributeCondition;
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
	Brand addBrand(String brandName, String brandNameEN, FSFile logoFSFile, String content) throws ImageFormatException, ObjectDuplicateException;
	
	/**
	 * @param brandId
	 * @param logoMultipartFile
	 * @return
	 * @throws ImageFormatException
	 * @throws ObjectDuplicateException
	 */
	Brand updateBrandLogoInfo(int brandId, FSFile logoFsFile) throws ImageFormatException, ObjectDuplicateException, ExistProductsNotAllowWriteException;
	
	/**
	 * 批量导出品牌信息
	 * @param fsFile 数据文件
	 * @param asyn 是否异步执行
	 * @return
	 */
	ImportBrandResultBean batchImportBrands(@NotNull FSFile fsFile,boolean asyn) throws ImportBrandFileFormatException;
	
	/**
	 * 刷新全部品类信息,异步
	 */
	void refreshAllProductCategoryDetail();
	
	/**
	 * 刷新全部品牌信息，异步
	 */
	void refreshAllBrands();
	
	/**
	 * 刷新全部属性信息，异步
	 */
	void refreshAllAttributes();
	
	/**
	 * 验证定义模块
	 */
	ValidateDefineBean validateDefineModule();
	
	/**
	 * 检索属性信息
	 * @param condition
	 * @param pageno
	 * @return
	 */
	PageInfo<AbstractAttribute> searchAttributes(AttributeCondition condition, int pageno,int size);
	
	/**
	 * 更新枚举属性基本信息
	 * @param attribtueID
	 * @param attributeName
	 * @param attributeDescrption
	 * @param listshowable
	 * @param requiredable
	 * @param multiselectable
	 * @param searchable
	 * @return
	 */
	Attr4Enum updateBaseEnumAttr(int attribtueID, @NotBlank String attributeName, String attributeDescrption, boolean listshowable, boolean requiredable, boolean multiselectable, boolean searchable);
	
	/**
	 * 更新枚举值
	 * @param valueID
	 * @param objectText
	 * @return
	 */
	Attr4Enum updateBaseAttr4EnumValue(int valueID, String objectText) throws ObjectDuplicateException ;
	
	/**
	 * 追加属性值
	 * @param attributeID
	 * @param objectText
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Attr4Enum appendAttr4EnumValue(int attributeID, String objectText) throws ObjectDuplicateException;
	
	/**
	 * 新建品类
	 * @param parentProductCategoryDetail
	 * @param categoryName
	 * @param description
	 * @param coverFSFile
	 * @param sort
	 * @param skuColor
	 * @param sizeTemplateID
	 * @param attributeIDs
	 * @return
	 */
	ProductCategoryDetail addProductCategoryDetail(Integer parentID, @NotBlank String categoryName, String description, FSFile coverFSFile, Integer sort, boolean skuColor, Integer sizeTemplateID, Collection<Integer> attributeIDs) throws ImageFormatException, ExistProductsNotAllowWriteException;
	
}
