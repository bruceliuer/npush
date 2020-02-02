package com.npush.task;

import com.npush.connection.Connection;

public interface ScanTask {
	
	/**
	 * 
	 * @param now 扫描触发的时间点
 	 */
	public void visit(long now,Connection conn);

}