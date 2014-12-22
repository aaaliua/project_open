package com.linju.android_property.servicemanager;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.Property_Fee_Bean;
import com.linju.android_property.utils.StartActivityUtils;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

public class PropertyInfo extends BaseActivity implements OnClickListener{

	public static final String EXTRA_OBJ = "property:obj";
	
	@InjectExtra(value = EXTRA_OBJ,optional = true)
	Property_Fee_Bean bean;
//	@InjectView(R.id.app_loading)
//	View loadinglayout;
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;
	
	@InjectView(R.id.buildingNumber)
	TextView buildinNumber;
	@InjectView(R.id.menpai)
	TextView menpai;
	@InjectView(R.id.name)
	TextView name;
	@InjectView(R.id.phone)
	TextView phone;
	@InjectView(R.id.carProperty)
	TextView carProperty;
	@InjectView(R.id.carnumber)
	TextView carnumber;
	@InjectView(R.id.propertyMoney)
	TextView propertyMoney;
	@InjectView(R.id.carMoney)
	TextView carMoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_info);
		titlebar.setText(getString(R.string.lou_wuye_info_title));
		editOrAdd.setVisibility(View.VISIBLE);
		editOrAdd.setText(getString(R.string.lou_property_history));
		back.setOnClickListener(this);
		editOrAdd.setOnClickListener(this);
		
		initView();
	}

	private void initView(){
		if(bean != null){
			buildinNumber.setText(bean.getBuilding_name());
			menpai.setText(bean.getHouse_number());
			name.setText(bean.getName());
			phone.setText(bean.getTel());
			carProperty.setText(bean.getParking_tate_numbers());
			carnumber.setText(bean.getCar_numbers());
			propertyMoney.setText(bean.getMoney_property());
			carMoney.setText(bean.getMoney_parking_tate());
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}else if(v.getId() == editOrAdd.getId()){
			Intent it = new Intent(this,PropertyHostroyList.class);
			it.putExtra(EXTRA_OBJ, bean);
			StartActivityUtils.startActivity(this, it);
		}
	}
	
}
