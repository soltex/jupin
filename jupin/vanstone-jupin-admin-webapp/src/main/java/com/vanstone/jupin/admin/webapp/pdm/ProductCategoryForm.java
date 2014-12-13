/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vanstone.jupin.admin.webapp.AdminBaseForm;

/**
 * @author shipeng
 */
public class ProductCategoryForm extends AdminBaseForm {

	private Integer parentProductCategoryId;
	private String categoryName;
	private String description;
	private String categoryBindPage;
	private String formTemplate;
	private MultipartFile coverImageFile;
	private Integer sort;
	private boolean skuColor = false;
	private boolean existSizeTemplate = false;
	private Integer sizeTemplateId;
	private List<Integer> attributeIds = new ArrayList<Integer>();

	private Integer level1;
	private Integer level2;
	private Integer level3;
	private Integer level4;
	private Integer level5;
	private Integer level6;
	
	public Integer getParentProductCategoryId() {
		return parentProductCategoryId;
	}

	public void setParentProductCategoryId(Integer parentProductCategoryId) {
		this.parentProductCategoryId = parentProductCategoryId;
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

	public String getCategoryBindPage() {
		return categoryBindPage;
	}

	public void setCategoryBindPage(String categoryBindPage) {
		this.categoryBindPage = categoryBindPage;
	}

	public String getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}

	public MultipartFile getCoverImageFile() {
		return coverImageFile;
	}

	public void setCoverImageFile(MultipartFile coverImageFile) {
		this.coverImageFile = coverImageFile;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isSkuColor() {
		return skuColor;
	}

	public void setSkuColor(boolean skuColor) {
		this.skuColor = skuColor;
	}

	public boolean isExistSizeTemplate() {
		return existSizeTemplate;
	}

	public void setExistSizeTemplate(boolean existSizeTemplate) {
		this.existSizeTemplate = existSizeTemplate;
	}

	public Integer getSizeTemplateId() {
		return sizeTemplateId;
	}

	public void setSizeTemplateId(Integer sizeTemplateId) {
		this.sizeTemplateId = sizeTemplateId;
	}

	public List<Integer> getAttributeIds() {
		return attributeIds;
	}

	public void setAttributeIds(List<Integer> attributeIds) {
		this.attributeIds = attributeIds;
	}

	public Integer getLevel1() {
		return level1;
	}

	public void setLevel1(Integer level1) {
		this.level1 = level1;
	}

	public Integer getLevel2() {
		return level2;
	}

	public void setLevel2(Integer level2) {
		this.level2 = level2;
	}

	public Integer getLevel3() {
		return level3;
	}

	public void setLevel3(Integer level3) {
		this.level3 = level3;
	}

	public Integer getLevel4() {
		return level4;
	}

	public void setLevel4(Integer level4) {
		this.level4 = level4;
	}

	public Integer getLevel5() {
		return level5;
	}

	public void setLevel5(Integer level5) {
		this.level5 = level5;
	}

	public Integer getLevel6() {
		return level6;
	}

	public void setLevel6(Integer level6) {
		this.level6 = level6;
	}

}
