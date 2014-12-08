/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import org.springframework.web.multipart.MultipartFile;

import com.vanstone.jupin.admin.webapp.AdminBaseForm;

/**
 * @author shipeng
 */
public class BrandForm extends AdminBaseForm {
	private Integer id;
	private String brandName;
	private String brandNameEN;
	private String content;
	private MultipartFile logoMultipartFile;
	
	private MultipartFile batchImportFile;
	
	public MultipartFile getBatchImportFile() {
		return batchImportFile;
	}

	public void setBatchImportFile(MultipartFile batchImportFile) {
		this.batchImportFile = batchImportFile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandNameEN() {
		return brandNameEN;
	}

	public void setBrandNameEN(String brandNameEN) {
		this.brandNameEN = brandNameEN;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MultipartFile getLogoMultipartFile() {
		return logoMultipartFile;
	}

	public void setLogoMultipartFile(MultipartFile logoMultipartFile) {
		this.logoMultipartFile = logoMultipartFile;
	}

	
	//==========================search form==========================//
	private Integer categoryID;
	private String key;
	
	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
