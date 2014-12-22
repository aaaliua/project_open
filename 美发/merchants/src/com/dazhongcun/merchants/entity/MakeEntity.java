package com.dazhongcun.merchants.entity;

public class MakeEntity {

	public static final String JSON_MAP = "data";

	public static final int ITEM = 0X111;
	public static final int LOADMORE = 0X112;
	public static final int NODATA = 0X113;
	/**
	 * id:预约id 
	 * userid： 用户id 
	 * username:用户名字 
	 * outpatientType:预约类型 
	 * mobile：用户的手机号码
	 * stylistid:发型师id 
	 * stylistName：发型师名字 
	 * outpatientTime：预约时间 12:00--13:00
	 * outpatientDate：预约日期 2014年11月12日 createDate：下单时间
	 */
	public static final String MAKE_ID = "id";
	public static final String MAKE_CREATDATE = "createdate";
	public static final String MAKE_MOBILE = "mobile";
	public static final String MAKE_OUTPATIENTDATE = "outpatientdate";
	public static final String MAKE_OUTPATIENTTIME = "outpatienttime";
	public static final String MAKE_OUTPATIENTTYPE = "outpatienttype";
	public static final String MAKE_STYLISTNAME = "stylistname";
	public static final String MAKE_STYLISTID = "stylistid";
	public static final String MAKE_USERNAME = "username";
	public static final String MAKE_USERID = "userid";
	public static final String MAKE_STATUSNAME = "statusname";

	private String id;
	private String createDate;
	private String mobile;
	private String outdate;
	private String outTime;
	private String outType;
	private String styListName;
	private String styListId;
	private String UserName;
	private String userID;
	private String statusName;
	
	private int loadtype;
	
	
	
	public int getLoadtype() {
		return loadtype;
	}
	public void setLoadtype(int loadtype) {
		this.loadtype = loadtype;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOutdate() {
		return outdate;
	}
	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getStyListName() {
		return styListName;
	}
	public void setStyListName(String styListName) {
		this.styListName = styListName;
	}
	public String getStyListId() {
		return styListId;
	}
	public void setStyListId(String styListId) {
		this.styListId = styListId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	
	
}
