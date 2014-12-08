/**
 * 
 */
package com.vanstone.jupin.common.properties;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vanstone.framework.context.SpringContextHolder;

/**
 * @author shipeng
 */
@Component("globalPropertiesManager")
public class GlobalPropertiesManager {
	
	private Properties globalProperties;
	
	/**
	 * 获取全局资源文件
	 * @return
	 */
	@PostConstruct
	public void init() {
		globalProperties = SpringContextHolder.getBean("globalProperties");
	}
	
	/**
	 * 获取属性值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return this.globalProperties.getProperty(key);
	}
	
}
