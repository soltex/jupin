/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute.sku;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 *  尺寸模板
 * @author shipeng
 */
public class SizeTemplate extends AbstractBusinessObject {
	
	/***/
	private static final long serialVersionUID = -802837290691345905L;
	
	private Integer id;
	/**模板名称*/
	private String templateName;
	/**模板内容*/
	private String templateContent;
	/**是否为系统内置*/
	private boolean systemable = true;
	/**腰围*/
	private boolean waistlineable = false;
	/**体重*/
	private boolean weightable = false;
	/**臀围*/
	private boolean hipable = false;
	/**胸围*/
	private boolean chestable=false;
	/**身高*/
	private boolean heightable=false;
	/**肩宽*/
	private boolean shoulderable = false;
	/**尺码数量*/
	private int sizeCount = COUNT_TYPE_FIELD_NOT_INITIAL_VALUE;
	
	public SizeTemplate() {}
	
	public SizeTemplate(int sizeCount) {
		this.sizeCount = sizeCount;
	}
	
	public int getSizeCount() {
		return sizeCount;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public boolean isSystemable() {
		return systemable;
	}

	public void setSystemable(boolean systemable) {
		this.systemable = systemable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setSizeCount(int sizeCount) {
		this.sizeCount = sizeCount;
	}

	public boolean isWaistlineable() {
		return waistlineable;
	}

	public void setWaistlineable(boolean waistlineable) {
		this.waistlineable = waistlineable;
	}

	public boolean isWeightable() {
		return weightable;
	}

	public void setWeightable(boolean weightable) {
		this.weightable = weightable;
	}

	public boolean isHipable() {
		return hipable;
	}

	public void setHipable(boolean hipable) {
		this.hipable = hipable;
	}

	public boolean isChestable() {
		return chestable;
	}

	public void setChestable(boolean chestable) {
		this.chestable = chestable;
	}

	public boolean isHeightable() {
		return heightable;
	}

	public void setHeightable(boolean heightable) {
		this.heightable = heightable;
	}

	public boolean isShoulderable() {
		return shoulderable;
	}

	public void setShoulderable(boolean shoulderable) {
		this.shoulderable = shoulderable;
	}
	
}
