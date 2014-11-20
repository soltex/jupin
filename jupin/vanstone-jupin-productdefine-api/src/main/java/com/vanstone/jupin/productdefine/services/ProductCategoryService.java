/**
 * 
 */
package com.vanstone.jupin.productdefine.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;

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
	 * @param attributes
	 * @return
	 */
	ProductCategory addProductCategory(@NotNull ProductCategory productCategory, Collection<AbstractAttribute> attributes);
	
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
	ProductCategory updateBaseProductCategoryInfo(int id, String categoryName, String description, String categoryBindPage, String formTemplate, Integer sort);
	
	/**
	 * 更新品类封面图片
	 * @param id
	 * @param coverImage
	 * @return
	 */
	ProductCategory updateProductCategoryCoverImage(int id, ImageBean coverImage);
	
	/**
	 * 获取品类详情
	 * @param id
	 * @return
	 */
	ProductCategory getProductCategoryDetail(int id);
	
	/**
	 * 删除品类信息，错误码详见异常信息
	 * @param id
	 * @throws CategoryHasProductsException,CategoryHashSubCategoriesException
	 */
	void deleteProductCategory(int id) throws CategoryHasProductsException, CategoryHasSubCategoriesException;
	
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
	 * 获取分类下的品类详情列列表
	 * @param parentID 当ParentID为Null
	 * @return
	 */
	Collection<ProductCategory> getProductCategories(Integer parentID);
	
	AbstractAttribute addAttribute(AbstractAttribute attribute);

	Attr4Text updateAttr4Text(Attr4Text attr4Text);
	
	Attr4Enum updateAttr4Enum(Attr4Enum attr4Enum);
	
	AbstractAttribute getAttribute(int id);
	
	Collection<AbstractAttribute> getAttributesByIds(Collection<Integer> ids);
	
	/**
	 * 更新ProdcuctCategory下是否存在产品
	 * @param pcID
	 * @param usable
	 */
	void updateProductCategoryExistProductState(int pcID, boolean ExistProductState);
	
}
