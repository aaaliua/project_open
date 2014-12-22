package com.linju.android_property.user;

import roboguice.inject.InjectView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.dialog.ActionSheetDialog;
import com.linju.android_property.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.linju.android_property.dialog.ActionSheetDialog.SheetItemColor;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

public class UserInfoSettingActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.more)
	ImageView info;
	
	@InjectView(R.id.setting_main)
	LinearLayout main;
	
	@InjectView(R.id.exit)
	Button exit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_setting);
		back.setText(getString(R.string.info_title));
		back.setOnClickListener(this);
		title.setText(getString(R.string.user_setting));
		exit.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}else if(v.getId() == exit.getId()){
//			PopupWindowPic pop = new PopupWindowPic(this,v);

			
			ActionSheetDialog dialog = new ActionSheetDialog(UserInfoSettingActivity.this)
			.builder()
			.setTitle("注销账户？")
			.setCancelable(false)
			.setCanceledOnTouchOutside(false)
			.addSheetItem("退出登录", SheetItemColor.Red,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {

						}
					});
			
			
			//dialog的显示隐藏动画
			dialog.setOnShowListener(new OnShowListener() {
				
				@Override
				public void onShow(DialogInterface dialog) {
					final Animation mScaleAnimation = AnimationUtils.loadAnimation(UserInfoSettingActivity.this,
							R.anim.setting_in);
					mScaleAnimation.setDuration(800);
					mScaleAnimation.setFillAfter(true);
					main.startAnimation(mScaleAnimation);
				}
			});
			dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					final Animation mScaleAnimation = AnimationUtils.loadAnimation(UserInfoSettingActivity.this,
							R.anim.setting_out);
					mScaleAnimation.setDuration(800);
					mScaleAnimation.setFillAfter(true);
					main.startAnimation(mScaleAnimation);					
				}
			});
			
			
			
			dialog.show();
		}
	}

	
	//弹出PopuP
	class PopupWindowPic extends PopupWindow{

		public PopupWindowPic(Context context , View v) {
			
			final Animation mScaleAnimation = AnimationUtils.loadAnimation(UserInfoSettingActivity.this,
					R.anim.setting_in);
			mScaleAnimation.setDuration(600);
			mScaleAnimation.setFillAfter(true);
			main.startAnimation(mScaleAnimation);
			
			View view = View.inflate(context, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
			LinearLayout llLayout = (LinearLayout)view.findViewById(R.id.ll_popup);
			llLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));
			//设置宽高
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);     
			setOutsideTouchable(true);  //外部触摸不隐藏
			setContentView(view);
			showAtLocation(v, Gravity.BOTTOM, 0, 0);
			update();
			
			
			Button camera = (Button)view.findViewById(R.id.item_popupwindows_camera);
			Button photo = (Button)view.findViewById(R.id.item_popupwindows_Photo);
			Button cancel = (Button)view.findViewById(R.id.item_popupwindows_cancel);
			camera.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			photo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					
					
					
				}
			});
		}
		
	}
}
