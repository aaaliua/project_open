package com.linju.android_property.activity;

import roboguice.inject.InjectView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.linju.android_property2.R;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.database.BaseAppDbHelper;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.PreferencesUtils;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.viewutils.OpenSansEditText;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPAutoCompleteTextView;
import com.linju.android_property.viewutils.WPTextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.sign_in_button)    
	WPTextView sigin;    					//登陆按钮
	@InjectView(R.id.sign_in_progress_bar)
	RelativeLayout progresslayout;   		//进度组件
	@InjectView(R.id.username)
	WPAutoCompleteTextView username;      	//用户名
	@InjectView(R.id.password)
	OpenSansEditText password;				//密码
	@InjectView(R.id.password_visibility)
	ImageView passwordvisibility;
	@InjectView(R.id.hints)
	WPTextView hint;   						//提示文本
	
	protected boolean mPasswordVisible;
	private BasicMember basicMember;
	private static final String[] cities=new String[]
	        {"661669@163.com"};
	
	private BaseAppDbHelper<Login_Bean> dbHelper = new BaseAppDbHelper<Login_Bean>();
	public static BaseAppDbHelper<Login_Bean> staticHelper = new BaseAppDbHelper<Login_Bean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		basicMember = new BasicMember();
		initPasswordVisibilityButton();
		
		
		sigin.setOnClickListener(this);
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				//s是输入框正在输的字符串，随着不断的输入，s的值也会不停地改变  
				 String str = s.toString();  
				 ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line,cities);
				 username.setAdapter(adapter);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				//请求登陆失败
				hint.setText(R.string.login_error_hint);
				username.setEnabled(true);
				password.setEnabled(true);
				progresslayout.setVisibility(View.GONE);
			}else if(msg.what == 2){
				//准备请求登陆
				username.setEnabled(false);
				password.setEnabled(false);
				hint.setText(R.string.default_login_hint);
			}else if(msg.what == 3){
				
				startActivity(new Intent(LoginActivity.this,MainTabActivity.class));
				progresslayout.setVisibility(View.GONE);
				LoginActivity.this.finish();
			}
			
		}
		
	};
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_in_button:
			if(isvalid()){
				if(progresslayout.getVisibility() == View.GONE){    		//处理用户登陆的业务逻辑 用户登陆完成 隐藏不确定进度条
					progresslayout.setVisibility(View.VISIBLE);
				}else{
					progresslayout.setVisibility(View.GONE);
				}
				handler.sendEmptyMessage(2);
				//开始HTTP请求
				httpLogin(v);
			}
			break;

		}
	}

	private void httpLogin(final View v){
		RequestParams params = new RequestParams();
		params.put(BasicMember.LOING_NAME, basicMember.getLoginname());
		params.put(BasicMember.LOING_PASSWORD, basicMember.getLoginpassword());
		//不确定的dialog
		AppApplication.getHttpClient().post(RequestURL.LOGIN_USER,params ,new AsyncHttpResponseHandler(){
			private boolean isSuc = false;
			@Override
			public void onStart() {
				//网络请求开始
				hint.setVisibility(View.VISIBLE);
				v.setEnabled(false);
			}

			@Override
			@Deprecated
			public void onSuccess(String content) {
//				hint.setText(content);
				parseUserJson(content);
				isSuc = true;
			}
			
			@Override
			@Deprecated
			public void onFailure(Throwable error) {
				v.setEnabled(true);
				isSuc = false;
				handler.sendEmptyMessage(1);
			}

			@Override
			public void onFinish() {
				if(!isSuc){
//					Toaster.showOneToast(R.string.login_error_hint);
					handler.sendEmptyMessage(1);
				}
				v.setEnabled(true);
			}

		});
	}
	
	//验证表单
	private boolean isvalid(){
		String account = username.getText().toString().trim();
		String pass = password.getText().toString().trim();
		if(TextUtils.isEmpty(account)){
			Toaster.showOneToast(R.string.sign_account_hint);
			username.requestFocus();
			return false;
		}else{
			basicMember.setLoginname(account.trim());
		}
		
		basicMember.setLoginpassword(pass);
		if(TextUtils.isEmpty(pass)){
			Toaster.showOneToast(R.string.login_password_hint);
			password.requestFocus();
			return false;
		}
		
		return true;
	}
	
	//点击显示密码的图片
	protected void initPasswordVisibilityButton() {
        if (passwordvisibility == null) {
            return;
        }
        passwordvisibility.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	changePassword();
            }
        });
    }
	//改变密码图片点击  明文或者密文
	private void changePassword(){
		 mPasswordVisible = !mPasswordVisible;
         if (mPasswordVisible) {
         	passwordvisibility.setImageResource(R.drawable.dashicon_eye_open);
         	passwordvisibility.setColorFilter(getResources().getColor(R.color.nux_eye_icon_color_open));
             password.setTransformationMethod(null);
         } else {
         	passwordvisibility.setImageResource(R.drawable.dashicon_eye_closed);
         	passwordvisibility.setColorFilter(getResources().getColor(R.color.nux_eye_icon_color_closed));
         	password.setTransformationMethod(PasswordTransformationMethod.getInstance());
         }
         password.setSelection(password.length());
	}
	//解析用户信息
	private void parseUserJson(String json){
		Login_Bean bean = ParseJson.get_loginJSON(json);
		if(bean != null && bean.getUser_id() != null){
			//保存或者更新用户信息到数据库  
			setLoginKey(bean.getUser_id());
			//查询数据库中是否有跟ID想等的数据
			Login_Bean dblogin = new Login_Bean();
			dblogin = dbHelper.queryObjForEq(Login_Bean.class, Login_Bean.USER_ID, bean.getUser_id());
			if(dblogin == null){
				//没有的话就创建对象 插入一条数据
				int i = dbHelper.create(bean);	
			}else{
				//如果有的话 吧数据库中的ID引用到请求后解析完成后的对象   
				bean.setId(dblogin.getId());
				//如果有localID就跟新数据库   否则插入数据库
				dbHelper.createOrUpdate(bean);
			}
			handler.sendEmptyMessage(3);
		}else{
			handler.sendEmptyMessage(1);
		}
	}
	
	//设置用户的key就是服务器上的ID 用sharedprefrence保存用户的ID
	public static void setLoginKey(String user_id){
		PreferencesUtils.setStringPreferences(AppApplication.getContext(), AppApplication.PREFERENCE_NAME, Login_Bean.USER_ID, user_id);
	}
	//获取用户的key
	public static String getLoginKey(){
		return PreferencesUtils.getStringPreference(AppApplication.getContext(), AppApplication.PREFERENCE_NAME, Login_Bean.USER_ID, "");
	}
	
	public static Login_Bean getUser(){
		Login_Bean dblogin = new Login_Bean();
		dblogin = staticHelper.queryObjForEq(Login_Bean.class, Login_Bean.USER_ID, getLoginKey());
		return dblogin;
	}
}
