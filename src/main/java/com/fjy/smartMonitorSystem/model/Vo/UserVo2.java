package com.fjy.smartMonitorSystem.model.Vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.model.File;

public class UserVo2 implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String phone;
	private String sex;
	private String addr;
	private String avatar;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	

}
