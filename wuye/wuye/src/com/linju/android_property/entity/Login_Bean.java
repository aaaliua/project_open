package com.linju.android_property.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * user用户的实体bean   顺便初始化数据库
 * @author Administrator
 *
 */
@DatabaseTable(tableName = "login_bean")
public class Login_Bean {
	public static final String property = "property";  //管理员权限
	public static final String FROM = "from";  
	
	public static final String ID = "local_id";
	public static final String USER_ID = "user_id"; 											//用户ID
	public static final String USER_SUBDISTRICTADDRESSID = "subdistrict_address_id";			//小区ID
	public static final String USER_SUBDISTRICTADDRESSNAME = "subdistrict_address_name";		//小区名字
	public static final String USER_KLASS = "klass";										//角色
	public static final String USER_LOGINNAME = "login_name";								//用户名						
	public static final String USER_NAME = "name";											//姓名
	public static final String USER_TEL = "tel";											//电话
	public static final String USER_DEPARTMENT = "department";								//部门/职位
	public static final String USER_EMAIL = "email";										//邮件
	public static final String USER_POSITION_ID = "position_id";							//职位id
	/** 用户本地id */
	@DatabaseField(generatedId = true,useGetSet=true,columnName=ID)
	private long id;
	
	@DatabaseField(useGetSet=true,columnName=USER_ID)
	private String user_id;
	
	@DatabaseField(useGetSet=true,columnName=USER_KLASS)
	private String klass;
	
	@DatabaseField(useGetSet=true,columnName=USER_SUBDISTRICTADDRESSID)
	private String subdistrict_address_id;
	
	@DatabaseField(useGetSet=true,columnName=USER_SUBDISTRICTADDRESSNAME)
	private String subdistrict_address_name;
	
	@DatabaseField(useGetSet=true,columnName=USER_NAME)
	private String name;
	
	@DatabaseField(useGetSet=true,columnName=USER_TEL)
	private String tel;
	
	@DatabaseField(useGetSet=true,columnName=USER_DEPARTMENT)
	private String department;
	
	@DatabaseField(useGetSet=true,columnName=USER_EMAIL)
	private String email;
	
	@DatabaseField(useGetSet=true,columnName=USER_POSITION_ID)
	private String position_id;
	
	@DatabaseField(useGetSet=true,columnName=USER_LOGINNAME)
	private String login_name;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public String getSubdistrict_address_id() {
		return subdistrict_address_id;
	}
	public void setSubdistrict_address_id(String subdistrict_address_id) {
		this.subdistrict_address_id = subdistrict_address_id;
	}
	public String getSubdistrict_address_name() {
		return subdistrict_address_name;
	}
	public void setSubdistrict_address_name(String subdistrict_address_name) {
		this.subdistrict_address_name = subdistrict_address_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

}
