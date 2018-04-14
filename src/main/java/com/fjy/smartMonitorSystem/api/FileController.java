package com.fjy.smartMonitorSystem.api;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.util.StringUtil;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/file")
public class FileController {
	
	private static final Log logger = LogFactory.getLog(FileController.class);

	@ApiOperation(value = "上传文件", notes = "上传文件")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> upload(HttpServletRequest request,
			@RequestParam("upfile") MultipartFile[] myfiles) {
		System.out.println(1);
		String fileName = "";
		try {
			for (MultipartFile myfile : myfiles) {
				if (myfile.isEmpty()) {
					return Entity.builder(400).build(10, "未上传文件", null);
				}
				long filesizeByte = myfile.getSize();
				String fileOriginalName = myfile.getOriginalFilename();
				logger.debug("文件长度: " + filesizeByte);
				logger.debug("文件类型: " + myfile.getContentType());
				logger.debug("文件名称: " + myfile.getName());
				logger.debug("文件原名: " + fileOriginalName);
				String extName = FilenameUtils.getExtension(fileOriginalName);
				if (StringUtil.isNull(extName)) {
					extName = "unknown";
				}
				// 生成新文件名
				Date date = new Date();
				String fileId = StringUtil.getUUID(date, UUID.randomUUID());
				// 获取文件存储的路径
				String basePath = "/tep/files";
				String folderPath = basePath;
				// 文件名称
				fileName = fileId + "." + extName.toLowerCase();
				String filePath = folderPath + File.separator  + File.separator + "upload";
				File f = new File(filePath);
				if (!f.exists()) {
					f.mkdirs();
				}
				filePath = filePath + File.separator + fileName;

				// 生成新文件名
				logger.info("文件存储的位置为" + filePath);
				File file = new File(filePath);
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
				try {
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
				} catch (IOException e) {
					logger.error("上传文件过程中出错", e);
					// "上传文件过程中出错"
					return Entity.builder(400).build(20, "上传文件过程中出错", null);
				}
			}
			return Entity.success(fileName);
		} catch (Throwable t) {
			logger.error("上传文件失败", t);
			return Entity.builder(500).build(500, "上传文件失败", null);
		}
	}
}
