package com.dazhongcun.merchants.activity;

import roboguice.inject.InjectView;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.dazhongcun.baseactivity.BaseActivity;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.database.BaseAppDbHelper;
import com.dazhongcun.merchants.entity.UserEntity;
import com.dazhongcun.merchants.user.LoginActivity;
import com.dazhongcun.merchants.utils.ImageOptions;
import com.dazhongcun.widget.WPTextView;

public class EditInfo extends BaseActivity implements OnClickListener{

	@InjectView(R.id.makeImage)
	ImageView img;
	@InjectView(R.id.backbtn)
	View backbtn;
	
	@InjectView(R.id.makeType)
	WPTextView type;
	@InjectView(R.id.makename)
	WPTextView name;
	@InjectView(R.id.makeposition)
	WPTextView makeposition;
	@InjectView(R.id.makesign)
	WPTextView makesign;
	
	
	private BaseAppDbHelper<UserEntity> dbHelper = new BaseAppDbHelper<UserEntity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_info);
		
		backbtn.setOnClickListener(this);
		
		UserEntity dblogin = new UserEntity();
		dblogin = dbHelper.queryObjForEq(UserEntity.class, UserEntity.JSON_ID, LoginActivity.getLoginKey());
		if(dblogin != null){
			AppApplication.getImageLoader().displayImage(dblogin.getImagePath(), img,ImageOptions.defaultOptions);
			
			
			type.setText("1".equals(dblogin.getGender())?"男":"女");
			name.setText(dblogin.getName());
			makesign.setText(dblogin.getDesc());
			makeposition.setText(dblogin.getPositionname());
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId() == backbtn.getId()){
			onBackPressed();
		}
	}
}
