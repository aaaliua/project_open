package com.linju.android_property.utils;

import com.linju.android_property2.R;

import android.app.Activity;
import android.content.Intent;


/**
 * 跳转intent类  自定义各种动画跳转效果
 * @author Administrator
 *
 */
public class StartActivityUtils {

	/**
	 * 左右的效果
	 * @param a
	 * @param it
	 */
	public static void startActivityForResult(Activity a,Intent it,int code){
		a.startActivityForResult(it,code);
		a.overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}
	
	/**
	 * 左右的效果
	 * @param a
	 * @param it
	 */
	public static void startActivity(Activity a,Intent it){
		a.startActivity(it);
		a.overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}
	/**
	 * 左右关闭效果
	 * @param a
	 */
	public static void finishActivity(Activity a){
		a.finish();
		a.overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

}
