package com.dazhongcun.merchants.service;

import java.io.File;
import java.net.URLEncoder;

import com.dazhongcun.application.BaseApplication;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.database.BaseAppDbHelper;
import com.dazhongcun.merchants.entity.Downloads;
import com.dazhongcun.merchants.utils.FileUtils;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

@SuppressLint("NewApi")
public class UpgradeService extends Service implements Runnable {

	public static final String DOWNLOAD_URL = "download_url";

	private DownloadManager downloadManager;

	private Downloads downloads = new Downloads();

	private BaseAppDbHelper<Downloads> mDbHelper;

	private String mDownloadUrl;

	private long downloadId = -1;

	private File destDir = null;
	private File destFile = null;
	private String destFileName;

	private static final int DOWNLOAD_FAIL = -1;
	private static final int DOWNLOAD_SUCCESS = 0;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_SUCCESS:
				Toast.makeText(getApplicationContext(),
						R.string.app_upgrade_download_sucess, Toast.LENGTH_LONG)
						.show();
				install(destFile);
				break;
			case DOWNLOAD_FAIL:
				Toast.makeText(getApplicationContext(),
						R.string.app_upgrade_download_fail, Toast.LENGTH_LONG)
						.show();
				break;
			default:
				break;
			}
		}

	};
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			long reference = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (downloadId == reference) {
				queryDownloadStatus();
			}
		}
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		mDbHelper = new BaseAppDbHelper<Downloads>();

		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		registerReceiver(receiver, filter);
	}

	private void queryDownloadStatus() {
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(downloadId);
		Cursor c = downloadManager.query(query);
		if (c.moveToFirst()) {
			int status = c.getInt(c
					.getColumnIndex(DownloadManager.COLUMN_STATUS));
			switch (status) {
			case DownloadManager.STATUS_PAUSED:
				// Log.v("down", "STATUS_PAUSED");
			case DownloadManager.STATUS_PENDING:
				// Log.v("down", "STATUS_PENDING");
			case DownloadManager.STATUS_RUNNING:
				// 正在下载，不做任何事情
				// Log.v("down", "STATUS_RUNNING");
				break;
			case DownloadManager.STATUS_SUCCESSFUL:
				// 完成
				// Log.v("down", "下载完成");
                Message msg1 = mHandler.obtainMessage();
                msg1.what = DOWNLOAD_SUCCESS;
                mHandler.sendMessage(msg1);
				break;
			case DownloadManager.STATUS_FAILED:
				// 清除已下载的内容，重新下载
				// Log.v("down", "STATUS_FAILED");
				 downloadManager.remove(downloadId);
				 mDbHelper.remove(Downloads.class, Downloads.DOWNLOAD_ID, downloadId);
                 Message msg2 = mHandler.obtainMessage();
                 msg2.what = DOWNLOAD_FAIL;
                 mHandler.sendMessage(msg2);
				break;
			}
		}
		stopSelf();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if (intent != null) {
			parseIntent(intent);
		}
	}

	private void parseIntent(Intent intent) {
		final Bundle arguments = intent.getExtras();
		if (arguments != null) {
			if (arguments.containsKey(DOWNLOAD_URL)) {
				mDownloadUrl = arguments.getString(DOWNLOAD_URL);

				if (Environment.getExternalStorageState().equals(
						android.os.Environment.MEDIA_MOUNTED)) {
					destDir = new File(Environment
							.getExternalStorageDirectory().getPath()
							+ BaseApplication.mAppDownloads);
					if (destDir.exists()) {
						try {
							destFileName = mDownloadUrl.substring(mDownloadUrl
									.lastIndexOf("/") + 1).toString();
							
							if(destFile == null ||"".equals(destFile)){
								destFileName = BaseApplication.mAppName + ".apk";
							}
						} catch (Exception e) {
							// TODO: handle exception
							destFileName = BaseApplication.mAppName + ".apk";
						}
						File destFile = new File(destDir.getPath() + "/"
								+ destFileName);
						if (destFile.exists() && destFile.isFile()
								&& checkApkFile(destFile.getPath())) {
							install(destFile);
							stopSelf();
							return;
						}
					}else{
						if(destDir.mkdirs()){
							try {
								destFileName = mDownloadUrl.substring(mDownloadUrl
										.lastIndexOf("/") + 1).toString();
								
								if(destFile == null ||"".equals(destFile)){
									destFileName = BaseApplication.mAppName + ".apk";
								}
							} catch (Exception e) {
								// TODO: handle exception
								destFileName = BaseApplication.mAppName + ".apk";
							}
							File destFile = new File(destDir.getPath() + "/"
									+ destFileName);
							if (destFile.exists() && destFile.isFile()
									&& checkApkFile(destFile.getPath())) {
								install(destFile);
								stopSelf();
								return;
							}
						}
					}
				} else {
					return;
				}

				new Thread(this).start();
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		download();
	}

	@SuppressLint("NewApi")
	private void download() {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			if (destDir == null) {
				destDir = new File(Environment.getExternalStorageDirectory()
						.getPath() + BaseApplication.mAppDownloads);
			}
			if (destDir.exists() || destDir.mkdirs()) {

				destFile = new File(destDir.getPath() + "/" + destFileName);// URLEncoder.encode(mDownloadUrl)
				if (destFile.exists() && destFile.isFile()
						&& checkApkFile(destFile.getPath())) {
					install(destFile);
				} else {
					//
					downloads.setUrl(mDownloadUrl);
					DownloadManager.Request request = new DownloadManager.Request(
							Uri.parse(mDownloadUrl));
					/**
					 * 下载存储文件夹和文件名 设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置
					 * 如果sdcard可用，下载后的文件
					 * 在/mnt/sdcard/Android/data/packageName/files目录下面，
					 * 如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个 目录下面
					 */
					downloads.setDirType(BaseApplication.mAppDownloads);
					downloads.setSubPath(destFileName);
					//setDestinationInExternalPublicDir这里设置是已包含了/mnt/sdcard
					request.setDestinationInExternalPublicDir(
							BaseApplication.mAppDownloads, destFileName);
					// 设置下载中通知栏提示的标题
					final String title = destFileName;/*getResources().getString(
							R.string.app_name);*/
					downloads.setTitle(title);
					request.setTitle(title);
					// 设置下载中通知栏提示的介绍
					final String description = getResources().getString(
							R.string.app_upgrade_download_start);
					downloads.setDescription(description);
					request.setDescription(description);

					downloads
							.setVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
					downloads.setScanning(false);
					request.setShowRunningNotification(true);
					request.setVisibleInDownloadsUi(true);
					// 以下两项设置需android3.0以上
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						/**
						 * 表示下载进行中和下载完成的通知栏是否显示,默认只显示下载中通知。
						 * VISIBILITY_VISIBLE(0) This download is visible but
						 * only shows in the notifications while it's in
						 * progress. VISIBILITY_VISIBLE_NOTIFY_COMPLETED(1)
						 * 表示下载中和完成后显示通知栏提示。 VISIBILITY_HIDDEN(2)表示不显示任何通知栏提示，
						 * 这个需要在AndroidMainfest中添加权限android
						 * .permission.DOWNLOAD_WITHOUT_NOTIFICATION.
						 * VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION(3)This
						 * download shows in the notifications after completion
						 * ONLY
						 */
						request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						// 表示允许MediaScanner扫描到这个文件，默认不允许。
						if (downloads.getScanning()) {
							request.allowScanningByMediaScanner();
						}
					}
					/**
					 * 表示下载允许的网络类型，默认在任何网络下都允许下载。
					 * 有NETWORK_MOBILE(1)、NETWORK_WIFI
					 * (2)、NETWORK_BLUETOOTH三种及其组合可供选择。
					 * 如果只允许wifi下载，而当前网络为3g，则下载会等待。
					 * request.setAllowedOverRoaming(boolean
					 * allow)移动网络情况下是否允许漫游。
					 */
					downloads.setNetworkType(0);
					if (downloads.getNetworkType() != 0) {
						request.setAllowedNetworkTypes(downloads
								.getNetworkType());
					}
					/**
					 * 设置下载文件的mineType。
					 * 因为下载管理Ui中点击某个已下载完成文件及下载完成点击通知栏提示都会根据mimeType去打开文件，
					 * 所以我们可以利用这个属性
					 * 。比如上面设置了mimeType为application/com.trinea.download.file，
					 * 我们可以同时设置某个Activity的intent
					 * -filter为application/com.trinea.download.file，用于响应点击的打开文件。
					 */
					// 设置文件类型
					MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
					String mimeString = mimeTypeMap
							.getMimeTypeFromExtension(MimeTypeMap
									.getFileExtensionFromUrl(mDownloadUrl));
					request.setMimeType(mimeString);
					downloads.setMimeType(mimeString);
					/**
					 * 添加请求下载的网络链接的http头，比如User-Agent，gzip压缩等
					 */
					// if(!TextUtils.isEmpty(downloads.getHeader())){
					// request.addRequestHeader(Downloads.HEADER,
					// downloads.getHeader());
					// }
					// //TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
					downloadId = downloadManager.enqueue(request);
					downloads.setDownloadId(downloadId);
					mDbHelper.create(downloads);
				}
			}
		}
		
	}

	public boolean checkApkFile(String apkFilePath) {
		boolean result = false;
		try {
			PackageManager pManager = getPackageManager();
			PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath,
					PackageManager.GET_ACTIVITIES);
			if (pInfo == null) {
				result = false;
			} else {
				if(pInfo.versionCode>BaseApplication.mVersionCode){
					result = true;
				}else{
					boolean del = FileUtils.DeleteFolder(apkFilePath);
//					Logger.d("upgrade", del+ "-------------");
					result = false;
				}
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public void install(File apkFile) {
		Uri uri = Uri.fromFile(apkFile);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		startActivity(intent);
	}
	
    @Override
	public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
