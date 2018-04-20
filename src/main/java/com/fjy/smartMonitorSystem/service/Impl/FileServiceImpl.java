package com.fjy.smartMonitorSystem.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjy.smartMonitorSystem.dao.FileMapper;
import com.fjy.smartMonitorSystem.model.File;
import com.fjy.smartMonitorSystem.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileMapper fileMapper;

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
