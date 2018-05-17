package com.fjy.smartMonitorSystem.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.model.Vo.UserVo;

@Repository
public interface UserMapper extends BaseMapper{

	@Select("select * from user where phone = #{phone}")
	User findUserByPhone(String phone);

	@Insert("INSERT INTO user (username, phone, password, addr, sex, avatarUrl) " + "VALUES (#{username}, #{phone}, #{password}, #{addr}, #{sex}, #{avatarUrl})")
	void save(UserVo user);
	
	@Select("select * from user where phone = #{phone} and password = #{password}")
	User findUserByPhoneAndPassword(User user);

	@Select("select * from user where phone = #{phone}")
	UserVo getByPhone(String phone);

}
