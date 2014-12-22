package com.linju.android_property.base;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property2.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import roboguice.activity.RoboActivity;

public class BaseActivity extends RoboActivity{

	//广播  处理接收到广播后关闭activityh
	protected BroadcastReceiver broadcast = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcast);
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppApplication.EXIT_APP);
		this.registerReceiver(broadcast, filter);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	
	
	
}
