/**
 * 
 */
package com.vanstone.jupin.admin.webapp.pdm;

import java.util.ArrayList;
import java.util.List;

import com.vanstone.jupin.admin.webapp.AdminBaseForm;

/**
 * 尺码表单
 * 
 * @author shipeng
 */
public class SizeForm extends AdminBaseForm {

	private Integer sizeTemplateId;
	/** 模板名称 */
	private String templateName;
	/** 模板内容 */
	private String templateContent;
	/** 尺码名称 */
	private List<String> sizeNames = new ArrayList<String>();
	private String sizeName;
	/** 腰围 */
	private boolean waistlineable = false;
	private List<Integer> waistlineStarts = new ArrayList<Integer>();
	private List<Integer> waistlineEnds = new ArrayList<Integer>();
	private Integer waistlineStart;
	private Integer waistlineEnd;
	/** 体重 */
	private boolean weightable = false;
	private List<Integer> weightStarts = new ArrayList<Integer>();
	private List<Integer> weightEnds = new ArrayList<Integer>();
	private Integer weightStart;
	private Integer weightEnd;
	/** 臀围 */
	private boolean hipable = false;
	private List<Integer> hipStarts = new ArrayList<Integer>();
	private List<Integer> hipEnds = new ArrayList<Integer>();
	private Integer hipStart;
	private Integer hipEnd;
	/** 胸围 */
	private boolean chestable;
	private List<Integer> chestStarts = new ArrayList<Integer>();
	private List<Integer> chestEnds = new ArrayList<Integer>();
	private Integer chestStart;
	private Integer chestEnd;
	/** 身高 */
	private boolean heightable;
	private List<Integer> heightStarts = new ArrayList<Integer>();
	private List<Integer> heightEnds = new ArrayList<Integer>();
	private Integer heightStart;
	private Integer heightEnd;
	/** 肩宽 */
	private boolean shoulderable = false;
	private List<Integer> shoulderStarts = new ArrayList<Integer>();
	private List<Integer> shoulderEnds = new ArrayList<Integer>();
	private Integer shoulderStart;
	private Integer shoulderEnd;

	private Integer sizeId;
	private List<Integer> sizeIds = new ArrayList<Integer>();

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

	public List<String> getSizeNames() {
		return sizeNames;
	}

	public void setSizeNames(List<String> sizeNames) {
		this.sizeNames = sizeNames;
	}

	public boolean isWaistlineable() {
		return waistlineable;
	}

	public void setWaistlineable(boolean waistlineable) {
		this.waistlineable = waistlineable;
	}

	public List<Integer> getWaistlineStarts() {
		return waistlineStarts;
	}

	public void setWaistlineStarts(List<Integer> waistlineStarts) {
		this.waistlineStarts = waistlineStarts;
	}

	public List<Integer> getWaistlineEnds() {
		return waistlineEnds;
	}

	public void setWaistlineEnds(List<Integer> waistlineEnds) {
		this.waistlineEnds = waistlineEnds;
	}

	public boolean isWeightable() {
		return weightable;
	}

	public void setWeightable(boolean weightable) {
		this.weightable = weightable;
	}

	public List<Integer> getWeightStarts() {
		return weightStarts;
	}

	public void setWeightStarts(List<Integer> weightStarts) {
		this.weightStarts = weightStarts;
	}

	public List<Integer> getWeightEnds() {
		return weightEnds;
	}

	public void setWeightEnds(List<Integer> weightEnds) {
		this.weightEnds = weightEnds;
	}

	public boolean isHipable() {
		return hipable;
	}

	public void setHipable(boolean hipable) {
		this.hipable = hipable;
	}

	public List<Integer> getHipStarts() {
		return hipStarts;
	}

	public void setHipStarts(List<Integer> hipStarts) {
		this.hipStarts = hipStarts;
	}

	public List<Integer> getHipEnds() {
		return hipEnds;
	}

	public void setHipEnds(List<Integer> hipEnds) {
		this.hipEnds = hipEnds;
	}

	public boolean isChestable() {
		return chestable;
	}

	public void setChestable(boolean chestable) {
		this.chestable = chestable;
	}

	public List<Integer> getChestStarts() {
		return chestStarts;
	}

	public void setChestStarts(List<Integer> chestStarts) {
		this.chestStarts = chestStarts;
	}

	public List<Integer> getChestEnds() {
		return chestEnds;
	}

	public void setChestEnds(List<Integer> chestEnds) {
		this.chestEnds = chestEnds;
	}

	public boolean isHeightable() {
		return heightable;
	}

	public void setHeightable(boolean heightable) {
		this.heightable = heightable;
	}

	public List<Integer> getHeightStarts() {
		return heightStarts;
	}

	public void setHeightStarts(List<Integer> heightStarts) {
		this.heightStarts = heightStarts;
	}

	public List<Integer> getHeightEnds() {
		return heightEnds;
	}

	public void setHeightEnds(List<Integer> heightEnds) {
		this.heightEnds = heightEnds;
	}

	public boolean isShoulderable() {
		return shoulderable;
	}

	public void setShoulderable(boolean shoulderable) {
		this.shoulderable = shoulderable;
	}

	public List<Integer> getShoulderStarts() {
		return shoulderStarts;
	}

	public void setShoulderStarts(List<Integer> shoulderStarts) {
		this.shoulderStarts = shoulderStarts;
	}

	public List<Integer> getShoulderEnds() {
		return shoulderEnds;
	}

	public void setShoulderEnds(List<Integer> shoulderEnds) {
		this.shoulderEnds = shoulderEnds;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public List<Integer> getSizeIds() {
		return sizeIds;
	}

	public void setSizeIds(List<Integer> sizeIds) {
		this.sizeIds = sizeIds;
	}

	public Integer getWaistlineStart() {
		return waistlineStart;
	}

	public void setWaistlineStart(Integer waistlineStart) {
		this.waistlineStart = waistlineStart;
	}

	public Integer getWaistlineEnd() {
		return waistlineEnd;
	}

	public void setWaistlineEnd(Integer waistlineEnd) {
		this.waistlineEnd = waistlineEnd;
	}

	public Integer getWeightStart() {
		return weightStart;
	}

	public void setWeightStart(Integer weightStart) {
		this.weightStart = weightStart;
	}

	public Integer getWeightEnd() {
		return weightEnd;
	}

	public void setWeightEnd(Integer weightEnd) {
		this.weightEnd = weightEnd;
	}

	public Integer getHipStart() {
		return hipStart;
	}

	public void setHipStart(Integer hipStart) {
		this.hipStart = hipStart;
	}

	public Integer getHipEnd() {
		return hipEnd;
	}

	public void setHipEnd(Integer hipEnd) {
		this.hipEnd = hipEnd;
	}

	public Integer getChestStart() {
		return chestStart;
	}

	public void setChestStart(Integer chestStart) {
		this.chestStart = chestStart;
	}

	public Integer getChestEnd() {
		return chestEnd;
	}

	public void setChestEnd(Integer chestEnd) {
		this.chestEnd = chestEnd;
	}

	public Integer getHeightStart() {
		return heightStart;
	}

	public void setHeightStart(Integer heightStart) {
		this.heightStart = heightStart;
	}

	public Integer getHeightEnd() {
		return heightEnd;
	}

	public void setHeightEnd(Integer heightEnd) {
		this.heightEnd = heightEnd;
	}

	public Integer getShoulderStart() {
		return shoulderStart;
	}

	public void setShoulderStart(Integer shoulderStart) {
		this.shoulderStart = shoulderStart;
	}

	public Integer getShoulderEnd() {
		return shoulderEnd;
	}

	public void setShoulderEnd(Integer shoulderEnd) {
		this.shoulderEnd = shoulderEnd;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

}
