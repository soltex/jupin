/**
 * 
 */
package com.vanstone.jupin.framework.productdef.services;

import java.util.Collection;
import java.util.List;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.business.ObjectHasSubObjectException;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.ProductCategory;

/**
 * ProductDef业务方法
 * @author shipeng
 */
public interface IProductDefService {
	
	/** SERVICE*/
	public static final String SERVICE = "productDefService";
	
	/**
	 * 初始化根节点栏目信息
	 */
	void initDefaultProductCategory();
	
	/**
	 *  增加产品分类
	 * @param productCategory
	 * @param productAttributes
	 * @return
	 */
	ProductCategory addProductCategory(ProductCategory productCategory, Collection<AbstractAttribute> productAttributes);
	
	/**
	 * 更新产品分类基本信息, 只更新categoryName, description, categoryBindPage, formTemplate, sort
	 * @param productCategory
	 * @return
	 */
	ProductCategory updateBaseProductCategoryInfo(ProductCategory productCategory);
	
	/**
	 * 更新封面图片信息
	 * @param cid
	 * @param imageObject
	 * @return
	 */
	ProductCategory updateCoverImageOfProductCategory(int cid, ImageObject imageObject);
	
	/**
	 * 获取产品分类信息
	 * @param cid,如cid <= 0则，获取根节点的产品分类
	 * @return
	 */
	ProductCategory getProductCategory(int cid);
	
	/**
	 * 获取第一层节点列表
	 * @return
	 */
	Collection<ProductCategory> getRootproductCategories();
	
	/**
	 * 删除ProductCategory
	 * @param cid
	 * @throws ObjectHasSubObjectException
	 */
	void deleteProductCategory(int cid) throws ObjectHasSubObjectException;
	
	/**
	 * 强制删除ProductCategory
	 * @param cid
	 */
	void forceDeleteProductCategory(int cid);
	
	/**
	 * 刷新全部ProductCategory
	 */
	void refreshAllProductCategories();
	
	/**
	 * 为品类增加属性
	 * @param cid
	 * @param attribute
	 * @return
	 * @throws ObjectDuplicateException
	 */
	ProductCategory appendAttribute(int cid, AbstractAttribute attribute) throws ObjectDuplicateException;
	
	/**
	 * 为品类增加一组属性
	 * @param cid
	 * @param attributes
	 * @return
	 * @throws ObjectDuplicateException
	 */
	ProductCategory appendAttributes(int cid, Collection<AbstractAttribute> attributes) throws ObjectDuplicateException;
	
	/**
	 * 增加属性定义
	 * @param attribute
	 * @return
	 */
	AbstractAttribute addAttribute(AbstractAttribute attribute);
	
	/**
	 * 获取Attribute定义信息
	 * @param aid
	 * @return
	 */
	AbstractAttribute getAttribute(int aid);
	
	/**
	 * 通过Ids获取AbstractAttribute
	 * @param ids
	 * @return
	 */
	List<AbstractAttribute> getAttributeByIds(List<Integer> ids);
	
	/**
	 * 更新Attribute基本信息
	 * @param attribute
	 * @return
	 */
	AbstractAttribute updateBaseAttribute(AbstractAttribute attribute);
	
	/**
	 * 删除Attribute信息
	 * @param aid
	 */
	void deleteAttribute(int aid);
	
	/**
	 * 添加EnumValue值
	 * @param aid
	 * @param value
	 * @return
	 */
	void addEnumValue(int aid, String value, int sort) throws ObjectDuplicateException ;
	
	/**
	 * 删除Enum Value
	 * @param enumid
	 */
	void deleteEnumValue(int enumid);
	
	/**
	 * 更新EnumValue值
	 * @param enumid
	 * @param objectvalue
	 * @param sort
	 */
	void updateEnumValue(int enumid, String objectvalue, int sort);
	
	/**
	 * Key为关键字，主要检索ID，AttributeName， AttributeDescription， EnumValue的ObjectValue
	 * @param key
	 * @param limit 当limit <=0 ,不限定数量
	 * @return
	 */
	Collection<AbstractAttribute> searchAttributes(String key, int limit);
	
}
