package com.fjy.smartMonitorSystem.Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.fjy.smartMonitorSystem.util.SocketUtil;

public class SocketAcceptThread extends Thread {

	ServerSocket server = null;
	Socket socket = null;
	
	@Override
	public void run() {
		server = SocketUtil.server;
		while(true) {
			socket = null;
			if(server != null) {
				try {
					socket = server.accept();
					SocketUtil.sockets.add(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				SocketUtil.start();
				server = SocketUtil.server;
			}
		}
	}
}
