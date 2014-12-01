/**
 * 
 */
package com.vanstone.jupin.ecs.product.index;

import java.util.Date;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * @author shipeng
 */
public class ProductIndex extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = 846989881639696499L;

	private String id;
	private Date sysInsertTime;
	private Date sysUpdateTime;
	
	@Override
	public String getId() {
		return id;
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
	
	public void setId(String id) {
		this.id = id;
	}
	
}
