/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import java.util.Date;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.entity.ImageBean;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Color;
import com.vanstone.jupin.ecs.product.define.attribute.sku.Size;

/**
 * 商品实例信息
 * 
 * @author shipeng
 */
public class ProductSku extends AbstractBusinessObject {

	private static final long serialVersionUID = -9069390064222987852L;
	
	private String id;
	/**所属商品*/
	private Product product;
	/**当前Sku库存量*/
	private int skuCount;
	/**当前Sku价格*/
	private double skuPrice;
	/**当前Sku市场价格*/
	private double marketPrice;
	/**当前颜色值*/
	private Color color;
	/**颜色值重定义*/
	private ImageBean colorImageBean;

	/**当前尺码值*/
	private Size size;
	/**尺码值重定义*/
	private ImageBean sizeImageBean;

	/**系统写入时间*/
	private Date sysInsertTime;
	/**系统更新时间*/
	private Date sysUpdateTime;
	/**上架时间*/
	private Date relaseTime;
	/**下架时间*/
	private Date downTime;
	/**当前Sku状态*/
	private SkuState skuState;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Date getRelaseTime() {
		return relaseTime;
	}

	public void setRelaseTime(Date relaseTime) {
		this.relaseTime = relaseTime;
	}

	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}

	public int getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(int skuCount) {
		this.skuCount = skuCount;
	}

	public double getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(double skuPrice) {
		this.skuPrice = skuPrice;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ImageBean getColorImageBean() {
		return colorImageBean;
	}

	public void setColorImageBean(ImageBean colorImageBean) {
		this.colorImageBean = colorImageBean;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public ImageBean getSizeImageBean() {
		return sizeImageBean;
	}

	public void setSizeImageBean(ImageBean sizeImageBean) {
		this.sizeImageBean = sizeImageBean;
	}

	public SkuState getSkuState() {
		return skuState;
	}

	public void setSkuState(SkuState skuState) {
		this.skuState = skuState;
	}

}
