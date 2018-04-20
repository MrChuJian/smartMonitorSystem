package com.fjy.smartMonitorSystem.api;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.model.Vo.UserVo;
import com.fjy.smartMonitorSystem.service.UserService;
import com.fjy.smartMonitorSystem.util.CaptchaUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户控制器", tags = { "用户控制器" })
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	Map<String, String> tokens = new HashMap<>();
	
	@ApiOperation(value = "注册", notes = "注册")
	@RequestMapping(value="/logup", method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> logup(HttpServletRequest request,
			@RequestParam(required = true) String phone,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String code){
		if(code != request.getSession().getAttribute("code")) {
			Entity.failure(38, "验证码错误");
		}
		request.getSession().removeAttribute("code");
		boolean exist = userService.existPhone(phone);
		if(exist == true) {
			Entity.failure(38, "该手机已注册");
		}
		User user = new User();
		user.setPhone(phone);
		user.setPassword(password);
		userService.logup(user);
		return Entity.success("注册成功");
	}
	
	@ApiOperation(value = "登陆", notes = "登陆")
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> login(HttpServletRequest request,
			@RequestParam(required = true) String phone,
			@RequestParam(required = true) String password,
			@RequestParam(required = true) String code){
		if(code != request.getSession().getAttribute("code")) {
			Entity.failure(38, "验证码错误");
		}
		boolean exist = userService.existPhone(phone);
		if(exist == false) {
			Entity.failure(38, "该手机末注册");
		}
		boolean validate = userService.validatePhoneAndPassword(phone, password);
		if(validate == false) {
			Entity.failure(38, "账号或密码错误");
		}
		request.getSession().removeAttribute("code");
		String token = phone + password;
		tokens.put(phone, token);
		return Entity.success(token);
	}
	
	@ApiOperation(value = "登出", notes = "登出")
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> logout(HttpServletRequest request,
			@RequestParam(required = true) String phone){
		tokens.remove(phone);
		return Entity.success("logout");
	}
	
	@ApiOperation(value = "查询手机是否已注册", notes = "查询手机是否已注册")
	@RequestMapping(value="/existPhone", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> existPhone(
			@RequestParam(required = true) String phone){
		boolean exist = userService.existPhone(phone);
		if(exist == true) {
			Entity.failure(38, "该手机已注册");
		}
		return Entity.success("该手机末注册");
	}
	
	@ApiOperation(value = "获得验证码", notes = "获得验证码")
	@RequestMapping(value="/code", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> getcode(HttpServletRequest request, HttpServletResponse response){
		CaptchaUtil captcha = new CaptchaUtil();
		BufferedImage bi = captcha.createImage();
		try {
			
			ImageIO.write(bi, "JPEG", response.getOutputStream());
			request.getSession().setAttribute("code", captcha.getText());
		} catch (Exception e) {
			Entity.failure(38, "不知道为什么报错");
		}
		return Entity.success(captcha.getText());
	}
	
	@ApiOperation(value = "校验验证码", notes = "校验验证码")
	@RequestMapping(value="/validateCode", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> validateCode(HttpServletRequest request,
			@RequestParam(required = true) String code){
		if(code != request.getSession().getAttribute("code")) {
			Entity.failure(38, "验证码错误");
		}
		return Entity.success("验证码正确");
	}
	
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<Entity<UserVo>> getUser(HttpServletRequest request,
			@RequestParam(required = true) String phone){
		UserVo userVo = new UserVo();
		return Entity.success(userVo);
	}
	
}
