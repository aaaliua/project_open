package com.linju.android_property.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

	public static int getWidth(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		int mWidth = metric.widthPixels;
		return mWidth;
	}

	public static int getHeight(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int mHeight = metric.heightPixels;
		return mHeight;
	}

	public static int getHeight(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		int mHeight = metric.heightPixels;
		return mHeight;
	}

	public static float getDensity(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		return metric.density;
	}

	public static int getDensityDpi(Context context) {
		DisplayMetrics metric = new DisplayMetrics();
		metric = context.getResources().getDisplayMetrics();
		return metric.densityDpi;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
