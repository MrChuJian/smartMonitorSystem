package com.fjy.smartMonitorSystem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjy.smartMonitorSystem.dao.UserMapper;
import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	
	@Override
	public boolean existPhone(String phone) {
		User user = userMapper.findUserByPhone(phone);
		if(user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void logup(User user) {
		userMapper.save(user);
	}

	@Override
	public boolean validatePhoneAndPassword(String phone, String password) {
		User user = userMapper.findUserByPhoneAndPassword(phone, password);
		if(user == null) {
			return false;
		}
		return true;
	}

}
