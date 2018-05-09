package com.fjy.smartMonitorSystem.netty.init;

import com.fjy.smartMonitorSystem.netty.handler.SimpleChatClientHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by louyuting on 16/12/8.
 * 用来增加多个的处理类到ChannelPipeline上:包括编码,解码,SimpleChatServerHandler
 */
public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("encoder", new ObjectEncoder());
        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,ClassResolvers.cacheDisabled(null)));
        pipeline.addLast("handler", new SimpleChatClientHandler());
    }
}
