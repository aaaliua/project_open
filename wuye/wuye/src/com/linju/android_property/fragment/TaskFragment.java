package com.linju.android_property.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.viewutils.LetterImageView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;


/**
 * 任务管理的fragmetn
 * @author LT
 *
 */
public class TaskFragment extends BaseFragment{

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		back.setVisibility(View.GONE);
		editOrAdd.setVisibility(View.GONE);
		title.setText(getActivity().getResources().getString(R.string.task_title));
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_task, null);
	}

	
}
