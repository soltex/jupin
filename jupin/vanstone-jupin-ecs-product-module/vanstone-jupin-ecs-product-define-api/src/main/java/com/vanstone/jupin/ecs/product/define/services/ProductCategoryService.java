/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.services;

import java.util.Collection;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.BasicProductCategory;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.ProductCategoryDetail;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;

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
	 * @throws ExistProductsNotAllowWriteException 当父品类信息中是叶子节点，并且当前叶子节点下已经存在商品，则不允许增加品类信息
	 */
	ProductCategoryDetail addProductCategory(@NotNull ProductCategoryDetail productCategory) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 添加品类信息
	 * @param productCategory
	 * @param attributes
	 * @return
	 */
	ProductCategoryDetail addProductCategory(@NotNull ProductCategoryDetail productCategory, Collection<AbstractAttribute> attributes) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 获取品类信息（从缓冲中直接获取）
	 * @param id
	 * @return
	 */
	ProductCategoryDetail getProductCategoryDetail(int id);
	
	/**
	 * 获取品类信息并验证
	 * @param id
	 * @return
	 */
	ProductCategoryDetail getProductCategoryDetailAndValidate(int id);
	
	/**
	 * 获取根节点信息,直接缓冲列表
	 * @return
	 */
	Collection<ProductCategoryDetail> getProductCategoriesOfLevel1();
	
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
	ProductCategoryDetail updateBaseProductCategoryInfo(int id, String categoryName, String description, String categoryBindPage, String formTemplate, Integer sort) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 更新品类父级信息
	 * @param id
	 * @param parentCategoryID
	 * @return
	 */
	ProductCategoryDetail updateParentProductCategory(int id, Integer parentCategoryID) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 更新品类封面图片
	 * @param id
	 * @param coverImage
	 * @return
	 */
	ProductCategoryDetail updateProductCategoryCoverImage(int id, @NotNull ImageBean coverImage) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 删除品类封面图片
	 * @param id
	 * @return
	 */
	ProductCategoryDetail deleteProductCategoryCoverImage(int id) throws ExistProductsNotAllowWriteException;
	
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
	void addAttributesToProductCategory(int productCategoryId, Collection<AbstractAttribute> attributes) throws ObjectDuplicateException;
	
	/**
	 * @param productCategoryId
	 * @param attribute
	 * @return
	 * @throws CategoryHasChildCategoriesException
	 */
	void addAttributeToProductCategory(int productCategoryId, AbstractAttribute attribute) throws ObjectDuplicateException;
	
	/**
	 * 删除品类上的属性信息
	 * @param productCategoryId
	 * @param attributeId
	 * @throws ExistProductsNotAllowWriteException
	 */
	void deleteAttributeInProductCategory(ProductCategoryDetail productCategoryId, AbstractAttribute attribute) throws ExistProductsNotAllowWriteException;
	
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
	 * 检索品类列表
	 * @param key
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<ProductCategoryDetail> getProductCategories(String key, int offset, int limit);
	
	/**
	 * 获取品类定义Map
	 * @param ids
	 * @return
	 */
	Map<Integer, ProductCategoryDetail> getProductCategoriesMap(Collection<Integer> ids);
	
	/**
	 * 检索品类信息
	 * @param key
	 * @return
	 */
	int getTotalProductCategories(String key);
	
	/**
	 * 验证是否允许对Category进行更新删除操作
	 * @param categoryID
	 * @return
	 */
	boolean validateAllowUDOperateCategory(int categoryID);
	
	/**
	 * 追加品牌信息
	 * @param baseProductCategory
	 * @param brand
	 * @return
	 * @throws ObjectDuplicateException
	 */
	ProductCategoryDetail appendBrandToProductCategoryDetail(BasicProductCategory baseProductCategory, Brand brand) throws CategoryMustLeafNodeException, ObjectDuplicateException;
	
	/**
	 * 追加一组品牌信息到品类信息中
	 * @param baseProductCategory
	 * @param brands
	 * @return
	 * @throws CategoryMustLeafNodeException
	 * @throws ObjectDuplicateException
	 */
	ProductCategoryDetail appendBrandsToProductCategoryDetail(BasicProductCategory baseProductCategory, Collection<Brand> brands) throws CategoryMustLeafNodeException, ObjectDuplicateException;
	
	/**
	 * 从ProductCategory清理Brand
	 * @param productCategory
	 * @param brand
	 * @return
	 */
	void deleteBrandFromProductCategory(BasicProductCategory basicProductCategory, Brand brand) throws ExistProductsNotAllowWriteException;
	
	/**
	 * 获取终端叶子节点下的品类列表
	 * @param basicProductCategoryOfLeafNode
	 * @return
	 */
	Collection<Brand> getBrandsByLeafProductCategory(BasicProductCategory basicProductCategoryOfLeafNode);
	
}
