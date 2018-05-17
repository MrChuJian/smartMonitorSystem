package com.fjy.smartMonitorSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.fjy.smartMonitorSystem.model.Sensor;
import com.fjy.smartMonitorSystem.model.Vo.SensorVo;

public interface SensorMapper extends BaseMapper {

	@Insert("INSERT INTO sensor (type, data, createTime) VALUES (#{type}, #{data}, #{createTime})")
	public int save(Sensor sensor);

	@Select("SELECT * FROM sensor WHERE  createTime > #{selectTime}")
	public List<Sensor> getByTime(SensorVo sensorVo);

	@Select("SELECT * FROM sensor WHERE type = #{type} and createTime > #{selectTime}")
	public List<Sensor> getByTimeAndType(SensorVo sensorVo);

	@Select("SELECT * FROM sensor WHERE type = #{type}")
	public List<Sensor> getByType(String type);

	@Select("SELECT * FROM sensor")
	public List<Sensor> getAll();
}
