/**
 * 
 */
package com.vanstone.jupin.example.product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;
import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.AbstractAttribute;
import com.vanstone.jupin.framework.productdef.AbstractAttributeValue;
import com.vanstone.jupin.framework.productdef.ProductCategory;

/**
 * 商品信息
 * @author shipeng
 */
@Document(collection="product_collection")
public class Product extends AbstractBusinessObject {
	
	/**  */
	private static final long serialVersionUID = -5767475904231376861L;

	/** 自然ID */
	@Id
	private String id;
	/** 产品品类*/
	private ProductCategory productCategory;
	/** 产品名称 */
	private String productName;
	/** 商品子名称，可用作促销时编写，可放置于检索 */
	private String subProductName;
	/** 商品编号 */
	private String skuNO;
	/** 商品简介 */
	private String brief;
	/** 商品详情 */
	private String content;
	/** 市场价格 */
	private double marketPrice;
	/** 商品价格 */
	private double price;
	/** 品牌 */
	private Brand brand;
	/** 商品包装清单 */
	private String packingList;
	/** 商品售后服务信息 */
	private String afterSaleInfo;
	/** 商品库存信息 */
	private Integer inventory;
	/** 列表上显示的图片*/
	private ImageObject mainImage;
	/** 商品图片信息 */
	private Collection<ImageObject> images = new ArrayList<ImageObject>();
	/** 系统写入时间 */
	private Date sysInsertTime;
	/** 系统更新时间 */
	private Date sysUpdateTime;
	/** 商品自定义属性值 */
	private Map<AbstractAttribute, AbstractAttributeValue> productProperties = new LinkedHashMap<AbstractAttribute, AbstractAttributeValue>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.business.def.AbstractBusinessObject#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Map<AbstractAttribute, AbstractAttributeValue> getProductProperties() {
		return productProperties;
	}
	
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getSkuNO() {
		return skuNO;
	}

	public void setSkuNO(String skuNO) {
		this.skuNO = skuNO;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}

	public String getAfterSaleInfo() {
		return afterSaleInfo;
	}

	public void setAfterSaleInfo(String afterSaleInfo) {
		this.afterSaleInfo = afterSaleInfo;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Collection<ImageObject> getImages() {
		return images;
	}
	
	public void addImageObject(ImageObject imageObject) {
		this.images.add(imageObject);
	}
	
	public void addImageObjects(Collection<ImageObject> imageObjects) {
		this.images.addAll(imageObjects);
	}
	
	public Date getSysInsertTime() {
		return sysInsertTime;
	}

	public void setSysInsertTime(Date sysInsertTime) {
		this.sysInsertTime = sysInsertTime;
	}

	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}

	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}

	public void addProductProperty(AbstractAttribute attribute, AbstractAttributeValue attributeValue) {
		MyAssert.notNull(attribute);
		MyAssert.notNull(attributeValue);
		this.productProperties.put(attribute, attributeValue);
	}
	
	public void addProductProperties(Map<AbstractAttribute, AbstractAttributeValue> map) {
		MyAssert.notEmpty(map);
		this.productProperties.putAll(map);
	}
	
	public void clearProductProperties() {
		this.productProperties.clear();
	}

	public ImageObject getMainImage() {
		return mainImage;
	}

	public void setMainImage(ImageObject mainImage) {
		this.mainImage = mainImage;
	}
	
}
