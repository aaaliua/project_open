package com.dazhongcun.merchants.user;

import roboguice.inject.InjectView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.dazhongcun.application.BaseApplication;
import com.dazhongcun.baseactivity.BaseActivity;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.activity.About;
import com.dazhongcun.merchants.activity.EditInfo;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.utils.PreferencesUtils;
import com.dazhongcun.merchants.utils.UpdateManager;
import com.dazhongcun.widget.ToggleButton;
import com.dazhongcun.widget.ToggleButton.OnToggleChanged;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SettingActivity extends BaseActivity implements OnClickListener{

	public static final String PRE_TOASTER = "toaster";
	
	@InjectView(R.id.edit_info)
	View edit;
	
	@InjectView(R.id.backbtn)
	View backbtn;
	@InjectView(R.id.updateLayout)
	View updateLayout;
	
	@InjectView(R.id.about)
	View about;
	
	@InjectView(R.id.logout)
	View logout;
	
	@InjectView(R.id.toast)
	ToggleButton toast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		edit.setOnClickListener(this);
		backbtn.setOnClickListener(this);
		logout.setOnClickListener(this);
		updateLayout.setOnClickListener(this);
		about.setOnClickListener(this);
		
		if(true == PreferencesUtils.getBooleanPreference(this, AppApplication.PREFERENCE_NAME, PRE_TOASTER, false)){
			toast.setToggleOn();
		}else{
			toast.setToggleOff();
		}
		
		
		
		toast.setOnToggleChanged(new OnToggleChanged() {
			
			@Override
			public void onToggle(boolean on) {
				PreferencesUtils.setBooleanPreference(SettingActivity.this, AppApplication.PREFERENCE_NAME, PRE_TOASTER, on);
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == edit.getId()){
			startActivity(new Intent(this,EditInfo.class));
		}
		if(v.getId() == backbtn.getId()){
			onBackPressed();
		}
		if(v.getId() == about.getId()){
			startActivity(new Intent(this,About.class));
		}
		if(v.getId() == updateLayout.getId()){
			new UpdateManager(this).checkUpdate();
		}
		if(v.getId() == logout.getId()){
			new MaterialDialog.Builder(this)
			.title("退出当前账号")
			.content(
					"确认退出?")
			.theme(Theme.LIGHT) // the default is light, so you don't
								// need this line
			.positiveText(R.string.accept) // the default is 'Accept'
			.positiveColor(getResources().getColor(R.color.merchants_color))
			.negativeText(R.string.decline) // leaving this line out
											// will remove the negative
											// button
			.callback(new MaterialDialog.Callback() {
				
				@Override
				public void onPositive(MaterialDialog dialog) {
//					Toast.makeText(SettingActivity.this, "点击完成",
//							Toast.LENGTH_SHORT).show();
					LoginActivity.setTokenID("");
					LoginActivity.setLoginKey(-1);
//					XGPushManager.clearLocalNotifications(SettingActivity.this);
					XGPushManager.unregisterPush(SettingActivity.this);
					Intent it = new Intent();
					it.setAction(BaseApplication.EXIT_APP);
					SettingActivity.this.sendBroadcast(it);
					SettingActivity.this.startActivity(new Intent(SettingActivity.this,LoginActivity.class));
					SettingActivity.this.finish();
					
				}

				@Override
				public void onNegative(MaterialDialog dialog) {
					dialog.dismiss();
				}
			})
			.build().show(); 
		}
	}
	
	
	
	public static final String TAG = "SettingActivity";
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
		MobclickAgent.onPause(this);
	}
}
