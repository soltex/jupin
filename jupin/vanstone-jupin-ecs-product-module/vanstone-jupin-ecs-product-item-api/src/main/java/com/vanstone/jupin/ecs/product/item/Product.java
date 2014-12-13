/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.BasicProductCategory;
import com.vanstone.jupin.ecs.product.define.Brand;

/**
 * 商品信息
 * 
 * @author shipeng
 */
public class Product extends AbstractBusinessObject {

	private static final long serialVersionUID = -3733251817562299608L;

	private BasicProductCategory productCategory;
	private String id;
	private boolean newable = true;
	private String productName;
	private String productSubName;
	private String productNo;
	private int skuCount;
	private double price;
	private ImageBean mainImage;
	private Brand brand;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BasicProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(BasicProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public boolean isNewable() {
		return newable;
	}

	public void setNewable(boolean newable) {
		this.newable = newable;
	}

	public String getProductSubName() {
		return productSubName;
	}

	public void setProductSubName(String productSubName) {
		this.productSubName = productSubName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(int skuCount) {
		this.skuCount = skuCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ImageBean getMainImage() {
		return mainImage;
	}

	public void setMainImage(ImageBean mainImage) {
		this.mainImage = mainImage;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
}
