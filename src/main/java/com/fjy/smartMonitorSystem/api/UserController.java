package com.fjy.smartMonitorSystem.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.model.Vo.UserVo;
import com.fjy.smartMonitorSystem.model.Vo.UserVo2;
import com.fjy.smartMonitorSystem.service.UserService;
import com.fjy.smartMonitorSystem.util.CaptchaUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户控制器", tags = { "用户控制器" })
@Controller
@RequestMapping("/user")
public class UserController {

	private static Log logger = LogFactory.getLog(UserController.class);
	@Autowired
	private UserService userService;
	public static Map<String, String> tokens = new HashMap<>();

	// @ApiOperation(value = "注册", notes = "测试专用验证码:123456 \n\r")
	// @RequestMapping(value="/logup", method = RequestMethod.POST)
	// public ResponseEntity<Entity<String>> logup(HttpServletRequest request,
	// @RequestParam(required = true) String phone,
	// @RequestParam(required = true) String password,
	// @RequestParam(required = true) String code){
	// if(!code.toLowerCase().equals(request.getSession().getAttribute(phone +
	// "-logup-code")) && !code.equals("123456")) {
	// return Entity.failure(38, "验证码错误");
	// }
	// boolean exist = userService.existPhone(phone);
	// if(exist == true) {
	// return Entity.failure(38, "该手机已注册");
	// }
	// request.getSession().removeAttribute(phone + "-logup-code");
	// User user = new User();
	// user.setPhone(phone);
	// user.setPassword(password);
	// userService.logup(user);
	// return Entity.success("注册成功");
	// }

	@ApiOperation(value = "注册", notes = "测试专用验证码:123456 \n\r")
	@RequestMapping(value = "/logup", method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> logup(HttpServletRequest request,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String phone,
			@RequestParam String addr,
			@RequestParam String code,
			@RequestParam String sex,
			@RequestParam MultipartFile avatar) {
		UserVo user = new UserVo();
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setAddr(addr);
		user.setCode(code);
		user.setSex(sex);
		if (!code.toLowerCase().equals(request.getSession().getAttribute(phone + "-logup-code"))
				&& !code.equals("123456")) {
			return Entity.failure(38, "验证码错误");
		}
		boolean exist = userService.existPhone(phone);
		if (exist == true) {
			return Entity.failure(38, "该手机已注册");
		}
		request.getSession().removeAttribute(phone + "-logup-code");
		try {
			userService.logup(user,avatar);
		} catch (Exception e) {
			e.printStackTrace();
			return Entity.builder(400).build(20, "上传文件过程中出错", null);
		}
		return Entity.success("注册成功");
	}

	@ApiOperation(value = "登陆", notes = "测试专用验证码:123456 \n\rcode:1 msg:验证码错误\n\rcode:2 msg:账号或密码错误\n\r")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> login(HttpServletRequest request, @RequestParam(required = true) String phone,
			@RequestParam(required = true) String password, @RequestParam(required = true) String code) {
		User user = new User();
		user.setPhone(phone);
		user.setPassword(password);
		System.out.println(code);
		if (!code.toLowerCase().equals(request.getSession().getAttribute(phone + "-login-code"))
				&& !code.equals("123456")) {
			return Entity.failure(1, "验证码错误");
		}
		boolean validate = userService.validatePhoneAndPassword(user);
		if (validate == false) {
			return Entity.failure(2, "账号或密码错误");
		}
		request.getSession().removeAttribute(phone + "-login-code");
		String token = tokens.get(phone);
		if (token == null || token.equals("")) {
			token = phone + password;
			tokens.put(phone, token);
		}
		return Entity.success(token);
	}

	@ApiOperation(value = "登出", notes = "登出")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> logout(HttpServletRequest request,
			@RequestParam(required = true) String phone) {
		tokens.remove(phone);
		return Entity.success("logout");
	}

	@ApiOperation(value = "查询手机是否已注册", notes = "code:1 msg:该手机末注册")
	@RequestMapping(value = "/exist/phone", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> existPhone(@RequestParam(required = true) String phone) {
		boolean exist = userService.existPhone(phone);
		if (exist == true) {
			return Entity.failure(38, "该手机已注册");
		}
		return Entity.success("该手机末注册");
	}

	@ApiOperation(value = "获得验证码", notes = "GET ip:3838/shabi/phone/type/code \n\rphone:手机号 \n\rtype:login,logup")
	@RequestMapping(value = "/{phone}/{type}/code", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> getcode(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String type, @PathVariable String phone) {
		CaptchaUtil captcha = new CaptchaUtil();
		captcha.createImage();
		// BufferedImage bi = captcha.createImage();
		try {

			// ImageIO.write(bi, "JPEG", response.getOutputStream());
			request.getSession().setAttribute(phone + "-" + type + "-code", captcha.getText().toLowerCase());
		} catch (Exception e) {
			return Entity.failure(38, "不知道为什么报错");
		}
		return Entity.success(captcha.getText());
	}

	@ApiOperation(value = "校验验证码", notes = "校验验证码")
	@RequestMapping(value = "/validate/{phone}/{type}/{code}", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> validateCode(HttpServletRequest request, @PathVariable String type,
			@PathVariable String phone, @PathVariable String code) {
		if (!code.toLowerCase().equals(request.getSession().getAttribute(phone + "-" + type + "-code"))) {
			return Entity.failure(38, "验证码错误");
		}
		return Entity.success("验证码正确");
	}
	
	@ApiOperation(value = "获得头像", notes = "用户信息中有avatarUrl")
	@RequestMapping(value = "/avatar/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getAvatar(HttpServletRequest request,HttpServletResponse response,
			@PathVariable String fileName) {
		ResponseEntity responseEntity = null;
		byte[] avatar = null;
		try {
			avatar = userService.getAvatar(fileName);
		} catch (Exception e) {
			logger.error(e);
			responseEntity = new ResponseEntity(new Entity<String>(1, "", e.getMessage()), HttpStatus.OK);
			return responseEntity;
		}
		try {
			response.getOutputStream().write(avatar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		responseEntity= new ResponseEntity<>(avatar, HttpStatus.OK);
		return responseEntity;
	}

	
	@ApiOperation(value = "获取用户信息", notes = "code:1 msg:该用户尚未登陆  \n\r  code:2 msg:token错误或过期,请重新登陆  \\n\\r  ")
	@RequestMapping(value = "/{phone}", method = RequestMethod.GET)
	public ResponseEntity<Entity<UserVo>> getUser(HttpServletRequest request,
			@PathVariable String phone,
			@RequestParam(required = true) String token) {
		String ttt = tokens.get(phone);
		if(ttt == null) {
			return Entity.failure(1, "该用户" + phone + "尚未登陆");
		}
		if(token.equals(ttt) || token.equals("123456")) {
			UserVo user = userService.getByPhone(phone);
			return Entity.success(user);
		} else {
			return Entity.failure(2, "token错误或过期,请重新登陆");
		}
	}
	
	@ApiOperation(value = "获取用户信息头像", notes = "获取用户信息头像 ")
	@RequestMapping(value = "/phone/{phone}/avatar", method = RequestMethod.GET)
	public ResponseEntity<Entity<byte[]>> getAvatar(HttpServletRequest request,
			@PathVariable String phone) {
		UserVo user = userService.getByPhone(phone);
		String avatarUrl = user.getAvatarUrl();
		byte[]  avatar = null;
		if(avatarUrl != null && !avatarUrl.equals("")) {
			int i = avatarUrl.lastIndexOf("/");
			try {
				avatar = userService.getAvatar(avatarUrl.substring(i));
			} catch (Exception e) {
				e.printStackTrace();
				return Entity.failure(2, "文件不存在");
			}
			return Entity.success(avatar);
		} else {
			return Entity.failure(1, "没有头像");
		}
	}
	
	@ApiOperation(value = "获取好友信息", notes = "获取好友信息 ")
	@RequestMapping(value = "/phone/{phone}/Friends", method = RequestMethod.GET)
	public ResponseEntity<Entity<List<UserVo2>>> getFriends(HttpServletRequest request,
			@PathVariable String phone) {
		List<User> users =  userService.getFriends(phone);
		List<UserVo2> users2 = new LinkedList<>();
		
		for (User user : users) {
			UserVo2 user2 = new UserVo2();
			BeanUtils.copyProperties(user, user2);
			users2.add(user2);
		}
		return Entity.success(users2);
	}

}
