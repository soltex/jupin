package com.vanstone.jupin.business.sdk.adminservice.pdm;

/**
 * @author shipeng
 */
public class SizeBean {

	private Integer id;
	/** 尺码名称 */
	private String sizeName;
	/** 腰围 */
	private Integer waistlineStart;
	private Integer waistlineEnd;
	/** 体重 */
	private Integer weightStart;
	private Integer weightEnd;
	/** 臀围 */
	private Integer hipStart;
	private Integer hipEnd;
	/** 胸围 */
	private Integer chestStart;
	private Integer chestEnd;
	/** 身高 */
	private Integer heightStart;
	private Integer heightEnd;
	/** 肩宽 */
	private Integer shoulderStart;
	private Integer shoulderEnd;

	private Integer sizeTemplateId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
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

	public Integer getSizeTemplateId() {
		return sizeTemplateId;
	}

	public void setSizeTemplateId(Integer sizeTemplateId) {
		this.sizeTemplateId = sizeTemplateId;
	}

	public boolean validateSizeInfo() {
		if (this.sizeName == null || this.sizeName.equals("")) {
			return false;
		}
		
		if ((this.getWaistlineEnd() == null && this.getWaistlineStart() == null)
				&& (this.getWeightStart() == null && this.getWeightEnd() == null)
				&& (this.getHipStart() == null && this.getHipEnd() == null)
				&& (this.getChestStart() == null && this.getChestEnd() == null)
				&& (this.getHeightStart() == null && this.getHeightEnd() == null)
				&& (this.getShoulderStart() == null && this.getShoulderEnd() == null)) {
			return false;
		}
		return true;
	}
}
