package com.fjy.smartMonitorSystem.model.Vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 接收数据
 * @author Tomato
 * 接收传感器数据后转换成数据库类型再进行保存
 */

public class SensorVo implements Serializable{
	private String type;
	private String unit;
	private Integer time;
	private Timestamp selectTime;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Timestamp getSelectTime() {
		return selectTime;
	}
	public void setSelectTime(Timestamp selectTime) {
		this.selectTime = selectTime;
	}
	@Override
	public String toString() {
		return "SensorVo [type=" + type + ", unit=" + unit + ", time=" + time + ", selectTime=" + selectTime + "]";
	}
	
	
}
