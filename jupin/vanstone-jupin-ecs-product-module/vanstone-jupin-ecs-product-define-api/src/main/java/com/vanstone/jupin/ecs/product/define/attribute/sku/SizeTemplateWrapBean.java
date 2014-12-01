/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute.sku;

import java.util.ArrayList;
import java.util.Collection;

import com.vanstone.common.MyAssert;

/**
 * @author shipeng
 */
public class SizeTemplateWrapBean {

	/** 尺码模板 */
	private SizeTemplate sizeTemplate;
	/** 尺码详情列表 */
	private Collection<Size> sizes = new ArrayList<Size>();
	/** 是否允许UD操作 */
	private boolean udable = false;

	public SizeTemplate getSizeTemplate() {
		return sizeTemplate;
	}

	public void setSizeTemplate(SizeTemplate sizeTemplate) {
		this.sizeTemplate = sizeTemplate;
	}

	public Collection<Size> getSizes() {
		return sizes;
	}

	public void addSize(Size size) {
		MyAssert.notNull(size);
		this.sizes.add(size);
	}

	public void addSizes(Collection<Size> sizes) {
		this.sizes.addAll(sizes);
	}
	
	public void clearSizes() {
		this.sizes.clear();
	}

	public boolean isUdable() {
		return udable;
	}

	public void setUdable(boolean udable) {
		this.udable = udable;
	}

}
