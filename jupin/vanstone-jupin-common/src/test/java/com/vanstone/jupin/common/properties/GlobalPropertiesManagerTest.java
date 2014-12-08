/**
 * 
 */
package com.vanstone.jupin.common.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { 
		"classpath*:/core-context.xml",
		"classpath*:META-INF/jupin**context-module.xml" }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class GlobalPropertiesManagerTest {
	
	@Autowired
	private GlobalPropertiesManager globalPropertiesManager;
	@Autowired
	private ResourceBundleMessageSource resourceBundleMessageSource;
	
	@Test
	public void testGetValue() {
		System.out.println(globalPropertiesManager.getValue("jupin.username"));
		System.out.println(globalPropertiesManager.getValue("jupin.password"));
	}
	
}
