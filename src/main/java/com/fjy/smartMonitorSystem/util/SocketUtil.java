package com.fjy.smartMonitorSystem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketUtil {

	public static ServerSocket server = null;
	public static List<Socket> sockets = new LinkedList<>();
	private static Log logger = LogFactory.getLog(SocketUtil.class);
	private static Integer count = 0;
	
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
				socket.setSoTimeout(1000);
				PrintWriter writer=new PrintWriter(socket.getOutputStream());
				writer.println(i);
				writer.flush();
				logger.info("向硬件发送" + i);
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String rl = br.readLine();
				logger.info("硬件响应:" + rl);
			} catch (SocketTimeoutException e) {
				if(count < 3) {
					count++;
					logger.info("没有收到硬件反馈，第" + count + "次重新发送");
					return send(i);
				} else {
					count = 0;
					logger.info("重新发送三次失败，发送失败");
					return false;
				}
				
			}
			catch (IOException e) {
				logger.info("硬件断开连接");
				sockets.remove(socket);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
