package com.fjy.smartMonitorSystem.netty.handler;

import com.fjy.smartMonitorSystem.model.Entity;

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
	

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
        for(Channel channel:channels){
            channel.writeAndFlush("[SERVER]-"+incomming.remoteAddress()+"加入\n");
        }
        channels.add(ctx.channel());
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
        for(Channel channel:channels){
            channel.writeAndFlush("[SERVER]-"+incomming.remoteAddress()+"离开\n");
        }
        //从服务端的channelGroup中移除当前离开的客户端
        channels.remove(ctx.channel());
    }

    /**
     * 每当从服务端读到客户端写入信息时,将信息转发给其他客户端的Channel.
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String entity) throws Exception {
    	System.out.println(entity);
        Channel incomming = ctx.channel();
        //将收到的信息转发给全部的客户端channel
        for(Channel channel:channels){
            if(channel != incomming) {
//                channel.writeAndFlush("[" + incomming.remoteAddress() + "]" + entity.getData() + "\n");
                channel.writeAndFlush("[" + incomming.remoteAddress() + "]" + entity + "\n");
            }else{
//                channel.writeAndFlush("[You]"+entity.getData()+"\n");
                channel.writeAndFlush("[You]"+entity+"\n");
            }
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel incomming = ctx.channel();
        //将收到的信息转发给全部的客户端channel
        Entity entity = (Entity)msg;
        for(Channel channel:channels){
            if(channel == incomming) {
            	entity.setMsg("you");
                channel.writeAndFlush(entity);
            }else{
            	entity.setMsg(incomming.remoteAddress() + "");
                channel.writeAndFlush(entity);
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