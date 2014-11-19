/**
 * 
 */
package com.vanstone.jupin.framework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;

import com.vanstone.framework.business.services.AbstractBusinessService;

/**
 * @author shipeng
 */
public class AbstractJupinService extends AbstractBusinessService {

	/** */
	private static final long serialVersionUID = 3327326916326047121L;
	
	@Autowired
	@Qualifier(value="jupin_jdbc_transactionmanager")
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		super.setTransactionManager(transactionManager);
	}
	
}
