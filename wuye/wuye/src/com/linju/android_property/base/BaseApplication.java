package com.linju.android_property.base;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 重写application类  一些初始化的操作在该类中做  整合RoboGuice ImageLoader 
 * @author LT
 * 未完成：异常信息邮件发送     推送等
 */
public abstract class BaseApplication extends Application {

	public static BaseApplication baseApplication;
	// 应用名
	public static String mAppName;
	// 应用下载文件存储路径："/" + mAppName + "/appdownload"
	public static String mAppDownloads;
	// 应用缓存数据路径：Environment.getExternalStorageDirectory().getPath() + "/" +
	// mAppName + "/config/
	public static String mSdcardCache;
	// 应用图片缓存数据路径：Environment.getExternalStorageDirectory().getPath() + "/" +
	// mAppName + "/config/ + ""imageCache/
	public static String mSdcardImageCache;
	//拍照后的图像文件夹
	public static String mSdcardImageCamera;
	// 当前应用版本号
	public static int mVersionCode;
	// 当前应用版本名称
	public static String mVersionName;
	// 语言 中文为zh，英文为en，日文为ko
	public static String language;
	// 应用preference名
	public static String PREFERENCE_NAME;
	// 数据库
	public static String DATABASE_NAME;
	// debuge
	public static boolean debuggable = true;
	// 加载网络图片库
	public static ImageLoader imageloader;
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 获取加载图片的实例
		// imageloader = imageloader.getInstance();
		baseApplication = this;
		

		initDB();
		initEnv();
		initLocalVersion();
		initImageLoad();
	}

	// 获取上下文
	public static Context getContext() {
		return baseApplication;
	}

	// 初始化本地版本信息
	public void initLocalVersion() {
		PackageInfo info;

		try {
			// 该方法获取的AndroidManifest.xml中的android:versionCode="1"
			// android:versionName="1.0"
			info = this.getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			mVersionCode = info.versionCode; // 版本code
			mVersionName = info.versionName; // 版本名
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 初始化数据库
	public abstract void initDB();

	// 初始化一些其他的操作 比如系统语言 文件夹的创建
	public abstract void initEnv();

	// 加载图片的一些配置 初始化在继承该类后再执行appapplication 实现在方法主要是设置自定义的缓存目录
	public abstract void initImageLoad();

}
