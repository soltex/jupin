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
 * @author shipeng
 */
public class Product extends AbstractBusinessObject {

	private static final long serialVersionUID = -3733251817562299608L;
	
	private BasicProductCategory productCategory;
	private String id;
	private boolean newable=true;
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
	
}
