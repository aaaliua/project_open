package com.linju.android_property.user;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

public class ResetPasswordActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.more)
	ImageView info;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_setting);
		back.setText(getString(R.string.info_title));
		back.setOnClickListener(this);
		title.setText(getString(R.string.user_setting));
	}


	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}
	}

	
}
