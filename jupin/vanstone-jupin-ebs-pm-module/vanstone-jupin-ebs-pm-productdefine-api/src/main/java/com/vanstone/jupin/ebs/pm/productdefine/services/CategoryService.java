/**
 * 
 */
package com.vanstone.jupin.ebs.pm.productdefine.services;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ebs.pm.productdefine.Brand;
import com.vanstone.jupin.ebs.pm.productdefine.ProductCategory;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.AbstractAttribute;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Enum;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4EnumValue;
import com.vanstone.jupin.ebs.pm.productdefine.attribute.Attr4Text;

/**
 * 品类以及品类属性业务方法
 * @author shipeng
 */
public interface CategoryService {
	
	/**SERVICE*/
	public static final String SERVICE = "productCategoryService";
	
	/**
	 * 添加品类信息
	 * @param productCategory
	 * @return
	 * @throws ExistProductsNotAllowWriteException 当父品类信息中是叶子节点，并且当前叶子节点下已经存在商品，则不允许增加品类信息
	 */
	ProductCategory addProductCategory(@NotNull ProductCategory productCategory) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 添加品类信息
	 * @param productCategory
	 * @param attributes
	 * @return
	 */
	ProductCategory addProductCategory(@NotNull ProductCategory productCategory, Collection<AbstractAttribute> attributes) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 获取品类信息（从缓冲中直接获取）
	 * @param id
	 * @return
	 */
	ProductCategory getProductCategoryDetail(int id);
	
	/**
	 * 获取品类信息并验证
	 * @param id
	 * @return
	 */
	ProductCategory getProductCategoryDetailAndValidate(int id);
	
	/**
	 * 获取根节点信息,直接缓冲列表
	 * @return
	 */
	Collection<ProductCategory> getProductCategoriesOfLevel1();
	
	/**
	 * 更新品类基本信息
	 * @param id
	 * @param categoryName
	 * @param description
	 * @param categoryBindPage
	 * @param formTemplate
	 * @param sort
	 * @return
	 */
	ProductCategory updateBaseProductCategoryInfo(int id, String categoryName, String description, String categoryBindPage, String formTemplate, Integer sort) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 更新品类父级信息
	 * @param id
	 * @param parentCategoryID
	 * @return
	 */
	ProductCategory updateParentProductCategory(int id, Integer parentCategoryID);
	
	/**
	 * 更新品类封面图片
	 * @param id
	 * @param coverImage
	 * @return
	 */
	ProductCategory updateProductCategoryCoverImage(int id, @NotNull ImageBean coverImage) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 删除品类封面图片
	 * @param id
	 * @return
	 */
	ProductCategory deleteProductCategoryCoverImage(int id) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 删除品类信息，错误码详见异常信息
	 * @param id
	 * @throws ExistProductsNotAllowWriteException,CategoryHashSubCategoriesException
	 */
	void deleteProductCategory(int id) throws ExistProductsNotAllowWriteException, CategoryHasChildCategoriesException;
	
	/**
	 * @param productCategoryId
	 * @param attribute
	 * @return
	 * @throws CategoryHasChildCategoriesException
	 */
	void addAttributesToProductCategory(int productCategoryId, Collection<AbstractAttribute> attributes) throws CategoryHasChildCategoriesException,ObjectDuplicateException;
	
	/**
	 * @param productCategoryId
	 * @param attribute
	 * @return
	 * @throws CategoryHasChildCategoriesException
	 */
	void addAttributeToProductCategory(int productCategoryId, AbstractAttribute attribute) throws CategoryHasChildCategoriesException,ObjectDuplicateException;
	
	/**
	 * 删除品类上的属性信息
	 * @param productCategoryId
	 * @param attributeId
	 * @throws ExistProductsNotAllowWriteException
	 */
	void deleteAttributeInProductCategory(ProductCategory productCategoryId, AbstractAttribute attribute) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 强制删除品类信息
	 * @param id
	 */
	void forceDeleteProductCategory(int id);
	
	/**
	 * 刷新品类信息
	 * @param id
	 */
	void refreshProductCategory(int id);
	
	/**
	 * 更新ExistProductState状态
	 * @param productCategoryID
	 * @param existProduct
	 */
	void updateExistProductState(int productCategoryID, boolean existProduct);
	
	/**
	 * 获取分类下的品类详情列列表
	 * @param parentID 当ParentID为Null
	 * @return
	 */
	Collection<ProductCategory> getProductCategories(Integer parentID);
	
	/**
	 * 判断属性是否存在于ProductCategory中
	 * @param productCategoryID
	 * @param attribute
	 * @return
	 */
	boolean attributeExistInProductCategory(int productCategoryID, AbstractAttribute attribute);
	
	/**
	 * 判断属性是否存在于ProductCategory中
	 * @param productCategoryID
	 * @param attributes
	 * @return
	 */
	boolean attributesExistInProductCategory(int productCategoryID, Collection<AbstractAttribute> attributes);
	
	/**
	 * 添加文本类型属性
	 * @param attr4Text
	 * @return
	 */
	Attr4Text addAttr4Text(Attr4Text attr4Text);
	
	/**
	 * 添加枚举类型
	 * @param attr4Enum
	 * @param objectValues
	 * @return
	 */
	Attr4Enum addAttr4Enum(Attr4Enum attr4Enum);
	
	/**
	 * 新增枚举值
	 * @param attr4Enum
	 * @param objectValue
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Attr4Enum appendAttr4EnumValue(Attr4Enum attr4Enum, String objectValue, Integer sort) throws ObjectDuplicateException;
	
	/**
	 * 更新枚举值信息
	 * @param attr4EnumValue
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Attr4Enum updateAttr4EnumValue(Attr4EnumValue attr4EnumValue) throws ObjectDuplicateException;
	
	/**
	 * 删除枚举值,当枚举值不存在了，一并删除属性
	 * @param enumValueId
	 * @return
	 */
	void deleteAttr4EnumValue(int enumValueId);
	
	/**
	 * 获取枚举值
	 * @param enumValueID
	 * @return
	 */
	Attr4EnumValue getAttr4EnumValue(int enumValueID);
	
	/**
	 * 更新文本属性信息
	 * @param attr4Text
	 * @return
	 */
	Attr4Text updateAttr4Text(Attr4Text attr4Text);
	
	/**
	 * 更新基本信息
	 * @param attr4Enum
	 * @return
	 */
	Attr4Enum updateBaseAttr4Enum(Attr4Enum attr4Enum);
	
	/**
	 * 获取属性
	 * @param id
	 * @return
	 */
	AbstractAttribute getAttribute(int id);
	
	/**
	 * 通过一组ID获取属性Map
	 * @param ids
	 * @return
	 */
	Map<Integer, AbstractAttribute> getAttributesByIDsMap(Collection<Integer> ids);
	
	/**
	 * 刷新缓冲中的Attribute
	 * @param id
	 * @return
	 */
	AbstractAttribute refreshAttribute(int id);
	
	/**
	 * 通过商品品类获取Brand列表
	 * @param productCategory
	 * @return
	 */
	Collection<Brand> getBrands(ProductCategory productCategory);
	
	/**
	 * 检索品类列表
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<ProductCategory> getProductCategories(String key, int offset, int limit);
	
	/**
	 * 检索品类信息
	 * @param key
	 * @return
	 */
	int getTotalProductCategories(String key);
	
}
