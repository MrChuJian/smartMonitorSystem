package com.fjy.smartMonitorSystem.service;

import com.fjy.smartMonitorSystem.model.File;

public interface FileService {
	
	public String findUuidNameByFileName(String fileName);
	
	public String saveFile(File file);
}
