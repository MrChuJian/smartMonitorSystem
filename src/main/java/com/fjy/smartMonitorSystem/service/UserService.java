package com.fjy.smartMonitorSystem.service;

import com.fjy.smartMonitorSystem.model.User;

public interface UserService {

	boolean existPhone(String phone);

	void logup(User user);

	boolean validatePhoneAndPassword(String phone, String password);

}
