/**
 * 
 */
package com.vanstone.jupin.productdefine;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.entity.ImageBean;

/**
 * 品牌信息
 * @author shipeng
 */
public class Brand extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -772887321258748622L;
	
	private Integer id;
	private String brandName;
	private String brandNameEN;
	private String brandNamefirstLetter;
	private String content;
	private boolean systemable = false;
	private ImageBean logoImage;
	
	@Override
	public Integer getId() {
		return this.id;
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

	public String getBrandNamefirstLetter() {
		return brandNamefirstLetter;
	}

	public void setBrandNamefirstLetter(String brandNamefirstLetter) {
		this.brandNamefirstLetter = brandNamefirstLetter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSystemable() {
		return systemable;
	}

	public void setSystemable(boolean systemable) {
		this.systemable = systemable;
	}

	public ImageBean getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(ImageBean logoImage) {
		this.logoImage = logoImage;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
