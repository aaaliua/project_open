package com.linju.android_property.personmanager;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

/**
 * 部门管理的activity
 * @author Administrator
 *
 */
public class PositionManager extends BaseActivity implements OnClickListener{

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_lists_activity);
		title.setText(getResources().getString(R.string.bumen_title));
		editOrAdd.setText(getResources().getString(R.string.add));
		
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		}
	}

	
}
