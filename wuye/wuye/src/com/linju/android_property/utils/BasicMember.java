package com.linju.android_property.utils;

/**
 * 各种请求的参数
 * @author Administrator
 *
 */
public class BasicMember {


	
	//分页参数
	public static final String OFFSET = "offset";
	public static final String LIMITE = "limit";
	public static final String STATUS = "status";
	public static final String POSITION_TYPE = "type";   //部门类型参数
	public static final String POSITION_DEPARTMENT_ID = "department_id";   //部门ID 用于维修指派任务
	public static final String position = "1";   //部门
	public static final String position_pos = "2";   //职位
	//用户登陆
	public static final String LOING_NAME = "loginname";
	public static final String LOING_PASSWORD = "password";
	private String loginname;
	private String loginpassword;
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpassword() {
		return loginpassword;
	}
	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}
	
}
