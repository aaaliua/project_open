package com.linju.android_property.entity;

import java.io.Serializable;

public class Property_Fee_Bean implements Serializable{

	private 
	String id   
	, building_name					//楼号
	, house_number					//门牌号
	, name							//业主名字
	, tel							//电话
	, parking_tate_numbers   		//车位号
	,car_numbers					//车牌号
	, money_property				//物业费
	, money_parking_tate;			//停车费

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

	public String getHouse_number() {
		return house_number;
	}

	public void setHouse_number(String house_number) {
		this.house_number = house_number;
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

	public String getParking_tate_numbers() {
		return parking_tate_numbers;
	}

	public void setParking_tate_numbers(String parking_tate_numbers) {
		this.parking_tate_numbers = parking_tate_numbers;
	}

	public String getCar_numbers() {
		return car_numbers;
	}

	public void setCar_numbers(String car_numbers) {
		this.car_numbers = car_numbers;
	}

	public String getMoney_property() {
		return money_property;
	}

	public void setMoney_property(String money_property) {
		this.money_property = money_property;
	}

	public String getMoney_parking_tate() {
		return money_parking_tate;
	}

	public void setMoney_parking_tate(String money_parking_tate) {
		this.money_parking_tate = money_parking_tate;
	}

	public Property_Fee_Bean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Property_Fee_Bean(String id, String building_name,
			String house_number, String name, String tel,
			String parking_tate_numbers, String car_numbers,
			String money_property, String money_parking_tate) {
		super();
		this.id = id;
		this.building_name = building_name;
		this.house_number = house_number;
		this.name = name;
		this.tel = tel;
		this.parking_tate_numbers = parking_tate_numbers;
		this.car_numbers = car_numbers;
		this.money_property = money_property;
		this.money_parking_tate = money_parking_tate;
	}

	@Override
	public String toString() {
		return "Property_Fee_Bean [id=" + id + ", building_name="
				+ building_name + ", house_number=" + house_number + ", name="
				+ name + ", tel=" + tel + ", parking_tate_numbers="
				+ parking_tate_numbers + ", car_numbers=" + car_numbers
				+ ", money_property=" + money_property
				+ ", money_parking_tate=" + money_parking_tate + "]";
	}
	
}
