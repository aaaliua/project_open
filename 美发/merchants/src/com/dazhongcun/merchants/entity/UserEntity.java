package com.dazhongcun.merchants.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "UserEntity")
public class UserEntity {

	public static final String JSON_MAP = "data";					//基础节点
		//时间 
	
	
	//用户bean
	public static final String ID = "u_id";     			//本地id
	public static final String JSON_ID = "id";				//服务器ID
	public static final String JSON_ADDRESS = "address";
	public static final String JSON_DESCRIPTION = "description";   
	public static final String JSON_EMAIL = "email";
	public static final String JSON_GENDER = "gender";			//性别
	public static final String JSON_IMGPATH = "imgpath";
	public static final String JSON_MOBILE = "mobile";
	public static final String JSON_NAME = "name";
	public static final String JSON_STAGENAME = "stagename";     //艺名
	public static final String JSON_TELEPHONE = "telephone";
	public static final String JSON_TOKENID = "tokenid";       //用户的tokenid
	
	public static final String JSON_PRAISE = "praise";  //点赞
	public static final String JSON_SERVICEFRACTION = "servicefraction";  //服务评分 
	public static final String JSON_WORKS = "works";  //作品集 
	public static final String JSON_SHOPNAME = "shopname";  //作品
	public static final String JSON_POSITIONNAME = "positionname";  //职位
	
	public static final String JSON_SPECIALTY = "specialty";  //特点
	public static final String JSON_HONOR = "honor";  //荣誉
	
	public static final String JSON_STORE_ID = "sid";  //门店ID

	
	
	/** 用户本地id  主键 */
	@DatabaseField(generatedId = true,useGetSet=true,columnName=ID)
	private long id;
	
	@DatabaseField(useGetSet=true,columnName=JSON_ID)
	private long u_id;
	
	@DatabaseField(useGetSet=true,columnName=JSON_ADDRESS)
	private String address;
	
	@DatabaseField(useGetSet=true,columnName=JSON_DESCRIPTION)
	private String desc;
	
	@DatabaseField(useGetSet=true,columnName=JSON_EMAIL)
	private String email;
	
	@DatabaseField(useGetSet=true,columnName=JSON_GENDER)
	private String gender;
	
	@DatabaseField(useGetSet=true,columnName=JSON_IMGPATH)
	private String imagePath;
	
	@DatabaseField(useGetSet=true,columnName=JSON_MOBILE)
	private String Moble;
	
	@DatabaseField(useGetSet=true,columnName=JSON_NAME)
	private String name;
	
	@DatabaseField(useGetSet=true,columnName=JSON_STAGENAME)
	private String stageName;
	
	@DatabaseField(useGetSet=true,columnName=JSON_TELEPHONE)
	private String telePhone;
	
	@DatabaseField(useGetSet=true,columnName=JSON_TOKENID)
	private String tokenId;

	@DatabaseField(useGetSet=true,columnName=JSON_PRAISE)
	private String praise;
	
	@DatabaseField(useGetSet=true,columnName=JSON_SERVICEFRACTION)
	private String servicefraction;
	
	@DatabaseField(useGetSet=true,columnName=JSON_WORKS)
	private String works;
	
	@DatabaseField(useGetSet=true,columnName=JSON_SHOPNAME)
	private String shopname;
	
	@DatabaseField(useGetSet=true,columnName=JSON_POSITIONNAME)
	private String positionname;
	
	@DatabaseField(useGetSet=true,columnName=JSON_SPECIALTY)
	private String specialty;
	
	@DatabaseField(useGetSet=true,columnName=JSON_HONOR)
	private String honor;
	
	@DatabaseField(useGetSet=true,columnName=JSON_STORE_ID)
	private String storeid;
	
	
	
	
	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getPositionname() {
		return positionname;
	}

	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}

	public String getServicefraction() {
		return servicefraction;
	}

	public void setServicefraction(String servicefraction) {
		this.servicefraction = servicefraction;
	}

	public String getWorks() {
		return works;
	}

	public void setWorks(String works) {
		this.works = works;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getU_id() {
		return u_id;
	}

	public void setU_id(long u_id) {
		this.u_id = u_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMoble() {
		return Moble;
	}

	public void setMoble(String moble) {
		Moble = moble;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
	
}
