package com.fjy.smartMonitorSystem.service;

import com.fjy.smartMonitorSystem.model.File;

public interface FileService {
	
	public File findFileByFileName(String fileName);
	
	public String saveFile(File file);

	public File findLastImage();

	public void deleteLikeCreateTime(String bb);
}
