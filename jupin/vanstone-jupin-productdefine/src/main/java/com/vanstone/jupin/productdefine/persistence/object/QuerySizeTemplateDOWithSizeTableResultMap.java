/**
 * 
 */
package com.vanstone.jupin.productdefine.persistence.object;

/**
 * @author shipeng
 */
public class QuerySizeTemplateDOWithSizeTableResultMap extends PDTSkuSizeTableDO {

	private String templateName;
	private String templateContent;

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
