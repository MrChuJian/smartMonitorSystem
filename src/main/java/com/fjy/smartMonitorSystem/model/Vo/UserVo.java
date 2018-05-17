package com.fjy.smartMonitorSystem.model.Vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class UserVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private MultipartFile avatar;
	private String avatarUrl;
	private String username;
	private String password;
	private String phone;
	private String sex;
	private String addr;
	private String code;
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public MultipartFile getAvatar() {
		return avatar;
	}
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
