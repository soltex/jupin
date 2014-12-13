/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import java.util.Date;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * 商品实例信息
 * @author shipeng
 */
public class ProductSku extends AbstractBusinessObject {
	
	private static final long serialVersionUID = -9069390064222987852L;
	private String id;
	private Product product;
	private Date sysInsertTime;
	private Date sysUpdateTime;
	private Date relaseTime;
	private Date downTime;
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
	
}
