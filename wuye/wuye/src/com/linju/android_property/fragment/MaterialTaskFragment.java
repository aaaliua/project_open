package com.linju.android_property.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import at.markushi.ui.ActionView;
import at.markushi.ui.action.BackAction;
import at.markushi.ui.action.CloseAction;
import at.markushi.ui.action.DrawerAction;
import at.markushi.ui.action.PlusAction;

import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.viewutils.LetterImageView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;


/**
 * 任务管理的fragmetn
 * @author LT
 *
 */
public class MaterialTaskFragment extends BaseFragment{

	@InjectView(R.id.action)
	ActionView view;
	
	
	int i = 0;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(i == 0 ){
					
					view.setAction(new BackAction(),ActionView.ROTATE_COUNTER_CLOCKWISE);
					i++;
				}else if(i == 1){
					view.setAction(new DrawerAction(),ActionView.ROTATE_CLOCKWISE);
					i++;
				}else if(i == 2){
					view.setAction(new CloseAction(),ActionView.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);
					i++;
				}else if(i == 3){
					view.setAction(new PlusAction(),ActionView.ACCESSIBILITY_LIVE_REGION_POLITE);
					i++;
				}else if(i == 4){
					view.setAction(new BackAction(),ActionView.DRAWING_CACHE_QUALITY_AUTO);
					i = 0;
				}
				View vs = getActivity().getLayoutInflater().inflate(R.layout.view_alertdialog, null);
				
				PopupWindow window = new PopupWindow(vs,800,800);
				window.setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_dropdown_panel_material_theme));
				window.setAnimationStyle(R.style.AnimationPreview);
				window.setFocusable(true);
				window.update();
				window.showAsDropDown(view, (int)view.getX()+100, (int)view.getY()-100);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_task_material, null);
	}

	
}
