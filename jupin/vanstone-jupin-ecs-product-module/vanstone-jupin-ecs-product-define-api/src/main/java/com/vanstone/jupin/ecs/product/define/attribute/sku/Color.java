/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute.sku;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.jupin.common.Constants;

/**
 * SkuColor对象
 * 
 * @author shipeng
 */
public class Color extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = 8186076824578174154L;
	
	/**ID*/
	private Integer id;
	/**颜色名称*/
	private String colorName;
	/**颜色RGB值*/
	private String colorRGB;
	/**颜色CSS值*/
	private String colorCSS;
	/**排序值*/
	private int sort = Constants.SYS_DEFAULT_SORT;
	
	@Override
	public Integer getId() {
		return this.id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	public String getColorCSS() {
		return colorCSS;
	}

	public void setColorCSS(String colorCSS) {
		this.colorCSS = colorCSS;
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
