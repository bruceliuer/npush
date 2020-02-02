package com.npush.task;

import com.npush.connection.Connection;
import com.npush.connection.ConnectionManager;
import com.npush.util.Constants;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时全量扫描connection  
 *
 */
public class ScanAllClientConnection implements TimerTask {

	private static final Logger log = LoggerFactory.getLogger(ScanAllClientConnection.class);

	private final List<ScanTask> taskList = new ArrayList<ScanTask>();
	
	public ScanAllClientConnection(final ScanTask...scanTasks) {
		if(scanTasks!=null){
			for(final ScanTask task:scanTasks){
				this.taskList.add(task);
			}
		}
	}
	
	@Override
	public void run(Timeout timeout) throws Exception {
		try{
			final long now = System.currentTimeMillis();
			List<Connection> connections = ConnectionManager.INSTANCE.getConnections();
			if(connections!=null){
				for(Connection conn:connections){
					for(final ScanTask task:this.taskList){
						task.visit(now, conn);
					}
				}
			}
		}catch(Exception e){
			log.error("","exception on scan",e);
		}finally{
			NettySharedHolder.timer.newTimeout(this, Constants.TIME_DELAY, TimeUnit.SECONDS);
		}
	}

}