/**
 * 
 */
package com.vanstone.jupin.example.product;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.framework.common.ImageObject;

/**
 * @author shipeng
 * 
 */
public class Brand extends AbstractBusinessObject {

	/**  */
	private static final long serialVersionUID = -2695367114512950207L;

	/** 自然ID */
	private Integer id;
	/** 品牌名称 */
	private String brandName;
	/** 品牌描述 */
	private String description;
	/** 品牌Logo */
	private ImageObject logoImageObject;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.business.def.AbstractBusinessObject#getId()
	 */
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ImageObject getLogoImageObject() {
		return logoImageObject;
	}

	public void setLogoImageObject(ImageObject logoImageObject) {
		this.logoImageObject = logoImageObject;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
