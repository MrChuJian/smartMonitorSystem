package com.fjy.smartMonitorSystem.netty.handler;

import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.util.SocketUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by louyuting on 16/12/8.
 * 服务端处理IO
 */
public class SimpleChatServerHandler extends ChannelInboundHandlerAdapter {
	

    public static ChannelGroup chats = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static ChannelGroup videos = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 每当服务端收到新的客户端连接时,客户端的channel存入ChannelGroup列表中,并通知列表中其他客户端channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //获取连接的channel
        Channel incomming = ctx.channel();
        //通知所有已经连接到服务器的客户端，有一个新的通道加入
        SB sb = new SB<String>(31, incomming.remoteAddress().toString(), "");
        for(Channel channel:chats){
        	sb.setData("[SERVER]-"+incomming.remoteAddress()+"加入\n");
            channel.writeAndFlush(sb);
        }
        chats.add(ctx.channel());
    }

    /**
     *每当服务端断开客户端连接时,客户端的channel从ChannelGroup中移除,并通知列表中其他客户端channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //获取连接的channel
        Channel incomming = ctx.channel();
        SB sb = new SB<String>(1, incomming.remoteAddress().toString(), "");
        for(Channel channel:chats){
        	sb.setData("[SERVER]-"+incomming.remoteAddress()+"离开\n");
            channel.writeAndFlush(sb);
        }
        //从服务端的channelGroup中移除当前离开的客户端
        chats.remove(ctx.channel());
        videos.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel incomming = ctx.channel();
        //将收到的信息转发给全部的客户端channel
        SB entity = (SB)msg;
        // code=1 文本
        if(entity.getCode() == 1) {
        	for(Channel channel:chats){
                if(channel == incomming) {
                    channel.writeAndFlush(entity);
                }else{
                    channel.writeAndFlush(entity);
                }
            }
        }
        // code=2 照片
        if(entity.getCode() == 2) {
        	for(Channel channel:chats){
                if(channel == incomming) {
                    channel.writeAndFlush(entity);
                }else{
                    channel.writeAndFlush(entity);
                }
            }
        }
        // code=3 视频开始播放
        if(entity.getCode() == 3) {
        	if(!videos.contains(incomming)) {
        		videos.add(incomming);
        	}
        	entity = new SB<>();
        	entity.setCode(200);
        	entity.setMsg("开始播放");
        	incomming.writeAndFlush(entity);
        }
        // code=4 视频停止播放
        if(entity.getCode() == 4) {
        	if(videos.contains(incomming)) {
        		videos.remove(incomming);
        	}
        	entity = new SB<>();
        	entity.setCode(200);
        	entity.setMsg("暂停播放");
        	incomming.writeAndFlush(entity);
        }
        // code=21 控制硬件设备指令
        if(entity.getCode() == 21) {
        	boolean isSend = false;
        	isSend = SocketUtil.send((Integer)entity.getData());
        	if(isSend) {
        		entity = new SB<>();
            	entity.setCode(400);
            	entity.setMsg("发送成功");
            	incomming.writeAndFlush(entity);
        	} else {
        		entity = new SB<>();
            	entity.setCode(404);
            	entity.setMsg("硬件没有连接服务器");
            	incomming.writeAndFlush(entity);
        	}
        }
        
    }
    

    /**
     * 服务端监听到客户端活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //服务端接收到客户端上线通知
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress()+"在线");
    }

    /**
     * 服务端监听到客户端不活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //服务端接收到客户端掉线通知
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress()+"掉线");
    }

    /**
     * 当服务端的IO 抛出异常时被调用
     * @param ctx                                              
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress()+"异常");
        //异常出现就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}