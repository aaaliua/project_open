package com.linju.android_property.entity;

import java.io.Serializable;

public class Building_info_Bean implements Serializable {

	public static final String params_id = "building_id";
	public static final String params_name = "name";
	public static final String params_desc = "description";
	public static final String params_klass = "house_klass_id";
	
	// id，name，description，house_count（房屋数）,house_klass, price}
	private String id;
	private String name;
	private String description;
	private String house_count;
	private String house_klass;
	private String house_klass_id;
	private String price;
	private String first_layer_price;
	private String device_money;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getHouse_klass_id() {
		return house_klass_id;
	}
	public void setHouse_klass_id(String house_klass_id) {
		this.house_klass_id = house_klass_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHouse_count() {
		return house_count;
	}
	public void setHouse_count(String house_count) {
		this.house_count = house_count;
	}
	public String getHouse_klass() {
		return house_klass;
	}
	public void setHouse_klass(String house_klass) {
		this.house_klass = house_klass;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFirst_layer_price() {
		return first_layer_price;
	}
	public void setFirst_layer_price(String first_layer_price) {
		this.first_layer_price = first_layer_price;
	}
	public String getDevice_money() {
		return device_money;
	}
	public void setDevice_money(String device_money) {
		this.device_money = device_money;
	}
	public Building_info_Bean(String id, String name, String description,
			String house_count, String house_klass, String price,
			String first_layer_price, String device_money) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.house_count = house_count;
		this.house_klass = house_klass;
		this.price = price;
		this.first_layer_price = first_layer_price;
		this.device_money = device_money;
	}
	public Building_info_Bean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Building_info_Bean [id=" + id + ", name=" + name
				+ ", description=" + description + ", house_count="
				+ house_count + ", house_klass=" + house_klass + ", price="
				+ price + ", first_layer_price=" + first_layer_price
				+ ", device_money=" + device_money + "]";
	}
}