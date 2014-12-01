/**
 * 
 */
package com.vanstone.jupin.ecs.product.define.attribute.sku;

import com.vanstone.business.def.AbstractBusinessObject;
import com.vanstone.common.MyAssert;

/**
 * @author shipeng
 */
public class Size extends AbstractBusinessObject {

	/***/
	private static final long serialVersionUID = 7017390860600322914L;

	/***/
	private Integer id;
	/**尺码名称*/
	private String sizeName;
	/**腰围*/
	private boolean waistlineable = false;
	private Integer waistlineStart;
	private Integer waistlineEnd;
	/**体重*/
	private boolean weightable = false;
	private Integer weightStart;
	private Integer weightEnd;
	/**臀围*/
	private boolean hipable = false;
	private Integer hipStart;
	private Integer hipEnd;
	/**胸围*/
	private boolean chestable;
	private Integer chestStart;
	private Integer chestEnd;
	/**身高*/
	private boolean heightable;
	private Integer heightStart;
	private Integer heightEnd;
	/**肩宽*/
	private boolean shoulderable = false;
	private Integer shoulderStart;
	private Integer shoulderEnd;

	/**尺寸模板*/
	private SizeTemplate sizeTemplate;

	public Size() {
		//必须初始化SizeTemplate
	}
	
	public Size(SizeTemplate sizeTemplate) {
		MyAssert.notNull(sizeTemplate);
		this.sizeTemplate = sizeTemplate;
	}
	
	@Override
	public Integer getId() {
		return this.id;
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

	public SizeTemplate getSizeTemplate() {
		return sizeTemplate;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setSizeTemplate(SizeTemplate sizeTemplate) {
		this.sizeTemplate = sizeTemplate;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
}
