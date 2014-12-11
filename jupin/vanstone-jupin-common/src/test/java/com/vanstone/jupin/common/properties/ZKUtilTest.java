/**
 * 
 */
package com.vanstone.jupin.common.properties;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;

import com.vanstone.zk.ZKManager;

/**
 * @author shipeng
 */
public class ZKUtilTest {
	
	@Test
	public void testReleaseLock() {
		CuratorFramework curatorFramework = ZKManager.getInstance().getCuratorFramework();
		InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/vanstone/jupin/mutex/PC_ATTR_35");
		try {
			mutex.acquire(0, TimeUnit.SECONDS);
			System.out.println("获取到锁");
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mutex != null) {
				try {
					mutex.release();
					System.out.println("释放锁");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
