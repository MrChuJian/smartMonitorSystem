package com.fjy.smartMonitorSystem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.fjy.smartMonitorSystem.model.Location;

public interface LocationMapper extends BaseMapper {
	@Insert("INSERT INTO location (longitude, latitude, lonSign, laSign, createTime) VALUES (#{longitude}, #{latitude}, #{lonSign}, #{laSign}, #{createTime})")
	public int save(Location location);

	@Select("SELECT * FROM location order by  id  desc  limit   1")
	public Location getlast();
}
