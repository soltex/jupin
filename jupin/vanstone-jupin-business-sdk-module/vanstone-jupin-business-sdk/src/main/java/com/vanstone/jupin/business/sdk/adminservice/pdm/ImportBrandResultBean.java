/**
 * 
 */
package com.vanstone.jupin.business.sdk.adminservice.pdm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author shipeng
 */
public class ImportBrandResultBean {
	private int successCount;
	private int failCount = 0;
	private Collection<String> failBrandNames = new ArrayList<String>();
	
	public int getSuccessCount() {
		return successCount;
	}
	
	public void incSuccessCount() {
		this.successCount++;
	}
	
	public int getFailCount() {
		return failCount;
	}

	public Collection<String> getFailBrandNames() {
		return failBrandNames;
	}
	
	public void addFailBrandName(String brandName) {
		this.failBrandNames.add(brandName);
		this.failCount++;
	}
	
	public void clearFailBrandName() {
		this.failBrandNames.clear();
		this.failCount = 0;
	}
	
	/**
	 * 是否全部成功
	 * @return
	 */
	public boolean isSuccess() {
		return this.failCount == 0;
	}
	
}
