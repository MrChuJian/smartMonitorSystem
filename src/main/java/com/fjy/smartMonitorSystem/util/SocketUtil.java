package com.fjy.smartMonitorSystem.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketUtil {

	public static ServerSocket server = null;
	public static List<Socket> sockets = new LinkedList<>();
	private static Log logger = LogFactory.getLog(SocketUtil.class);
	
	public static boolean start() {
		try {
			server = new ServerSocket(8089);
		} catch (IOException e) {
			logger.warn("启动监听8089端口失败!");
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static boolean send(int i) {
		if(sockets.size() <= 0) {
			return false;
		}
		for (Socket socket : sockets) {
			try {
				if(socket.isClosed()) {
					throw new Exception("");
				}
				PrintWriter writer=new PrintWriter(socket.getOutputStream());
				writer.println(i);
				writer.flush();
				logger.info("向硬件发送" + i);
			} catch (IOException e) {
				e.printStackTrace();
				sockets.remove(socket);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
