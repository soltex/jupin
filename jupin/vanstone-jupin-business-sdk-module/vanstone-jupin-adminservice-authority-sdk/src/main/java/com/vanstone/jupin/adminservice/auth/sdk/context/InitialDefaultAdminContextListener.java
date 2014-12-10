/**
 * 
 */
package com.vanstone.jupin.adminservice.auth.sdk.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.framework.business.ServiceManagerFactory;
import com.vanstone.jupin.authority.Admin;
import com.vanstone.jupin.business.sdk.adminservice.auth.AdminManager;

/**
 * 默认Admin账户初始化
 * @author shipeng
 */
public class InitialDefaultAdminContextListener implements ServletContextListener {

	private static Logger LOG = LoggerFactory.getLogger(InitialDefaultAdminContextListener.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		AdminManager adminManager = ServiceManagerFactory.getInstance().getService(AdminManager.SERVICE);
		Admin admin = adminManager.initialDefaultAdmin();
		LOG.debug("Initial Default Admin Name And Password. {}, {} ", admin.getAdminName(), admin.getPassword());
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	
}
