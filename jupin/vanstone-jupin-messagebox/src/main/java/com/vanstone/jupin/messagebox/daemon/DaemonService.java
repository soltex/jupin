/**
 * 
 */
package com.vanstone.jupin.messagebox.daemon;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vanstone.jupin.messagebox.Constants;
import com.vanstone.jupin.messagebox.PersistenceManager;

/**
 * @author shipeng
 */
@Service("messageBoxDaemonService")
public class DaemonService {
	
	private static Logger LOG = LoggerFactory.getLogger(DaemonService.class);
	
	private ScheduledExecutorService scheduledExecutorService;
	
	@PostConstruct
	public void init() {
		this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
		this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Collection<String> keies = PersistenceManager.getInstance().getMessageBoxKeies();
				if (keies != null && keies.size() >0) {
					for (String key : keies) {
						long count = PersistenceManager.getInstance().getMessageCount(key);
						if (count <=0) {
							PersistenceManager.getInstance().deleteBykey(key);
							LOG.info("DELETE MessageBox {}", key);
						}
					}
				}
			}
		}, 0 , Constants.DAEMON_SERVICE_PERIOD_SECOND, TimeUnit.SECONDS);
		LOG.info("Daemon Service Startup");
	}
	
	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}
	
}
