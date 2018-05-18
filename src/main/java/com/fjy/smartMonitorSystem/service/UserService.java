package com.fjy.smartMonitorSystem.service;

import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.model.Vo.UserVo;

public interface UserService{

	boolean existPhone(String phone);

	void logup(UserVo user, MultipartFile avatar) throws Exception;

	boolean validatePhoneAndPassword(User user);

	byte[] getAvatar(String fileName) throws Exception;

	UserVo getByPhone(String phone);


}
