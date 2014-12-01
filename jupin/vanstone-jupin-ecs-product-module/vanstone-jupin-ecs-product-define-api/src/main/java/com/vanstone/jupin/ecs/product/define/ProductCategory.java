/**
 * 
 */
package com.vanstone.jupin.ecs.product.define;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.attribute.AbstractAttribute;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;

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
	/**表单模板*/
	private String formTemplate;
	/**封面图片*/
	private ImageBean coverImage;
	/** 排序 */
	private int sort = Constants.SYS_DEFAULT_SORT;
	/** 是否为叶子节点 */
	private boolean leafable = false;
	/** 栏目状态*/
	private CategoryState categoryState = CategoryState.Active;
	/**是否存在商品*/
	private boolean existProduct = false;
	/**是否存在颜色*/
	private boolean skuColor=false;
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
	private Collection<AbstractAttribute> attributes = new ArrayList<AbstractAttribute>();
	/** 旗下全部属性 */
	private Collection<AbstractAttribute> currentAttributes = new ArrayList<AbstractAttribute>();
	/**旗下关联品牌*/
	private Collection<Brand> brands = new ArrayList<Brand>();
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public ImageBean getCoverImage() {
		return coverImage;
	}
	
	public void setCoverImage(ImageBean coverImage) {
		this.coverImage = coverImage;
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

	public String getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
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

	public boolean isSkuColor() {
		return skuColor;
	}

	public void setSkuColor(boolean skuColor) {
		this.skuColor = skuColor;
	}

	public SizeTemplate getSizeTemplate() {
		return sizeTemplate;
	}

	public void setSizeTemplate(SizeTemplate sizeTemplate) {
		this.sizeTemplate = sizeTemplate;
	}
	
	public boolean isExistSkuTemplate() {
		return this.sizeTemplate != null;
	}
	
	//==========================leaf product categories==================//
	public Collection<ProductCategory> getLeafProductCategories() {
		return leafProductCategories;
	}
	
	public void addLeafProductCategory(ProductCategory productCategory) {
		if (productCategory != null) {
			this.leafProductCategories.add(productCategory);
		}
	}
	
	public void addLeafProductCategories(Collection<ProductCategory> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			this.leafProductCategories.addAll(productCategories);
		}
	}
	
	public void clearLeafProductCategories() {
		this.leafProductCategories.clear();
	}
	
	//========================== child product categories================//
	public Collection<ProductCategory> getChildProductCategories() {
		return childProductCategories;
	}
	
	public void addChildProductCategory(ProductCategory productCategory) {
		if (productCategory != null) {
			childProductCategories.add(productCategory);
		}
	}
	
	public void addChildProductCategories(Collection<ProductCategory> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			childProductCategories.addAll(productCategories);
		}
	}
	
	public void clearChildProductCategories() {
		this.childProductCategories.clear();
	}
	
	//===============================All Child ProductCategoroies===============================//
	public Collection<ProductCategory> getAllChildProductCategories() {
		return allChildProductCategories;
	}
	
	public void addAllChildProductCategory(ProductCategory productCategory) {
		if (productCategory != null) {
			this.allChildProductCategories.add(productCategory);
		}
	}
	
	public void addAllChildProductCategories(Collection<ProductCategory> productCategories) {
		if (productCategories != null && productCategories.size() >0) {
			this.allChildProductCategories.addAll(productCategories);
		}
	}
	
	public void clearAllChildProductCategories() {
		this.allChildProductCategories.clear();
	}
	
	//===============================ProductCategory Node Path===============================//
	public Collection<ProductCategory> getProductCategoryNodePath() {
		return productCategoryNodePath;
	}
	
	public void addProductCategoryNodePath(ProductCategory productCategory) {
		if (productCategory != null) {
			this.productCategoryNodePath.add(productCategory);
		}
	}
	
	public void addProductCategoriesNodePath(Collection<ProductCategory> productCategories) {
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
