package com.linju.android_property.message.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linju.android_property.base.BaseFragment;
import com.linju.android_property2.R;

public class InBoxFragment extends BaseFragment{

	@InjectView(R.id.hint)
	TextView hint;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		hint.setText("还没有任何信息，点击右上角开始");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.empty_inbox, null);
	}

	
	
	
}
