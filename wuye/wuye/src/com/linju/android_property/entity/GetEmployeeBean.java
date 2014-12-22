package com.linju.android_property.entity;

import java.io.Serializable;

public class GetEmployeeBean implements Serializable{
	public static final String ID = "id";
	public static final String NAME = "name";
	String id;
	String name;
	@Override
	public String toString() {
		return "GetEmployeeBean [id=" + id + ", name=" + name + "]";
	}
	public GetEmployeeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GetEmployeeBean(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
