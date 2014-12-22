package com.linju.android_property.user;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.database.BaseAppDbHelper;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.viewutils.CircleImageView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

public class UserInfoActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.back)
	View back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.more)
	ImageView info;
	
	@InjectView(R.id.user_img)
	CircleImageView userImg;  					 //用户头像
	@InjectView(R.id.comuntiyName)
	TextView comuntiyName;    					 //小区名称
	@InjectView(R.id.username)
	TextView userName;    						 //用户名
	@InjectView(R.id.position)
	TextView position;							 //职位
	@InjectView(R.id.phone)
	TextView phone;								 //手机号码
	@InjectView(R.id.setting)
	View settingView;							 //设置按钮
	
	private Login_Bean bean;
	private BaseAppDbHelper<Login_Bean> dbHelper = new BaseAppDbHelper<Login_Bean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		back.setOnClickListener(this);
		title.setText(getString(R.string.info_title));
		settingView.setOnClickListener(this);
		
		bean = dbHelper.queryObjForEq(Login_Bean.class, Login_Bean.USER_ID, LoginActivity.getLoginKey());
		init();
	}
	private void init(){
		userName.setText(bean.getLogin_name() == null||"null".equals(bean.getLogin_name())?"":bean.getLogin_name());
		phone.setText(bean.getTel()== null||"null".equals(bean.getTel())?"":bean.getTel());
		position.setText(bean.getDepartment()== null||"null".equals(bean.getDepartment())?"":bean.getDepartment());
		comuntiyName.setText(bean.getSubdistrict_address_name() == null || "null".equals(bean.getSubdistrict_address_name())?"":bean.getSubdistrict_address_name());
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}else if(v.getId() == settingView.getId()){
			startActivity(new Intent(this,UserInfoSettingActivity.class));
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
		}
	}

	
}
