/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import com.vanstone.jupin.admin.webapp.AdminBaseForm;

/**
 * 尺码表单
 * @author shipeng
 */
public class SizeForm extends AdminBaseForm {

	private Integer sizeTemplateId;
	/** 模板名称 */
	private String templateName;
	/** 模板内容 */
	private String templateContent;
	/** 尺码名称 */
	private String sizeName;
	/** 腰围 */
	private boolean waistlineable = false;
	private Integer[] waistlineStarts;
	private Integer[] waistlineEnds;
	/** 体重 */
	private boolean weightable = false;
	private Integer[] weightStarts;
	private Integer[] weightEnds;
	/** 臀围 */
	private boolean hipable = false;
	private Integer[] hipStarts;
	private Integer[] hipEnds;
	/** 胸围 */
	private boolean chestable;
	private Integer[] chestStarts;
	private Integer[] chestEnds;
	/** 身高 */
	private boolean heightable;
	private Integer[] heightStarts;
	private Integer[] heightEnds;
	/** 肩宽 */
	private boolean shoulderable = false;
	private Integer[] shoulderStarts;
	private Integer[] shoulderEnds;

	private Integer sizeId;

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public Integer getSizeTemplateId() {
		return sizeTemplateId;
	}

	public void setSizeTemplateId(Integer sizeTemplateId) {
		this.sizeTemplateId = sizeTemplateId;
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

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public boolean isWaistlineable() {
		return waistlineable;
	}

	public void setWaistlineable(boolean waistlineable) {
		this.waistlineable = waistlineable;
	}

	public Integer[] getWaistlineStarts() {
		return waistlineStarts;
	}

	public void setWaistlineStarts(Integer[] waistlineStarts) {
		this.waistlineStarts = waistlineStarts;
	}

	public Integer[] getWaistlineEnds() {
		return waistlineEnds;
	}

	public void setWaistlineEnds(Integer[] waistlineEnds) {
		this.waistlineEnds = waistlineEnds;
	}

	public boolean isWeightable() {
		return weightable;
	}

	public void setWeightable(boolean weightable) {
		this.weightable = weightable;
	}

	public Integer[] getWeightStarts() {
		return weightStarts;
	}

	public void setWeightStarts(Integer[] weightStarts) {
		this.weightStarts = weightStarts;
	}

	public Integer[] getWeightEnds() {
		return weightEnds;
	}

	public void setWeightEnds(Integer[] weightEnds) {
		this.weightEnds = weightEnds;
	}

	public boolean isHipable() {
		return hipable;
	}

	public void setHipable(boolean hipable) {
		this.hipable = hipable;
	}

	public Integer[] getHipStarts() {
		return hipStarts;
	}

	public void setHipStarts(Integer[] hipStarts) {
		this.hipStarts = hipStarts;
	}

	public Integer[] getHipEnds() {
		return hipEnds;
	}

	public void setHipEnds(Integer[] hipEnds) {
		this.hipEnds = hipEnds;
	}

	public boolean isChestable() {
		return chestable;
	}

	public void setChestable(boolean chestable) {
		this.chestable = chestable;
	}

	public Integer[] getChestStarts() {
		return chestStarts;
	}

	public void setChestStarts(Integer[] chestStarts) {
		this.chestStarts = chestStarts;
	}

	public Integer[] getChestEnds() {
		return chestEnds;
	}

	public void setChestEnds(Integer[] chestEnds) {
		this.chestEnds = chestEnds;
	}

	public boolean isHeightable() {
		return heightable;
	}

	public void setHeightable(boolean heightable) {
		this.heightable = heightable;
	}

	public Integer[] getHeightStarts() {
		return heightStarts;
	}

	public void setHeightStarts(Integer[] heightStarts) {
		this.heightStarts = heightStarts;
	}

	public Integer[] getHeightEnds() {
		return heightEnds;
	}

	public void setHeightEnds(Integer[] heightEnds) {
		this.heightEnds = heightEnds;
	}

	public boolean isShoulderable() {
		return shoulderable;
	}

	public void setShoulderable(boolean shoulderable) {
		this.shoulderable = shoulderable;
	}

	public Integer[] getShoulderStarts() {
		return shoulderStarts;
	}

	public void setShoulderStarts(Integer[] shoulderStarts) {
		this.shoulderStarts = shoulderStarts;
	}

	public Integer[] getShoulderEnds() {
		return shoulderEnds;
	}

	public void setShoulderEnds(Integer[] shoulderEnds) {
		this.shoulderEnds = shoulderEnds;
	}

}
