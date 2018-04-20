package com.fjy.smartMonitorSystem.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.fjy.smartMonitorSystem.model.User;

@Repository
public interface UserMapper extends BaseMapper{

	@Select("select * from user where phone = #{phone}")
	User findUserByPhone(String phone);

	@Insert("INSERT INTO user (phone, password) " + "VALUES (#{phone}, #{password})")
	void save(User user);
	
	@Select("select * from user where phone = #{phone} and password = #{password}")
	User findUserByPhoneAndPassword(String phone, String password);

}
