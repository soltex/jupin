/**
 * 
 */
package com.vanstone.jupin.ecs.product.define;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;

/**
 * 商品品类
 * @author shipeng
 */
public class ProductCategoryDetail extends BasicProductCategory {

	/***/
	private static final long serialVersionUID = -310782740763204047L;
	
	
	
	
	
	
	/** 品类下的叶子节点 */
	private Collection<ProductCategoryDetail> leafProductCategories = new ArrayList<ProductCategoryDetail>();
	/** 品类下的一级孩子节点 */
	private Collection<ProductCategoryDetail> childProductCategories = new ArrayList<ProductCategoryDetail>();
	/** 所有孩子节点，包含叶子一级非叶子节点 */
	private Collection<ProductCategoryDetail> allChildProductCategories = new ArrayList<ProductCategoryDetail>();
	/** 品类路径 不包含ROOT节点 */
	private Collection<ProductCategoryDetail> productCategoryNodePath = new ArrayList<ProductCategoryDetail>();
	
	
	/** 用来检索用的属性 */
	private Collection<Attr4Enum> searchAttributes = new ArrayList<Attr4Enum>();
	/** 全部商品属性 */
	private Collection<AbstractAttribute> attributes = new ArrayList<AbstractAttribute>();
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> currentAttributes = new ArrayList<AbstractAttribute>();
	/**旗下关联品牌*/
	private Collection<Brand> brands = new ArrayList<Brand>();
	
	
	
	
	
	
	//==========================leaf product categories==================//
	public Collection<ProductCategoryDetail> getLeafProductCategories() {
		return leafProductCategories;
	}
	
	public void addLeafProductCategory(ProductCategoryDetail productCategory) {
		if (productCategory != null) {
			this.leafProductCategories.add(productCategory);
		}
	}
	
	public void addLeafProductCategories(Collection<ProductCategoryDetail> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			this.leafProductCategories.addAll(productCategories);
		}
	}
	
	public void clearLeafProductCategories() {
		this.leafProductCategories.clear();
	}
	
	//========================== child product categories================//
	public Collection<ProductCategoryDetail> getChildProductCategories() {
		return childProductCategories;
	}
	
	public void addChildProductCategory(ProductCategoryDetail productCategory) {
		if (productCategory != null) {
			childProductCategories.add(productCategory);
		}
	}
	
	public void addChildProductCategories(Collection<ProductCategoryDetail> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			childProductCategories.addAll(productCategories);
		}
	}
	
	public void clearChildProductCategories() {
		this.childProductCategories.clear();
	}
	
	//===============================All Child ProductCategoroies===============================//
	public Collection<ProductCategoryDetail> getAllChildProductCategories() {
		return allChildProductCategories;
	}
	
	public void addAllChildProductCategory(ProductCategoryDetail productCategory) {
		if (productCategory != null) {
			this.allChildProductCategories.add(productCategory);
		}
	}
	
	public void addAllChildProductCategories(Collection<ProductCategoryDetail> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			this.allChildProductCategories.addAll(productCategories);
		}
	}
	
	public void clearAllChildProductCategories() {
		this.allChildProductCategories.clear();
	}
	
	//===============================ProductCategory Node Path===============================//
	public Collection<ProductCategoryDetail> getProductCategoryNodePath() {
		return productCategoryNodePath;
	}
	
	public void addProductCategoryNodePath(ProductCategoryDetail productCategory) {
		if (productCategory != null) {
			this.productCategoryNodePath.add(productCategory);
		}
	}
	
	public void addProductCategoriesNodePath(Collection<ProductCategoryDetail> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			this.productCategoryNodePath.addAll(productCategories);
		}
	}
	
	public void clearProductCategoryNodePath() {
		this.productCategoryNodePath.clear();
	}
	
	//=======================search Attributes======================//
	public Collection<Attr4Enum> getSearchAttributes() {
		return searchAttributes;
	}
	
	public void addSearchSearchAttribute(Attr4Enum attr4Enum) {
		if (attr4Enum != null) {
			this.searchAttributes.add(attr4Enum);
		}
	}
	
	public void addSearchAttributes(Collection<Attr4Enum> attr4Enums) {
		if (attr4Enums != null && attr4Enums.size() >0) {
			this.searchAttributes.addAll(attr4Enums);
		}
	}
	
	public void clearSearchAttributes() {
		this.searchAttributes.clear();
	}
	
	//=====================attributes===========================//
	public Collection<AbstractAttribute> getAttributes() {
		return attributes;
	}
	
	public void addAttribute(AbstractAttribute attribute) {
		if (attribute != null) {
			attributes.add(attribute);
		}
	}
	
	public void addAttributes(Collection<AbstractAttribute> attributes) {
		if (attributes != null && attributes.size() >0) {
			for(AbstractAttribute attribute : attributes) {
				this.attributes.add(attribute);
			}
		}
	}
	
	public void clearAttributes() {
		this.attributes.clear();
	}
	//====================current attributes======================//
	public Collection<AbstractAttribute> getCurrentAttributes() {
		return currentAttributes;
	}
	
	public void addCurrentAttribute(AbstractAttribute attribute) {
		if (attribute != null) {
			currentAttributes.add(attribute);
		}
	}
	
	public void addCurrentAttributes(Collection<AbstractAttribute> attributes) {
		if (attributes != null && attributes.size() >0) {
			for (AbstractAttribute attribute : attributes) {
				currentAttributes.add(attribute);
			}
		}
	}
	
	public void clearCurrentAttributes() {
		currentAttributes.clear();
	}
	//===================brand s==============================//
	public Collection<Brand> getBrands() {
		return brands;
	}
	
	public void addBrand(Brand brand) {
		if (brand != null) {
			this.brands.add(brand);
		}
	}
	
	public void addBrands(Collection<Brand> brands) {
		if (brands != null && brands.size() >0) {
			this.brands.addAll(brands);
		}
	}
	
	public void clearBrands() {
		this.brands.clear();
	}
	
}
