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
		System.out.println("111");
		for (Socket socket : sockets) {
			System.out.println("000");
			try {
				socket.sendUrgentData(-1);
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				sockets.remove(socket);
				e.printStackTrace();
				if(sockets.size() <= 0) {
					return false;
				}
			}
			try {
				PrintWriter writer=new PrintWriter(socket.getOutputStream());
				writer.println(i);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	
}
