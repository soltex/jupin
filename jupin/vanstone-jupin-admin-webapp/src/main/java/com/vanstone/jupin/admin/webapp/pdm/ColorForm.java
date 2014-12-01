/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

/**
 * @author shipeng
 * 
 */
public class ColorForm {
	/** ID */
	private Integer id;
	/** 颜色名称 */
	private String colorName;
	/** 颜色RGB值 */
	private String colorRGB;
	/** 颜色CSS值 */
	private String colorCSS;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
