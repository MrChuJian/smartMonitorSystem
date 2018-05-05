package com.fjy.smartMonitorSystem.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.model.Test;
import com.fjy.smartMonitorSystem.netty.init.SimpleChatClientInitializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by louyuting on 16/12/8. 启动服务端
 */
public class SimpleChatClient {
	private final int port;
	private final String host;

	public SimpleChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			// 是一个启动NIO服务的辅助启动类
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new SimpleChatClientInitializer());

			Channel channel = bootstrap.connect(host, port).sync().channel();

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			Entity entity;
			while (true) {
				entity = null;
				entity = new Entity<String>(1, "ahahaha", in.readLine());
				channel.writeAndFlush(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new SimpleChatClient("120.77.34.35", 8088).run();
	}

}
