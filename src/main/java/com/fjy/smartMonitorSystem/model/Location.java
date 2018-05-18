package com.fjy.smartMonitorSystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Location implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String longitude; 	//经度
	private String latitude;	//纬度
	private String lonSign;		//经度符号
	private String laSign;		//纬度符号
	private Timestamp createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLonSign() {
		return lonSign;
	}
	public void setLonSign(String lonSign) {
		this.lonSign = lonSign;
	}
	public String getLaSign() {
		return laSign;
	}
	public void setLaSign(String laSign) {
		this.laSign = laSign;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
