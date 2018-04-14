package com.fjy.smartMonitorSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.fjy.smartMonitorSystem.model.File;

@Repository
public interface FileMapper extends BaseMapper{
	
	@Select("select * from file where fileName = #{fileName}")
	List<File> findByFileName(String fileName);
	
	@Insert("INSERT INTO file (fileName,uuidName,mimeType,createTime) " + "VALUES (#{fileName}, #{uuidName}, #{mimeType} ,#{createTime})")
	int saveFile(File file);
}
