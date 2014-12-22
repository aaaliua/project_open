package com.linju.android_property.viewutils;

import com.linju.android_property2.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 自定义的各种dialog类  全部在这个类中写
 * @author Administrator
 *
 */
public class Progress_Dialog {

	
	/**
	 * 进度条的dialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createDialog(Context context,String msg){
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.progress_dialog_test, null);
		LinearLayout layout = (LinearLayout)v.findViewById(R.id.dialog_view);
		TextView text = (TextView)v.findViewById(R.id.tipTextView);
		text.setText(msg);
		
		Dialog loading = new Dialog(context,R.style.loadin_dialog);
		loading.setCancelable(false);
		loading.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		
		return loading;
	}
	
	/**
	 * 进度条的dialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createDialogPro(Context context,String msg){
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.progress_dialog, null);
		LinearLayout layout = (LinearLayout)v.findViewById(R.id.dialog_view);
		ImageView img = (ImageView)v.findViewById(R.id.img);
		TextView text = (TextView)v.findViewById(R.id.tipTextView);
		
		Animation jumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animaion);
		img.startAnimation(jumpAnimation);
		text.setText(msg);
		
		Dialog loading = new Dialog(context,R.style.loadin_dialog);
		loading.setCancelable(false);
		loading.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		
		return loading;
	}
}
