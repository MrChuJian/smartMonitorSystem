package com.fjy.smartMonitorSystem.netty.handler;


import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.util.LogUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by louyuting on 16/12/8.
 * 客户端处理IO,只需要将读到的信息打印出来就OK了
 */
public class SimpleChatClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每当从服务端读到客户端写入信息时,将信息转发给其他客户端的Channel.
     * @param ctx
     * @param msg
     * @throws Exception
     */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		new Thread() {
			public void run() {
				SB entity = new SB(1, "shabi", "channelActive:bbbb");
				ctx.writeAndFlush(entity);
			};
		}.start();
		
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		LogUtil.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }
}