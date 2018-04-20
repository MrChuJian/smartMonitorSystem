package com.fjy.smartMonitorSystem.model.Vo;

import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.model.User;

public class UserVo extends User{

	private MultipartFile icon;

	public MultipartFile getIcon() {
		return icon;
	}

	public void setIcon(MultipartFile icon) {
		this.icon = icon;
	}

}
