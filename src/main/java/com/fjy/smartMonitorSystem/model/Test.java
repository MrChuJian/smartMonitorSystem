package com.fjy.smartMonitorSystem.model;

import java.io.Serializable;

public class Test implements Serializable{

	private int code;
	private String msg;
	private String data;
	public Test() {
	}
	public Test(int code, String msg, String data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	@Override
	public String toString() {
		return "Test [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
