package com.fjy.smartMonitorSystem.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Sensor implements Serializable{

	private Integer id;
	private String type;
	private Double data;
	private Timestamp createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getData() {
		return data;
	}
	public void setData(Double data) {
		this.data = data;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Sensor [id=" + id + ", type=" + type + ", data=" + data + ", createTime=" + createTime + "]";
	}
	
	
}
