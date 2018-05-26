package com.fjy.smartMonitorSystem.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.dao.UserMapper;
import com.fjy.smartMonitorSystem.model.User;
import com.fjy.smartMonitorSystem.model.Vo.UserVo;
import com.fjy.smartMonitorSystem.service.UserService;
import com.fjy.smartMonitorSystem.util.StringUtil;

@Service
public class UserServiceImpl implements UserService {

	private static Log logger = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	UserMapper userMapper;

	@Override
	public boolean existPhone(String phone) {
		User user = userMapper.findUserByPhone(phone);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void logup(UserVo user, MultipartFile avatar) throws Exception {
		if (avatar != null) {
			String fileOriginalName = avatar.getOriginalFilename();
			String extName = FilenameUtils.getExtension(fileOriginalName);
			if (StringUtil.isNull(extName)) {
				extName = "unknown";
			}
			// 生成新文件名
			Date date = new Date();
			String uuidId = StringUtil.getUUID(date, UUID.randomUUID());
			// 获取文件存储的路径
			String basePath = "/tmp/files";
			String folderPath = basePath + "/upload/avatar/";
			// 文件名称
			String uuidName = uuidId + "." + extName.toLowerCase();
			String filePath = folderPath;
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			filePath = filePath + uuidName;
			// 生成新文件名
			logger.info("文件存储的位置为" + filePath);
			File file = new File(filePath);
			// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
			try {
				FileUtils.copyInputStreamToFile(avatar.getInputStream(), file);
			} catch (IOException e) {
				logger.error("上传文件过程中出错", e);
				// "上传文件过程中出错"
				throw new Exception("上传文件过程中出错");
			}
			user.setAvatarUrl("http://120.77.34.35:3838/shabi/user/avatar/" + uuidName);
		}
		userMapper.save(user);
	}

	@Override
	public boolean validatePhoneAndPassword(User user) {
		User user1 = userMapper.findUserByPhoneAndPassword(user);
		if (user1 == null) {
			return false;
		}
		return true;
	}

	@Override
	public byte[] getAvatar(String fileName) throws Exception {
		String basePath = "/tmp/files";
		String folderPath = basePath + "/upload/avatar/";
		String filePath = folderPath + fileName;
		// 文件名称
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件不存在");
		}
		// 生成新文件名
		logger.info("文件存储的位置为" + filePath);
		// IOUtils.read
		byte[] avatar = FileUtils.readFileToByteArray(file);
		return avatar;
	}

	@Override
	public UserVo getByPhone(String phone) {
		UserVo user = userMapper.getByPhone(phone);
		return user;
	}

	@Override
	public List<User> getFriends(String phone) {
		List<User> users = userMapper.getFriendsByPhone(phone);
		return users;
	}
}
