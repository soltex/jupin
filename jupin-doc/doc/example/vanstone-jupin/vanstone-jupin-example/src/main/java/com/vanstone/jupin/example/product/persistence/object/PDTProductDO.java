/**
 * 
 */
package com.vanstone.jupin.example.product.persistence.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vanstone.jupin.framework.common.ImageObject;
import com.vanstone.jupin.framework.productdef.AbstractAttributeValue;

/**
 * @author shipeng
 * 
 */
@Document(collection = "product_collection")
public class PDTProductDO {
	@Id
	private String id;
	/** 产品品类 */
	private Integer productCategoryId;
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
	private Integer brandId;
	/** 商品包装清单 */
	private String packingList;
	/** 商品售后服务信息 */
	private String afterSaleInfo;
	/** 商品库存信息 */
	private Integer inventory;
	/** 商品图片信息 */
	private Collection<ImageObject> images = new ArrayList<ImageObject>();
	/** 系统写入时间 */
	private Date sysInsertTime;
	/** 系统更新时间 */
	private Date sysUpdateTime;
	/** 商品自定义属性值 */
	private Map<Integer, AbstractAttributeValue> productProperties = new LinkedHashMap<Integer, AbstractAttributeValue>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
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

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
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

	public void setImages(Collection<ImageObject> images) {
		this.images = images;
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

	public Map<Integer, AbstractAttributeValue> getProductProperties() {
		return productProperties;
	}

	public void addProductProperty(Integer attributeId, AbstractAttributeValue attributeValue) {
		productProperties.put(attributeId, attributeValue);
	}
	
}
