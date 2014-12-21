/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vanstone.business.MyAssert4Business;
import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.BasicProductCategory;
import com.vanstone.jupin.ecs.product.define.Brand;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Enum;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4EnumValue;
import com.vanstone.jupin.ecs.product.define.attribute.Attr4Text;

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
	
	private Map<Attr4Text, String> extendAttr4TextDataMap = new LinkedHashMap<Attr4Text, String>();
	private Map<Attr4Enum, Attr4EnumValue> extendSingleAttr4EnumDataMap = new LinkedHashMap<Attr4Enum, Attr4EnumValue>();
	private Map<Attr4Enum, List<Attr4EnumValue>> extendMultiAttr4EnumDataMap = new LinkedHashMap<Attr4Enum, List<Attr4EnumValue>>();
	
	/**商品配图*/
	private Collection<ProductImage> productImageBeans = new ArrayList<ProductImage>();
	
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

	
	/**
	 * 获取扩展文本属性信息以及值信息
	 * @return
	 */
	public Map<Attr4Text, String> getExtendAttr4TextDataMap() {
		return extendAttr4TextDataMap;
	}

	/**
	 * 写入文本类型属性
	 * @param attr4Text
	 * @param value
	 */
	public void putExtendAttr4Text(Attr4Text attr4Text, String value) {
		MyAssert4Business.objectInitialized(attr4Text);
		MyAssert4Business.hasText(value);
		this.extendAttr4TextDataMap.put(attr4Text, value);
	}
	
	/**
	 * 清理扩展文本属性值
	 */
	public void clearExtendAttr4Text() {
		this.extendAttr4TextDataMap.clear();
	}
	
	/**
	 * 获取多选枚举属性信息以及值信息
	 * @return
	 */
	public Map<Attr4Enum, List<Attr4EnumValue>> getExtendMultiAttr4EnumDataMap() {
		return extendMultiAttr4EnumDataMap;
	}
	
	/**
	 * 写入多选枚举集合
	 * @param attr4Enum
	 * @param attr4EnumValues
	 */
	public void putExtendMultiAttr4Enum(Attr4Enum attr4Enum, List<Attr4EnumValue> attr4EnumValues) {
		MyAssert4Business.objectInitialized(attr4Enum);
		MyAssert4Business.notEmpty(attr4EnumValues);
		this.extendMultiAttr4EnumDataMap.put(attr4Enum, attr4EnumValues);
	}
	
	/**
	 * 清理多选枚举集合
	 */
	public void clearExtendMultiAttr4Enum() {
		this.extendMultiAttr4EnumDataMap.clear();
	}
	
	/**
	 * 获取单选枚举属性信息以及值信息
	 * @return
	 */
	public Map<Attr4Enum, Attr4EnumValue> getExtendSingleAttr4EnumDataMap() {
		return extendSingleAttr4EnumDataMap;
	}
	
	/**
	 * 清理单选枚举集合
	 */
	public void clearExtendSingleAttr4Enum() {
		this.extendSingleAttr4EnumDataMap.clear();
	}
	
	/**
	 * 获取商品配图实例
	 * @return
	 */
	public Collection<ProductImage> getProductImageBeans() {
		return productImageBeans;
	}
	
	/**
	 * 商品配图
	 * @return
	 */
	public void addProductImageBean(ProductImage productImage) {
		MyAssert4Business.notNull(productImage);
		this.productImageBeans.add(productImage);
	}
	
}
