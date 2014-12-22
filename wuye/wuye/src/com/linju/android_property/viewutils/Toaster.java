package com.linju.android_property.viewutils;

import com.linju.android_property.application.AppApplication;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 自定义Toast
 * 
 * @author gryps
 * 
 */
public class Toaster {

	public static Toast mToast;
	
	private static Handler mhandler = new Handler();
	private static Runnable r = new Runnable(){
		public void run() {
			mToast.cancel();
		};
	};
	
	/**
	 * 只显示一次的Toast   用String    默认时间SHORT
	 * @param resId
	 */
	public static void showOneToast(String str){    //只显示一次的Toast
		mhandler.removeCallbacks(r);
		if(null != mToast){
			mToast.setText(str);
		}else{
			mToast = Toast.makeText(AppApplication.getContext(), str, Toast.LENGTH_SHORT);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}
	
	/**
	 * 只显示一次的Toast   用id  默认时间SHORT
	 * @param resId
	 */
	public static void showOneToast(int resId){
		mhandler.removeCallbacks(r);
		if(null != mToast){
			mToast.setText(resId);
		}else{
			mToast = Toast.makeText(AppApplication.getContext(), resId, Toast.LENGTH_SHORT);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}
	
	/**
	 * 只显示一次的Toast   用id  默认时间SHORT
	 * @param resId
	 */
	public static void showOneToastCenter(int resId){
		mhandler.removeCallbacks(r);
		if(null != mToast){
			mToast.setText(resId);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		}else{
			mToast = Toast.makeText(AppApplication.getContext(), resId, Toast.LENGTH_SHORT);
			mToast.setGravity(Gravity.CENTER, 0, 0);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}
	
	/**
	 * 只显示一次的Toast   居中显示图片
	 * @param resId
	 */
	public static Toast mto;
	private static Handler ah = new Handler();
	private static Runnable rm = new Runnable(){
		public void run() {
			mto.cancel();
		};
	};
	public static void showOneImg(String str,int imgid){
		ah.removeCallbacks(rm);
		if(mto != null){
			mto.setText(str);
		}else{
			mto = Toast.makeText(AppApplication.getContext(), str, Toast.LENGTH_SHORT);
		mto.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) mto.getView();
		ImageView image = new ImageView(AppApplication.getContext());
		image.setImageResource(imgid);
		toastView.addView(image, 0);
		}
		ah.postDelayed(rm, 5000);
		mto.show();
	}
	//用ID 显示
	public static void showOneImg(int str,int imgid){
		ah.removeCallbacks(rm);
		if(mto != null){
			mto.setText(str);
		}else{
			mto = Toast.makeText(AppApplication.getContext(), str, Toast.LENGTH_SHORT);
			mto.setGravity(Gravity.CENTER, 0, 0);
			LinearLayout toastView = (LinearLayout) mto.getView();
			ImageView image = new ImageView(AppApplication.getContext());
			image.setImageResource(imgid);
			toastView.addView(image, 0);
		}
		ah.postDelayed(rm, 5000);
		mto.show();
	}
	
	/**
	 * 只显示一次的Toast   用String   自定义时间
	 * @param 
	 */
	public static void showOneToast(String str,int duration){    //只显示一次的Toast
		mhandler.removeCallbacks(r);
		if(null != mToast){
			mToast.setText(str);
		}else{
			mToast = Toast.makeText(AppApplication.getContext(), str, duration);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}
	/**
	 * 只显示一次的Toast   用id  自定义时间
	 * @param resId
	 */
	public static void showOneToast(int resId,int duration){
		mhandler.removeCallbacks(r);
		if(null != mToast){
			mToast.setText(resId);
		}else{
			mToast = Toast.makeText(AppApplication.getContext(), resId, duration);
		}
		mhandler.postDelayed(r, 5000);
		mToast.show();
	}
	
	
	
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showResIdToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 1.1默认效果
	 * 
	 * 显示字符串，默认短时间，居底。
	 */
	public static void showDefToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 1.2默认效果
	 * 
	 * 自定义存活时间 显示字符串，居底。
	 */
	public static void showDefToastByDuration(Context context, String text,
			int duration) {
		Toast.makeText(context, text, duration).show();
	}

	/**
	 * 1.3默认效果
	 * 
	 * 显示字符串资源，默认短时间，居底。
	 */
	public static void showDefToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 1.4默认效果
	 * 
	 * 自定义存活时间 显示字符串资源，居底。
	 */
	public static void showDefToast(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

	/**
	 * 2.1自定义显示位置效果并自定义存活时间
	 * 
	 * 显示字符串
	 */
	public static void showToastByGravity(Context context, String text,
			int gravity, int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	/**
	 * 2.2自定义显示位置效果并自定义存活时间
	 * 
	 * 显示字符串资源
	 */
	public static void showToastByGravity(Context context, int resId,
			int gravity, int duration) {
		Toast toast = Toast.makeText(context, resId, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	/**
	 * 3.1带图片效果并自定义存活时间
	 * 
	 * 居中显示字符串
	 */
	public static void showImgToast(Context context, String text, int imgResId,
			int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView image = new ImageView(context);
		image.setImageResource(imgResId);
		toastView.addView(image, 0);
		toast.show();
	}

	/**
	 * 3.2带图片效果并自定义存活时间
	 * 
	 * 居中显示字符串资源
	 */
	public static void showImgToast(Context context, int resId, int imgResId,
			int duration) {
		Toast toast = Toast.makeText(context, resId, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView image = new ImageView(context);
		image.setImageResource(imgResId);
		toastView.addView(image, 0);
		toast.show();
	}
}
