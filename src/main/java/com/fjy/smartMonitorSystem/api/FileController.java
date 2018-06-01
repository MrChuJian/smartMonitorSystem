package com.fjy.smartMonitorSystem.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.service.FileService;
import com.fjy.smartMonitorSystem.util.FileUtil;
import com.fjy.smartMonitorSystem.util.MimeTypeUtil;
import com.fjy.smartMonitorSystem.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "文件控制器", tags = { "文件控制器" })
@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	private static final Log logger = LogFactory.getLog(FileController.class);

	@ApiOperation(value = "上传文件", notes = "上传文件")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Entity<List<String>>> upload(HttpServletRequest request,
			@RequestParam("upfile") MultipartFile[] myfiles) {
		List<String> fileNames = new LinkedList<>();
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
				// 获取文件.后的后缀名
				String extName = FilenameUtils.getExtension(fileOriginalName);
				if (StringUtil.isNull(extName)) {
					extName = "unknown";
				}
				// 生成新文件名
				Date date = new Date();
				// uuid的唯一性，避免同名覆盖文件
				String uuidId = StringUtil.getUUID(date, UUID.randomUUID());
				// 获取文件存储的路径
				String basePath = "/tmp/files";
				String folderPath = basePath;
				// 文件名称
				String uuidName = uuidId + "." + extName.toLowerCase();
				
				com.fjy.smartMonitorSystem.model.File file2 = new com.fjy.smartMonitorSystem.model.File();
				file2.setFileName(fileOriginalName);
				file2.setUuidName(uuidName);
				file2.setMimeType(myfile.getContentType());
				file2.setCreateTime(new Timestamp(System.currentTimeMillis()));
//				file2.getCreateTime().get
				String dateStr = file2.getCreateTime().toString();
		    	int i = dateStr.indexOf(":");
		    	String dateName = dateStr.substring(0, i).replaceAll(" ", "-");
		    	// 文件路径/tmp/files/upload/年-月-日-时
		    	String filePath = folderPath + "/upload/" + extName + File.separator + dateName + File.separator;
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
					//进行文件中的内容以二进制形式存到文件路径
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
				} catch (IOException e) {
					logger.error("上传文件过程中出错", e);
					// "上传文件过程中出错"
					return Entity.builder(400).build(20, "上传文件过程中出错", null);
				}
				fileService.sendFile(filePath);
				// 保存至数据库
				fileService.saveFile(file2);
				// 添加多文件进表
				fileNames.add(fileOriginalName);
			}
			//返回给APP端或设备端
			return Entity.success(fileNames);
		} catch (Throwable t) {
			logger.error("上传文件失败", t);
			return Entity.builder(500).build(500, "上传文件失败", null);
		}
	}
	
	@ApiOperation(value = "下载文件", notes = "根据fileName下载文件")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> download(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "isdefault", required = false) String isdefault) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File file = null;
		com.fjy.smartMonitorSystem.model.File file2 = fileService.findFileByFileName(fileName);
		String uuidName = file2.getUuidName();
		try {
			if (uuidName.indexOf("/") >= 0) {
				return Entity.builder(400).build(10, "文件ID异常", null);
			}
			String fileName1 = uuidName;
			String dateStr = file2.getCreateTime().toString();
	    	int i = dateStr.indexOf(":");
	    	String dateName = dateStr.substring(0, i).replaceAll(" ", "-");
	    	String extName = FilenameUtils.getExtension(file2.getFileName());
			uuidName = "/tmp/files/upload/" + extName + File.separator + dateName + File.separator + uuidName;
			file = new File(uuidName);
			if (MimeTypeUtil.getContentType(uuidName) != null)
				response.setContentType(MimeTypeUtil.getContentType(uuidName));
			request.setCharacterEncoding("UTF-8");
			if (StringUtil.isNotNull(uuidName)) {
				response.setHeader("Content-disposition",
						"attachment; filename=" + new String(fileName1.getBytes("utf-8"), "ISO8859-1"));
			}
			response.setHeader("Content-Length", String.valueOf(file.length()));
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			return Entity.success(uuidName);
		} catch (Throwable t) {
			logger.error("找不到指定文件:" + uuidName);
			// 如果找不到对应的logo就把默认的logo添加上去
			try {
				uuidName = "/tmp/files" + File.separator + File.separator
						+ "upload" + File.separator + "prj_default.png";
				file = new File(uuidName);
				if (MimeTypeUtil.getContentType(uuidName) != null)
					response.setContentType(MimeTypeUtil.getContentType(uuidName));
				request.setCharacterEncoding("UTF-8");
				if (StringUtil.isNotNull(uuidName)) {
					response.setHeader("Content-disposition",
							"attachment; filename=" + new String(uuidName.getBytes("utf-8"), "ISO8859-1"));
				}
				response.setHeader("Content-Length", String.valueOf(file.length()));
				bis = new BufferedInputStream(new FileInputStream(file));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				logger.info("返回的图片为:" + uuidName);
				return Entity.success(uuidName);
			} catch (Exception e) {

				return Entity.builder(500).build(500, "查找文件失败", null);
			}
		} finally {
			try {
				bis.close();
			} catch (Exception e2) {
			}
			try {
				bos.close();
			} catch (Exception e2) {
			}
		}
	}
	
	@ApiOperation(value = "下载文件", notes = "根据fileName下载文件")
	@RequestMapping(value="/lastimage", method = RequestMethod.GET)
	public ResponseEntity<Entity<String>> lastImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "isdefault", required = false) String isdefault) {
		try {
			return Entity.success("seccess");
		} catch (Exception e) {
			
		} finally {
			return Entity.success("seccess");
		}
	}
	
	public boolean deleteDir() {
		com.fjy.smartMonitorSystem.model.File file =  fileService.findLastImage();
		String dateStr = file.getCreateTime().toString();
		int index = dateStr.indexOf(":");;
		String DateDir = dateStr.substring(0, index).replaceAll(" ", "-");
		String bashDir = "/tmp/files/upload/jpeg/";
		File fileDir = new File(bashDir);
		if ((!fileDir.exists()) || (!fileDir.isDirectory())) {
            System.out.println("目录" + bashDir + "不存在！");
            return false;
        }
		File[] files = fileDir.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				String DirName = file2.getName();
				if(DirName != DateDir) {
					int i2 = DirName.lastIndexOf('-');
			    	String bb = DirName.substring(0, i2) + " " + DirName.substring(i2 + 1) + ":%";
			    	System.out.println(bb);
					fileService.deleteLikeCreateTime(bb);
					FileUtil.deleteDirectory(bashDir + DirName);
				}
            }
		}
		return true;
	}
}
