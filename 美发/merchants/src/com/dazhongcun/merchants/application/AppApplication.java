package com.dazhongcun.merchants.application;

import java.io.File;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;

import com.dazhongcun.application.BaseApplication;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.utils.BaseUtils;
import com.dazhongcun.utils.PinyinUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class AppApplication extends BaseApplication {

	public static final String DB_NAME = "linjuwuye.db";
	public static AsyncHttpClient mClient;
	public static Context mContext;
	public static final String EXIT_APP = "exitActivity:app"; //处理activity结束的broadcast的action
	@Override
	public void onCreate() {
		super.onCreate();
		
		
		//异常报告
//		ACRA.init(this);
//		ErrorReporter.getInstance().removeAllReportSenders();
//		ErrorReporter.getInstance().setReportSender(new ErrorSend(getApplicationContext()));
		//清除图片缓存
//		getImageLoader().clearDiscCache();
//		getImageLoader().clearMemoryCache();
		
		
	}

	@Override
	public void initDB() {
		DATABASE_NAME = DB_NAME;
	}

	@Override
	public void initEnv() {
		//用pinyin4j来获取对应的应用名
		if ("zh".equals(BaseUtils.getDefaultLanguage())) {
			mAppName = PinyinUtils.chineneToSpell(getResources().getString(
					R.string.app_name0));
		} else {
			mAppName = PinyinUtils.chineneToSpell(getResources().getString(
					R.string.app_name0));
		}

		// PREFERENCE_NAME是APP的名称
		PREFERENCE_NAME = mAppName;
		// 判断是否有SD卡再执行创建文件夹的操作
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			
			//创建应用缓存路径
			File dataDir = new File(Environment.getExternalStorageDirectory()
					.getPath()
					+ File.separator
					+ mAppName
					+ File.separator
					+ "config" + File.separator);
			if (dataDir.exists()) {
				mSdcardCache = dataDir.getAbsolutePath();
			} else {
				if (dataDir.mkdirs()) {
					mSdcardCache = dataDir.getAbsolutePath();
				}
			}

			//创建APP应用下载路径
			mAppDownloads = File.separator + mAppName + File.separator
					+ "download";
			File f = new File(Environment.getExternalStorageDirectory()
					.getPath() + mAppDownloads);
			if (!f.exists()) {
				f.mkdirs();
			}
			
			//图片的缓存路径
			File imageCache = new File(dataDir.toString() + File.separator +"imageCache" + File.separator);
			if (imageCache.exists()) {
				mSdcardImageCache = imageCache.getAbsolutePath();
			} else {
				if (imageCache.mkdirs()) {
					mSdcardImageCache = imageCache.getAbsolutePath();
				}
			}
			//拍照后的图片
			File imageCamera = new File(dataDir.toString() + File.separator +"imageCamera" + File.separator);
			if (imageCamera.exists()) {
				mSdcardImageCamera = imageCamera.getAbsolutePath();
			} else {
				if (imageCamera.mkdirs()) {
					mSdcardImageCamera = imageCamera.getAbsolutePath();
				}
			}
		}
	}

	@Override
	public void initImageLoad() {
		mContext = getApplicationContext();
		ImageLoaderConfiguration config = null;
		if(!TextUtils.isEmpty(mSdcardImageCache)){
			config = new ImageLoaderConfiguration
					.Builder(mContext)
					.memoryCacheExtraOptions(480, 800)// max width, max height，即保存的每个缓存文件的最大长宽 
//					.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个  
					.threadPoolSize(3)//线程池内加载的数量 
					.threadPriority(Thread.NORM_PRIORITY -2)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))// You can pass your own memory cache implementation/你可以通过自己的内存缓存实现  
					.memoryCacheSize(2 * 1024 * 1024)
					.discCacheSize(50 * 1024 * 1024)
					.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密  
					.tasksProcessingOrder(QueueProcessingType.LIFO)  
					.discCacheFileCount(100)  //缓存的文件数量  
					.discCache(new UnlimitedDiscCache(new File(mSdcardImageCache))) //自定义缓存路径  
					.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) 
					.imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000))// connectTimeout (5 s), readTimeout (30 s)超时时间  
					.writeDebugLogs() 
					.build();
		}else{
			config = new ImageLoaderConfiguration
					.Builder(mContext)
					.memoryCacheExtraOptions(480, 800)// max width, max height，即保存的每个缓存文件的最大长宽 
//					.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个  
					.threadPoolSize(3)//线程池内加载的数量 
					.threadPriority(Thread.NORM_PRIORITY -2)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))// You can pass your own memory cache implementation/你可以通过自己的内存缓存实现  
					.memoryCacheSize(2 * 1024 * 1024)
					.discCacheSize(50 * 1024 * 1024)
					.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密  
					.tasksProcessingOrder(QueueProcessingType.LIFO)  
					.discCacheFileCount(100)  //缓存的文件数量  
//					.discCache(new UnlimitedDiscCache(new File(mSdcardImageCache))) //自定义缓存路径  
					.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) 
					.imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000))// connectTimeout (5 s), readTimeout (30 s)超时时间  
					.writeDebugLogs() 
					.build();
		}
		
		
		ImageLoader.getInstance().init(config);
		//默认加载图片方法
		/**
		 * uri 图片地址
		 * 以下是加载类型
		 * String imageUri = "http://site.com/image.png"; // from Web  
		 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card  
	     * String imageUri = "content://media/external/audio/albumart/13"; // from content provider  
		 * String imageUri = "assets://image.png"; // from assets  
		 * String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)  
		 * imageAware ImageView组件
		 */
//		public void displayImage(String uri, ImageAware imageAware)
	}
	
	//获取Imageload的实例
	public static ImageLoader getImageLoader(){
		if(imageloader == null){
			imageloader = ImageLoader.getInstance();
		}
		return imageloader;
	}

	// 单例 获取异步数据请求的实例
	public static AsyncHttpClient getHttpClient() {
		if (mClient == null) {
			mClient = new AsyncHttpClient();
		}
		return mClient;
	}

}
