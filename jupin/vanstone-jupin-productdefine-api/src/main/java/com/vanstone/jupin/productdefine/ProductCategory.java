/**
 * 
 */
package com.vanstone.jupin.productdefine;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.productdefine.attr.AbstractAttribute;
import com.vanstone.jupin.productdefine.attr.Attr4Enum;
import com.vanstone.jupin.productdefine.attr.sku.SizeTemplate;

/**
 * 商品品类
 * @author shipeng
 */
public class ProductCategory extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -310782740763204047L;
	
	/** 父产品类型 */
	private ProductCategory parentProductCategory;
	/** 自然id */
	private Integer id;
	/** 品类名称 */
	private String categoryName;
	/** 封面图片 */
	private ImageBean converImage;
	/** 品类描述 */
	private String description;
	/** 栏目绑定的URL */
	private String categoryBindPage;
	/** 排序 */
	private int sort = Constants.SYS_DEFAULT_SORT;
	/** 是否为叶子节点 */
	private boolean leafable = false;
	/** 栏目状态*/
	private CategoryState categoryState = CategoryState.Active;
	/**是否存在商品*/
	private boolean existProduct = false;
	/**是否存在颜色*/
	private boolean existColor=false;
	/**选用的尺码模板*/
	private SizeTemplate sizeTemplate;
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
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> allCurrentAttributes = new ArrayList<AbstractAttribute>();
	/** 旗下商品属性 */
	private Collection<AbstractAttribute> currentProductAttributes = new ArrayList<AbstractAttribute>();
	/**旗下关联品牌*/
	private Collection<Brand> brands = new ArrayList<Brand>();
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public boolean isLeafable() {
		return leafable;
	}

	public void setLeafable(boolean leafable) {
		this.leafable = leafable;
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

	public ImageBean getConverImage() {
		return converImage;
	}

	public void setConverImage(ImageBean converImage) {
		this.converImage = converImage;
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

	public CategoryState getCategoryState() {
		return categoryState;
	}

	public void setCategoryState(CategoryState categoryState) {
		this.categoryState = categoryState;
	}

	public boolean isExistProduct() {
		return existProduct;
	}

	public void setExistProduct(boolean existProduct) {
		this.existProduct = existProduct;
	}

	public boolean isExistColor() {
		return existColor;
	}

	public void setExistColor(boolean existColor) {
		this.existColor = existColor;
	}

	public SizeTemplate getSizeTemplate() {
		return sizeTemplate;
	}

	public void setSizeTemplate(SizeTemplate sizeTemplate) {
		this.sizeTemplate = sizeTemplate;
	}

	public Collection<ProductCategory> getLeafProductCategories() {
		return leafProductCategories;
	}
	
	public void addLeafProductCategory(ProductCategory productCategory) {
		this.leafProductCategories.add(productCategory);
	}
	
	public Collection<ProductCategory> getChildProductCategories() {
		return childProductCategories;
	}

	public void setChildProductCategories(Collection<ProductCategory> childProductCategories) {
		this.childProductCategories = childProductCategories;
	}
	
	public Collection<ProductCategory> getAllChildProductCategories() {
		return allChildProductCategories;
	}

	public void setAllChildProductCategories(
			Collection<ProductCategory> allChildProductCategories) {
		this.allChildProductCategories = allChildProductCategories;
	}

	public Collection<ProductCategory> getProductCategoryNodePath() {
		return productCategoryNodePath;
	}

	public void setProductCategoryNodePath(
			Collection<ProductCategory> productCategoryNodePath) {
		this.productCategoryNodePath = productCategoryNodePath;
	}

	public Collection<Attr4Enum> getSearchAttributes() {
		return searchAttributes;
	}

	public void setSearchAttributes(Collection<Attr4Enum> searchAttributes) {
		this.searchAttributes = searchAttributes;
	}

	public Collection<AbstractAttribute> getAllAttributes() {
		return allAttributes;
	}

	public void setAllAttributes(Collection<AbstractAttribute> allAttributes) {
		this.allAttributes = allAttributes;
	}

	public Collection<AbstractAttribute> getAllProductAttributes() {
		return allProductAttributes;
	}

	public void setAllProductAttributes(
			Collection<AbstractAttribute> allProductAttributes) {
		this.allProductAttributes = allProductAttributes;
	}

	public Collection<AbstractAttribute> getAllCurrentAttributes() {
		return allCurrentAttributes;
	}

	public void setAllCurrentAttributes(
			Collection<AbstractAttribute> allCurrentAttributes) {
		this.allCurrentAttributes = allCurrentAttributes;
	}

	public Collection<AbstractAttribute> getCurrentProductAttributes() {
		return currentProductAttributes;
	}

	public void setCurrentProductAttributes(Collection<AbstractAttribute> currentProductAttributes) {
		this.currentProductAttributes = currentProductAttributes;
	}

	public Collection<Brand> getBrands() {
		return brands;
	}

	public void setBrands(Collection<Brand> brands) {
		this.brands = brands;
	}

	public void addBrand(Brand brand) {
		MyAssert4Business.objectInitialized(brand);
		this.brands.add(brand);
	}
	
	public void clearBrands() {
		this.brands.clear();
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
