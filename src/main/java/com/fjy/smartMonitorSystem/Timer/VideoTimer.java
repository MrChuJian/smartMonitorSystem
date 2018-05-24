package com.fjy.smartMonitorSystem.Timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.netty.handler.SimpleChatServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

public class VideoTimer extends TimerTask {
	
	private static Integer i = 0;
	private static String[] images = {"/images/sha.jpeg", "/images/bi.jpeg"};
	private Log logger = LogFactory.getLog(VideoTimer.class);

	@Override
	public void run() {
		ChannelGroup videos = SimpleChatServerHandler.videos;
		String com_path = VideoTimer.class.getClassLoader().getResource("./").getPath();
		if(videos != null && videos.size() > 0) {
			for (Channel cannel : videos) {
				SB<byte[]> sb = new SB<>();
				sb.setCode(5);
				sb.setMsg("");
				String filepath = com_path + images[i];
				i = (i + 1) % 2;
				File file = new File(filepath);
				InputStream is = null;
				try {
					is = new FileInputStream(file);
					sb.setData(IOUtils.toByteArray(is));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				cannel.writeAndFlush(sb);
			}
		}
	}
}
