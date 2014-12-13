/**
 * 
 */
package com.vanstone.jupin.ecs.store;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * 店铺信息
 * @author shipeng
 */
public class Store extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = -8936254894904482946L;
	
	private String id;
	
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
