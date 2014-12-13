/**
 * 
 */
package com.vanstone.jupin.ecs.product.define;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.attribute.sku.SizeTemplate;

/**
 * 基础品类信息
 * 
 * @author shipeng
 */
public class BasicProductCategory extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -1720502280492663924L;
	
	/** 自然id */
	private Integer id;
	
	/** 父产品类型 */
	private ProductCategoryDetail parentProductCategory;
	/** 品类名称 */
	private String categoryName;
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

	public ProductCategoryDetail getParentProductCategory() {
		return parentProductCategory;
	}

	public void setParentProductCategory(ProductCategoryDetail parentProductCategory) {
		this.parentProductCategory = parentProductCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
