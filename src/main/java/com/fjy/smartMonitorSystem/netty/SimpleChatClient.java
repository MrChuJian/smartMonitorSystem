package com.fjy.smartMonitorSystem.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fjy.smartMonitorSystem.model.FileUploadFile;
import com.fjy.smartMonitorSystem.model.SB;
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
			FileUploadFile file = new FileUploadFile();
			file.setFileName("abc");
			file.setBytes("shabi".getBytes());
			SB sb = new SB<String>(1, "11", "sdfsf");
			channel.writeAndFlush(sb);
			while (true) {
				in.readLine();
				channel.writeAndFlush(sb);
			}
//			while(true) {
//				in.readLine();
//				entity = null;
//				entity = new Entity<Test>(1, "ahahaha", new Test(1, "bb", "aa"));
//				System.out.println(11);
//				channel.writeAndFlush(entity);
//				System.out.println(22);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	public static void main(String[] args) throws Exception {
		new SimpleChatClient("localhost", 8088).run();
	}
}
