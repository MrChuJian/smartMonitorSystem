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
	public String findUuidNameByFileName(String fileName) {
		List<File> files = fileMapper.findByFileName(fileName);
		return files.get(files.size()-1).getUuidName();
	}

	@Override
	public String saveFile(File file) {
		fileMapper.saveFile(file);
		return "ojbk";
	}
}
