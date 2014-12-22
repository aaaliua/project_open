package com.linju.android_property.entity;

import java.io.Serializable;

public class Property_fee_Histroy_Bean implements Serializable{
	
	public static final String HIS_ID ="estate_info_id";
	
	private 
	String id
	,data					//缴费日期
	,payment_type_name		//缴费类型
	,money					//费用
	,buy_type_name			//缴费方式   手机还是客户端
	,is_pay_off				//是否付清
	,created_at;			//最后更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPayment_type_name() {
		return payment_type_name;
	}

	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBuy_type_name() {
		return buy_type_name;
	}

	public void setBuy_type_name(String buy_type_name) {
		this.buy_type_name = buy_type_name;
	}

	public String getIs_pay_off() {
		return is_pay_off;
	}

	public void setIs_pay_off(String is_pay_off) {
		this.is_pay_off = is_pay_off;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public Property_fee_Histroy_Bean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Property_fee_Histroy_Bean(String id, String data,
			String payment_type_name, String money, String buy_type_name,
			String is_pay_off, String created_at) {
		super();
		this.id = id;
		this.data = data;
		this.payment_type_name = payment_type_name;
		this.money = money;
		this.buy_type_name = buy_type_name;
		this.is_pay_off = is_pay_off;
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Property_fee_Histroy_Bean [id=" + id + ", data=" + data
				+ ", payment_type_name=" + payment_type_name + ", money="
				+ money + ", buy_type_name=" + buy_type_name + ", is_pay_off="
				+ is_pay_off + ", created_at=" + created_at + "]";
	}
	
	

}
