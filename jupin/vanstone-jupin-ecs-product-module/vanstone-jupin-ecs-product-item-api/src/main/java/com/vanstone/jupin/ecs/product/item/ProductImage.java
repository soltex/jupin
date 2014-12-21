/**
 * 
 */
package com.vanstone.jupin.ecs.product.item;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;
import com.vanstone.jupin.common.entity.ImageBean;

/**
 * @author shipeng
 */
public class ProductImage extends AbstractBusinessObject {
	
	/***/
	private static final long serialVersionUID = 3621217367502608487L;
	private Integer id;
	private ImageBean imageBean;
	private boolean mainimgable = false;
	private int sort = Constants.SYS_DEFAULT_SORT;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public ImageBean getImageBean() {
		return imageBean;
	}

	public void setImageBean(ImageBean imageBean) {
		this.imageBean = imageBean;
	}

	public boolean isMainimgable() {
		return mainimgable;
	}

	public void setMainimgable(boolean mainimgable) {
		this.mainimgable = mainimgable;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
