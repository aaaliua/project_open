package com.linju.android_property.activity;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.database.BaseAppDbHelper;
import com.linju.android_property.dialog.ActionSheetDialog;
import com.linju.android_property.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.linju.android_property.dialog.ActionSheetDialog.SheetItemColor;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property2.R;

public class InfoActivity extends BaseActivity implements OnClickListener{

	public static final String TAG = "infoactivity:tag";
	
	@InjectView(R.id.activity_back)
	TextView back;					//返回按钮
	@InjectView(R.id.edit)	
	Button edit;					//编辑
	
	@InjectView(R.id.subject_loginusername)
	EditText loginusername;
	@InjectView(R.id.subject_username)
	EditText username;
	@InjectView(R.id.subject_address)
	EditText address;
	@InjectView(R.id.subject_tel)
	EditText tel;
	@InjectView(R.id.subject_email)
	EditText email;
	@InjectView(R.id.subject_position)
	EditText position;
	@InjectView(R.id.flot_but)
	Button exit;
	private boolean flag;
	private Login_Bean bean;
	private BaseAppDbHelper<Login_Bean> dbHelper = new BaseAppDbHelper<Login_Bean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info_activity);
		back.setOnClickListener(this);
		exit.setOnClickListener(this);
		bean = dbHelper.queryObjForEq(Login_Bean.class, Login_Bean.USER_ID, LoginActivity.getLoginKey());
		init();
	}
	private void init(){
		loginusername.setText(bean.getLogin_name() == null||"null".equals(bean.getLogin_name())?"":bean.getLogin_name());
		username.setText(bean.getName()== null ||"null".equals(bean.getName())?"":bean.getName());
		tel.setText(bean.getTel()== null||"null".equals(bean.getTel())?"":bean.getTel());
		email.setText(bean.getEmail()== null||"null".equals(bean.getEmail())?"":bean.getEmail());
		position.setText(bean.getDepartment()== null||"null".equals(bean.getDepartment())?"":bean.getDepartment());
		address.setText(bean.getSubdistrict_address_name() == null || "null".equals(bean.getSubdistrict_address_name())?"":bean.getSubdistrict_address_name());
		changestate();
	}
	
	private void changestate(){
			loginusername.setEnabled(false);
			username.setEnabled(false);
			tel.setEnabled(false);
			email.setEnabled(false);
			position.setEnabled(false);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_back:
			onBackPressed();
			break;
		case R.id.edit:
			changestate();
			break;
		case R.id.flot_but:
			new ActionSheetDialog(this)
			.builder()
			.setTitle("注销账户？")
			.setCancelable(false)
			.setCanceledOnTouchOutside(false)
			.addSheetItem("注销", SheetItemColor.Red,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {

						}
					}).show();
			break;
		}
	}
	
	
	
	
	
}
