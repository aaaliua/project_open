package com.dazhongcun.merchants.utils;

public class RequestUri {

	//http://staging.dazhongcun.com:8090/AppInterface/
	public static final String DEBUG_URL = "http://192.168.2.200:8080";
	public static final String PRODUCT_URL = "http://www.dazhongcun.com:8090";
	public static final String BASE_URL = PRODUCT_URL + "/AppInterface/AppInterface";
	
	public static final String CODE = "code";
	//用户登陆   code=10000&mobile=18019050008&password=123456
	public static final String LOGIN_CODE = "10000";
	//预约列表  userid=12&status=1
	public static final String MAKE_CODE = "10001";
	// 发型师id详细情况 code=10008&userid=123 
	public static final String INFO_CODE = "10008";
	//修改预约状态 ?code=10010&userid=111&oid=17&status=5   code：接口id 10009 oid：预约记录id status：状态 
	public static final String INFO_DONE = "10010";
	
}
