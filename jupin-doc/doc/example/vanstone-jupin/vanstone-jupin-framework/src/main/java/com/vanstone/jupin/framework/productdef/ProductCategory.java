/**
 * 
 */
package com.vanstone.jupin.framework.productdef;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;
import com.vanstone.jupin.framework.common.ImageObject;

/**
 * @author shipeng
 * 
 */
public class ProductCategory extends AbstractBusinessObject {

	/** */
	private static final long serialVersionUID = -8211168848654178532L;
	
	/** 父产品类型 */
	private ProductCategory parentProductCategory;
	/** 自然id */
	private Integer id;
	/** 品类名称 */
	private String categoryName;
	/** 封面图片 */
	private ImageObject coverImageObject;
	/** 品类描述 */
	private String description;
	/** 栏目绑定的URL */
	private String categoryBindPage;
	/** 排序 */
	private int sort = 0;
	/** 是否为叶子节点 */
	private boolean leafable = false;
	/** 栏目状态*/
	private CategoryStatus categoryStatus = CategoryStatus.Active;
	/** 品类下的叶子节点 */
	private Collection<ProductCategory> leafProductCategories = new ArrayList<ProductCategory>();
	/** 品类下的一级孩子节点 */
	private Collection<ProductCategory> childProductCategories = new ArrayList<ProductCategory>();
	/** 所有孩子节点，包含叶子一级非叶子节点 */
	private Collection<ProductCategory> allChildProductCategories = new ArrayList<ProductCategory>();
	/** 品类路径 不包含ROOT节点 */
	private Collection<ProductCategory> productCategoryNodePath = new ArrayList<ProductCategory>();
	/** 用来检索用的属性 */
	private Collection<Attr4Enum> searchAttributes = new ArrayList<Attr4Enum>();
	/** 全部商品属性 */
	private Collection<AbstractAttribute> allAttributes = new ArrayList<AbstractAttribute>();
	/** 全部商品属性 */
	private Collection<AbstractAttribute> allProductAttributes = new ArrayList<AbstractAttribute>();
	/** 全部商品实例属性 */
	private Collection<Attr4Enum> allSkuAttributes = new ArrayList<Attr4Enum>();
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> allCurrentAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品属性 */
	private Collection<AbstractAttribute> currentProductAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品实例属性 */
	private Collection<Attr4Enum> currentSkuAttributes = new ArrayList<Attr4Enum>();
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ProductCategory getParentProductCategory() {
		return parentProductCategory;
	}

	public void setParentProductCategory(ProductCategory parentProductCategory) {
		this.parentProductCategory = parentProductCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ImageObject getCoverImageObject() {
		return coverImageObject;
	}

	public void setCoverImageObject(ImageObject coverImageObject) {
		this.coverImageObject = coverImageObject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryBindPage() {
		return categoryBindPage;
	}

	public void setCategoryBindPage(String categoryBindPage) {
		this.categoryBindPage = categoryBindPage;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isLeafable() {
		return leafable;
	}

	public void setLeafable(boolean leafable) {
		this.leafable = leafable;
	}

	public CategoryStatus getCategoryStatus() {
		return categoryStatus;
	}
	
	public void setCategoryStatus(CategoryStatus categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	/**
	 * 获取当前品类下的全部叶子品类
	 * @return
	 */
	public Collection<ProductCategory> getLeafProductCategories() {
		return leafProductCategories;
	}
	
	/**
	 * 添加到叶子节点集合中
	 * @param leafProductCategory
	 */
	public void addToLeafProductCategoies(ProductCategory leafProductCategory) {
		MyAssert4Business.objectInitialized(leafProductCategory);
		if (!leafProductCategory.isLeafable()) {
			throw new IllegalArgumentException("Add Product Category is not leaf.");
		}
		this.leafProductCategories.add(leafProductCategory);
	}
	
	/**
	 * 添加到叶子节点集合中
	 * @param leafProductCategories
	 */
	public void addToLeafProductCategories(Collection<ProductCategory> leafProductCategories) {
		MyAssert.notEmpty(leafProductCategories);
		for (ProductCategory leafProductCategory : leafProductCategories) {
			if (!leafProductCategory.isLeafable()) {
				throw new IllegalArgumentException("Add Product Category is not leaf.");
			}
		}
		this.leafProductCategories.addAll(leafProductCategories);
	}
	
	/**
	 * 获取当前节点下的全部孩子节点
	 * @return
	 */
	public Collection<ProductCategory> getChildProductCategories() {
		return childProductCategories;
	}
	
	/**
	 * 添加到孩子节点集合中
	 * @param productCategory
	 */
	public void addToChildProductCategories(ProductCategory productCategory) {
		MyAssert4Business.objectInitialized(productCategory);
		this.childProductCategories.add(productCategory);
	}
	
	/**添加到孩子节点集合中
	 * @param productCategories
	 */
	public void addToChildProductCategories(Collection<ProductCategory> productCategories) {
		MyAssert.notEmpty(productCategories);
		this.childProductCategories.addAll(productCategories);
	}
	
	/**
	 * 获取全部子节点（包含叶子节点以及非叶子节点）
	 * @return
	 */
	public Collection<ProductCategory> getAllChildProductCategories() {
		return allChildProductCategories;
	}
	
	/**
	 * 添加到全部节点中
	 * @param productCategory
	 */
	public void addToAllChildProductCategories(ProductCategory productCategory) {
		MyAssert4Business.objectInitialized(productCategory);
		this.allChildProductCategories.add(productCategory);
	}
	
	/**
	 * 添加到全部节点中
	 * @param productCategories
	 */
	public void addToAllChildProductCategories(Collection<ProductCategory> productCategories) {
		MyAssert.notEmpty(productCategories);
		this.allChildProductCategories.addAll(productCategories);
	}
	
	/**
	 * 获取节点路径，例如  电器->家用电器->平板电视 <br>
	 * 节点顺序从高层节点开始到低级节点路径 
	 * @return
	 */
	public Collection<ProductCategory> getProductCategoryNodePath() {
		return productCategoryNodePath;
	}

	/**
	 * 添加到节点路径
	 * @param productCategory
	 */
	public void addToProductCategoryNodePath(ProductCategory productCategory) {
		MyAssert4Business.objectInitialized(productCategory);
		this.productCategoryNodePath.add(productCategory);
	}
	
	/**
	 * 添加到节点路径
	 * @param productCategories
	 */
	public void addToProductCategoryNodePath(Collection<ProductCategory> productCategories) {
		MyAssert.notEmpty(productCategories);
		this.productCategoryNodePath.addAll(productCategories);
	}
	
	/**
	 * 获取查询属性，用于条件检索使用，例如，<br /
	 * 网络： 		移动4G（TD-LTE ） 联通3G（WCDMA） 移动3G（TD-SCDMA）电信3G（CDMA2000）<br>
	 * 屏幕尺寸：  5.6英寸及以上  5.5-5.0英寸  4.9-4.1英寸  4.0-3.1英寸  3.0英寸及以下 <br>
	 * @return
	 */
	public Collection<Attr4Enum> getSearchAttributes() {
		return searchAttributes;
	}
	
	/**
	 * 添加到查询属性中
	 * @param attribute
	 */
	public void addToSearchAttributes(Attr4Enum attribute) {
		MyAssert4Business.objectInitialized(attribute);
		this.searchAttributes.add(attribute);
	}
	
	/**
	 * 添加到查询属性中
	 * @param attributes
	 */
	public void addToSearchAttribute(Collection<Attr4Enum> attributes) {
		MyAssert.notEmpty(attributes);
		this.searchAttributes.addAll(attributes);
	}
	
	/**
	 * 获取当前节点下的全部属性，包含Parent节点以及Parent等节点的属性
	 * @return
	 */
	public Collection<AbstractAttribute> getAllAttributes() {
		return allAttributes;
	}
	
	/**
	 * 添加到当前节点下全部属性集合中
	 * @param attribute
	 */
	public void addToAllAttributes(AbstractAttribute attribute) {
		MyAssert4Business.objectInitialized(attribute);
		this.allAttributes.add(attribute);
	}
	
	/**
	 * 添加到当前节点下全部属性集合中
	 * @param attributes
	 */
	public void addToAllAttributes(Collection<AbstractAttribute> attributes) {
		MyAssert.notEmpty(attributes);
		this.allAttributes.addAll(attributes);
	}
	
	/**
	 * 获取全部商品属性
	 * @return
	 */
	public Collection<AbstractAttribute> getAllProductAttributes() {
		return allProductAttributes;
	}
	
	/**
	 * 添加到全部<strong>商品</strong>属性集合中
	 * @param attribute
	 */
	public void addToAllProductAttributes(AbstractAttribute attribute) {
		MyAssert4Business.objectInitialized(attribute);
		if (!attribute.getAttributeScope().equals(AttributeScope.Product)) {
			throw new IllegalArgumentException();
		}
		this.allProductAttributes.add(attribute);
	}
	
	/**
	 * 添加到全部<strong>商品</strong>属性集合中
	 * @param attributes
	 */
	public void addToAllProductAttributes(Collection<AbstractAttribute> attributes) {
		MyAssert4Business.notEmpty(attributes);
		for (AbstractAttribute attribute : attributes) {
			MyAssert4Business.objectInitialized(attribute);
			if (!attribute.getAttributeScope().equals(AttributeScope.Product)) {
				throw new IllegalArgumentException();
			}
		}
		this.allProductAttributes.addAll(attributes);
	}
	
	/**
	 * 获取全部<strong>库存 SKU </strong>属性集合中
	 * @return
	 */
	public Collection<Attr4Enum> getAllSkuAttributes() {
		return allSkuAttributes;
	}
	
	/**
	 * 添加到全部<strong>库存 SKU </strong>属性集合中
	 * @param attribute
	 */
	public void addToAllSkuAttributes(Attr4Enum attribute) {
		MyAssert4Business.objectInitialized(attribute);
		if (!attribute.getAttributeScope().equals(AttributeScope.Sku)) {
			throw new IllegalArgumentException();
		}
		this.allSkuAttributes.add(attribute);
	}
	
	/**
	 * 添加到全部<strong>库存 SKU </strong>属性集合中
	 * @param attributes
	 */
	public void addToAllSkuAttributes(Collection<Attr4Enum> attributes) {
		MyAssert4Business.notEmpty(attributes);
		for (AbstractAttribute attribute : attributes) {
			MyAssert4Business.objectInitialized(attribute);
			if (!attribute.getAttributeScope().equals(AttributeScope.Sku)) {
				throw new IllegalArgumentException();
			}
		}
		this.allSkuAttributes.addAll(attributes);
	}
	
	/**
	 * 获取当前节点下定义的全部属性
	 * @return
	 */
	public Collection<AbstractAttribute> getAllCurrentAttributes() {
		return allCurrentAttributes;
	}
	
	/**
	 * 添加到当前节点下的属性集合中
	 * @param attribute
	 */
	public void addToAllCurrentAttribtues(AbstractAttribute attribute) {
		MyAssert4Business.objectInitialized(attribute);
		this.allCurrentAttributes.add(attribute);
	}
	
	/**
	 * 添加到当前节点下的属性集合中
	 * @param attributes
	 */
	public void addToAllCurrentAttribtues(Collection<AbstractAttribute> attributes) {
		MyAssert4Business.notEmpty(attributes);
		this.allCurrentAttributes.addAll(attributes);
	}
	
	/**
	 * 获取当前节点下定义的全部<strong>商品</strong>属性
	 * @return
	 */
	public Collection<AbstractAttribute> getCurrentProductAttributes() {
		return currentProductAttributes;
	}
	
	/**
	 * 添加到当前节点下定义的全部<strong>商品</strong>属性集合中
	 * @param attribute
	 */
	public void addToCurrentProductAttributes(AbstractAttribute attribute) {
		MyAssert4Business.objectInitialized(attribute);
		if (!attribute.getAttributeScope().equals(AttributeScope.Product)) {
			throw new IllegalArgumentException();
		}
		this.currentProductAttributes.add(attribute);
	}
	
	/**
	 * 添加到当前节点下定义的全部<strong>商品</strong>属性集合中
	 * @param attributes
	 */
	public void addToCurrentProductAttributes(Collection<AbstractAttribute> attributes) {
		MyAssert4Business.notEmpty(attributes);
		for (AbstractAttribute attribute : attributes) {
			if (!attribute.getAttributeScope().equals(AttributeScope.Product)) {
				throw new IllegalArgumentException();
			}
		}
		this.currentProductAttributes.addAll(attributes);
	}
	
	/**
	 * 获取当前节点下定义的全部<strong>库存SKU</strong>属性
	 * @return
	 */
	public Collection<Attr4Enum> getCurrentSkuAttributes() {
		return currentSkuAttributes;
	}
	
	/**
	 * 添加到当前节点下定义的全部<strong>库存SKU</strong>属性集合中
	 * @param attribute
	 */
	public void addToCurrentSkuAttributes(Attr4Enum attribute) {
		MyAssert4Business.notNull(attribute);
		if (!attribute.getAttributeScope().equals(AttributeScope.Sku)) {
			throw new IllegalArgumentException();
		}
		this.currentSkuAttributes.add(attribute);
	}
	
	/**
	 * 添加到当前节点下定义的全部<strong>库存SKU</strong>属性集合中
	 * @param attributes
	 */
	public void addToCurrentSkuAttributes(Collection<Attr4Enum> attributes) {
		MyAssert4Business.notEmpty(attributes);
		for (Attr4Enum attribute : attributes) {
			if (!attribute.getAttributeScope().equals(AttributeScope.Sku)) {
				throw new IllegalArgumentException();
			}
		}
		this.currentSkuAttributes.addAll(attributes);
	}
	
}
