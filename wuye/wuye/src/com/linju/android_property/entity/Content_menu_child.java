package com.linju.android_property.entity;

import java.io.Serializable;

public class Content_menu_child implements Serializable{

	public static final String CONTENT_TYPE_ID = "type_id";
	public static final String CONTENT_TYPE_NAME = "type_name";
	public static final String CONTENT_TYPE_TYPE = "type_type";
	
	private  String type_id;
	private  String type_name;
	private  String type_type;
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_type() {
		return type_type;
	}
	public void setType_type(String type_type) {
		this.type_type = type_type;
	}
	
	
	
}
