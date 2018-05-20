package com.fjy.smartMonitorSystem.Thread;

import com.fjy.smartMonitorSystem.netty.SimpleChatServer;

public class NettyServerThread extends Thread{
	@Override
	public void run() {
		try {
			new SimpleChatServer(8088).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
