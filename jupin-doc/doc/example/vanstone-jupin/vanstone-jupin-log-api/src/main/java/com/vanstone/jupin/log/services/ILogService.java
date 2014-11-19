/**
 * 
 */
package com.vanstone.jupin.log.services;

import java.util.Collection;

import com.vanstone.jupin.log.AbstractJupinLog;

/**
 * 日志业务方法
 * @author shipeng
 */
public interface ILogService {
	
	/**
	 * 添加日志
	 * @param log
	 * @return
	 */
	AbstractJupinLog addLog(AbstractJupinLog log);
	
	/**
	 * 查看日志信息
	 * @param id
	 * @return
	 */
	AbstractJupinLog getLog(String id);
	
	/**
	 * 通过id删除日志
	 * @param id
	 */
	void deleteLog(String id);
	
	/**
	 * 通过条件删除日志
	 */
	void deleteLogs();
	
	/**
	 * 检索日志
	 * @param offset
	 * @param limit
	 * @return
	 */
	Collection<AbstractJupinLog> searchLogs(long offset, long limit);
	
	/**
	 * 检索日志
	 * @return
	 */
	long searchTotalLogs();
	
}
