package com.fjy.smartMonitorSystem.service.Impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjy.smartMonitorSystem.dao.FileMapper;
import com.fjy.smartMonitorSystem.model.File;
import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.netty.handler.SimpleChatServerHandler;
import com.fjy.smartMonitorSystem.service.FileService;

import io.netty.channel.group.ChannelGroup;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileMapper fileMapper;
	
	@Override
	public boolean sendFile(String fileName) {
		ChannelGroup videos = SimpleChatServerHandler.videos;
		System.out.println("准备发送" + fileName);
		if(videos != null && videos.size() > 0) {
			SB<byte[]> sb = new SB<>();
			sb.setCode(5);
			sb.setMsg("");
			java.io.File file = new java.io.File(fileName);
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				sb.setData(IOUtils.toByteArray(is));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			videos.writeAndFlush(sb);
			System.out.println("发送" + fileName);
			return true;
		}
		return false;
	}

	@Override
	public File findFileByFileName(String fileName) {
		List<File> files = fileMapper.findByFileName(fileName);
		return files.get(files.size()-1);
	}

	@Override
	public String saveFile(File file) {
		fileMapper.saveFile(file);
		return "ojbk";
	}

	@Override
	public File findLastImage() {
		return fileMapper.findLastImage();
	}

	@Override
	public void deleteLikeCreateTime(String createTime) {
		fileMapper.deleteLikeCreateTime(createTime);
	}
}
