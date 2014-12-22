package com.linju.android_property.cache;



import java.io.File;
import java.io.IOException;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.utils.FileUtils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

//缓存类
public class ConfigCache {
    private static final String TAG = ConfigCache.class.getName();

//    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 7200000;  //2 hour
//    public static final int CONFIG_CACHE_WIFI_TIMEOUT    = 1800000;   //30 minute
    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000;  //1 hour
    public static final int CONFIG_CACHE_WIFI_TIMEOUT    = 300000;   //5 minute
    
    public static final int LONG_CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000*24*7;  //7 day
    public static final int LONG_CONFIG_CACHE_WIFI_TIMEOUT    = 300000*12*24;   //1 day
    

    public static String getUrlCache(String url,Activity context) {
        if (url == null) {
            return null;
        }

        String result = null;
        File file = new File(AppApplication.mSdcardCache + "/" + StringUtils.replaceUrlWithPlus(url));
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            //1. in case the system time is incorrect (the time is turn back long ago)
            //2. when the network is invalid, you can only read the cache
            if (NetworkUtils.getNetworkState(context) != NetworkUtils.NETWORN_NONE && expiredTime < 0) {
                return null;
            }
            if(NetworkUtils.getNetworkState(context) == NetworkUtils.NETWORN_WIFI && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (NetworkUtils.getNetworkState(context) == NetworkUtils.NETWORN_MOBILE && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    public static String getLongUrlCache(String url,Activity context){
        if (url == null) {
            return null;
        }

        String result = null;
        File file = new File(AppApplication.mSdcardCache + "/" + StringUtils.replaceUrlWithPlus(url));
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            //1. in case the system time is incorrect (the time is turn back long ago)
            //2. when the network is invalid, you can only read the cache
            if (NetworkUtils.getNetworkState(context) != NetworkUtils.NETWORN_NONE && expiredTime < 0) {
                return null;
            }
            if(NetworkUtils.getNetworkState(context) == NetworkUtils.NETWORN_WIFI && expiredTime > LONG_CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (NetworkUtils.getNetworkState(context) == NetworkUtils.NETWORN_MOBILE && expiredTime > LONG_CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void setUrlCache(String data, String url) {
        if (AppApplication.mSdcardCache == null) {
            return;
        }
        File dir = new File(AppApplication.mSdcardCache); 
        if (!dir.exists() && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dir.mkdirs();
        }
        File file = new File(AppApplication.mSdcardCache + "/" + StringUtils.replaceUrlWithPlus(url));
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }
    
    /**
     * 清除缓存
     */
    public static boolean wipeCache(){
        if (AppApplication.mSdcardCache == null) {
            return true;
        }
//        File dir = new File(BaseApplication.mSdcardDataDir); 
//        if (dir.exists() && Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//        	boolean isDel = FileUtils.DeleteFolder(dir.getAbsolutePath());
//            return isDel;
//        }
        
        boolean isDel  = false;
        if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	//清除图片缓存
        	File dir2 = new File(AppApplication.mSdcardImageCache);
        	if(dir2.exists()){
        		isDel = FileUtils.DeleteFolder(dir2.getAbsolutePath());
        	}
        	//清除路劲缓存
        	File dir1 = new File(AppApplication.mSdcardCache);
        	if(dir1.exists()){
        		isDel = FileUtils.DeleteFolder(dir1.getAbsolutePath());
        	}
        	return isDel;
        }
        return false;
    }
}
