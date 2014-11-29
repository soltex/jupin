/**
 * 
 */
package com.vanstone.jupin.ebs.pm.productdefine;

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
	/**品牌名称*/
	private String brandName;
	/**品牌英文名称*/
	private String brandNameEN;
	/**品牌首字母*/
	private String brandNamefirstLetter;
	/**品牌介绍*/
	private String content;
	/**是否系统内置品牌*/
	private boolean systemable = false;
	/**Logo图片*/
	private ImageBean logoImage;
	
	/**旗下叶子节点品类数量*/
	private Integer productCategoryCount;
	/**旗下产品数量*/
	private Integer productCount;
	
	/**
	 * 默认使用在缓冲中
	 */
	public Brand() {
		
	}
	
	/**
	 * 后台统计分析使用
	 * @param productCategoryCount
	 * @param productCount
	 */
	public Brand(Integer productCategoryCount, Integer productCount) {
		this.productCategoryCount = productCategoryCount;
		this.productCount = productCount;
	}
	
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

	public Integer getProductCategoryCount() {
		return productCategoryCount;
	}

	public Integer getProductCount() {
		return productCount;
	}
	
	/**
	 * 是否存在LogoInfo
	 * @return
	 */
	public boolean existLogoInfo() {
		return this.logoImage != null;
	}
}
