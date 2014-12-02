/**
 * 
 */
package com.vanstone.jupin.ecs.product.framework.persistence.object;

/**
 * @author shipeng
 */
public class QuerySizeTemplateDOWithSizeTableResultMap extends PDTSkuSizeTableDO {

	private String templateName;
	private String templateContent;
	private Integer systemable;
	private Integer templateId;
	
	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getSystemable() {
		return systemable;
	}
	
	public void setSystemable(Integer systemable) {
		this.systemable = systemable;
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

}
