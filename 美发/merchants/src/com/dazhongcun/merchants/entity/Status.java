package com.dazhongcun.merchants.entity;


/**
 * 状态对象
 * @author LT
 *
 */
public class Status {

	
	public static final String JSON_STATUS = "status";				//状态码 非0的都失败
	public static final String JSON_MSG = "msg";					//失败信息
	public static final String JSON_CODE = "code";					//请求的接口id
	public static final String JSON_TIME = "time";			
	
	
	
	private String status;
	private String msg;
	private String code;
	private String time;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
