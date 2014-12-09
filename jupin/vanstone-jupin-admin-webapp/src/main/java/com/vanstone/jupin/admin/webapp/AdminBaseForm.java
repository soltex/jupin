/**
 * 
 */
package com.vanstone.jupin.admin.webapp;

/**
 * @author shipeng
 * 
 */
public class AdminBaseForm {
	private Integer pageNum = 1;
	private String rel = "container";

	public Integer getPageNum() {
		if (pageNum == null) {
			return 1;
		}
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

}
