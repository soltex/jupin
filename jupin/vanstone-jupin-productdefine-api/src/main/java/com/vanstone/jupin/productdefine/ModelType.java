/**
 * 
 */
package com.vanstone.jupin.productdefine;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;

/**
 * 型号信息
 * @author shipeng
 */
public class ModelType extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = 2592062817357215563L;
	
	/**型号ID*/
	private Integer id;
	/**型号名*/
	private String model;
	/**排序*/
	private int sort = Constants.SYS_DEFAULT_SORT;
	
	/* (non-Javadoc)
	 * @see com.vanstone.business.def.AbstractBusinessObject#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
