package com.dazhongcun.merchants.user;

/**
 * 各种请求的参数
 * @author Administrator
 *
 */
public class BasicMember {

	public static final int size = 10;

	public static final String PARAMS_STATUS = "status";    //status: 预约状态 1  // 1 已经预约 2 未付款 3 已经付款 4 完成
	public static final String PARAMS_USERID = "userid";    //userid：用户id
	public static final String PARAMS_PAGENUMBER = "pagenumber";    //userid：用户id
	public static final String PARAMS_PAGESIZE = "pagesize";    //userid：用户id
	public static final String PARAMS_OID = "oid";    //userid：用户id
	public static final String PARAMS_TYPE = "type";    //userid：用户id
	
	//用户登陆
	public static final String LOING_NAME = "mobile";
	public static final String LOING_PASSWORD = "password";
	public static final String LOING_TOKENID = "tokenid";
	public static final String LOING_APPTOKENID = "apptokenid";
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
