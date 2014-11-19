/**
 * 
 */
package com.vanstone.jupin.log;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vanstone.business.def.AbstractBusinessObject;

/**
 * @author shipeng
 * 
 */
public class AbstractJupinLog extends AbstractBusinessObject {

	/**  */
	private static final long serialVersionUID = -2657128446917044692L;

	private String id;
	private String title;
	private String content;
	private Date sysInsertTime;
	private LogLevel logLevel = LogLevel.Info;
	private Map<String, Object> params = new LinkedHashMap<String, Object>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vanstone.business.def.AbstractBusinessObject#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSysInsertTime() {
		return sysInsertTime;
	}

	public void setSysInsertTime(Date sysInsertTime) {
		this.sysInsertTime = sysInsertTime;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getParams() {
		return params;
	}
	
}
