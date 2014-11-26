/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.productdefine.ProductCategory;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.Attr4Text;

/**
 * 品类以及品类属性业务方法
 * @author shipeng
 */
public interface ProductCategoryService {
	
	/**SERVICE*/
	public static final String SERVICE = "productCategoryService";
	
	/**
	 * 添加品类信息
	 * @param productCategory
	 * @return
	 * @throws CategoryHasProductsException 当父品类信息中是叶子节点，并且当前叶子节点下已经存在商品，则不允许增加品类信息
	 */
	ProductCategory addProductCategory(@NotNull ProductCategory productCategory) throws CategoryHasProductsException;
	
	/**
	 * 添加品类信息
	 * @param productCategory
	 * @param attributes
	 * @return
	 */
	ProductCategory addProductCategory(@NotNull ProductCategory productCategory, Collection<AbstractAttribute> attributes) throws CategoryHasProductsException;
	
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
	 * 获取根节点信息，缓冲（ROOT作为ID虚拟存储）
	 * @return
	 */
	ProductCategory getRootProductCategoryDetail();
	
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
	ProductCategory updateBaseProductCategoryInfo(int id, String categoryName, String description, String categoryBindPage, String formTemplate, Integer sort) throws CategoryHasProductsException;
	
	/**
	 * 更新品类封面图片
	 * @param id
	 * @param coverImage
	 * @return
	 */
	ProductCategory updateProductCategoryCoverImage(int id, @NotNull ImageBean coverImage) throws CategoryHasProductsException;
	
	/**
	 * 删除品类封面图片
	 * @param id
	 * @return
	 */
	ProductCategory deleteProductCategoryCoverImage(int id) throws CategoryHasProductsException;
	
	/**
	 * 删除品类信息，错误码详见异常信息
	 * @param id
	 * @throws CategoryHasProductsException,CategoryHashSubCategoriesException
	 */
	void deleteProductCategory(int id) throws CategoryHasProductsException, CategoryHasSubCategoriesException;
	
	/**
	 * @param productCategoryId
	 * @param attribute
	 * @return
	 * @throws CategoryHasSubCategoriesException
	 */
	void addAttributeToProductCategory(int productCategoryId, Collection<AbstractAttribute> attributes) throws CategoryHasSubCategoriesException,ObjectDuplicateException;
	
	/**
	 * 删除品类上的属性信息
	 * @param productCategoryId
	 * @param attributeId
	 * @throws CategoryHasProductsException
	 */
	void deleteAttributeInProductCategory(int productCategoryId, int attributeId) throws CategoryHasProductsException;
	
	/**
	 * 强制删除品类信息
	 * @param id
	 */
	void forceDeleteProductCategory(int id);
	
	/**
	 * 刷新全部ProductCategory
	 */
	void refreshAllProductCategories();
	
	/**
	 * 刷新品类信息
	 * @param id
	 */
	void refreshProductCategory(int id);
	
	/**
	 * 刷新根节点品类信息
	 */
	void refreshRootProductCategory();
	
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
	
	AbstractAttribute addAttribute(AbstractAttribute attribute);

	Attr4Text updateAttr4Text(Attr4Text attr4Text);
	
	Attr4Enum updateAttr4Enum(Attr4Enum attr4Enum);
	
	AbstractAttribute getAttribute(int id);
	
	Collection<AbstractAttribute> getAttributesByIDs(Collection<Integer> ids);
	
}
