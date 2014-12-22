package com.dazhongcun.merchants.utils;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.entity.NewVersionInfo;
import com.dazhongcun.merchants.service.UpgradeService;
import com.dazhongcun.views.Toaster;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * app版本更新管理类
 * 
 * @author gonglei
 * 
 */
public class UpdateManager {

	private Context mContext;
	/**检查版本更新的服务器URL*/
	private static final String URL = "http://192.168.2.200:8080/AppInterface/AppInterface?code=10020";
	private NewVersionInfo info;
	private ProgressDialog progressDialog;

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 检查服务端最新版本信息
	 */
	public void checkUpdate() {
		showProgressDialog();

		final String version = CommonUtils.getVersionName(mContext);
		RequestParams params = new RequestParams();
		params.add("type", 0 +"");
		HttpUtil.get(URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				// TODO Auto-generated method stub
				progressDialog.cancel();
				try {
					JSONObject object = new JSONObject(arg2);
					String return_value = object.getString("status");
					if (return_value.equals("0")) {// return_value为0是最新版本
						//首先判断是否是显示还是隐式调用
						info = new NewVersionInfo();
						JSONObject oc = object.getJSONObject("data");
						info.setVersion(oc.getString("version"));
						info.setUrl(oc.getString("downloadurl"));
						info.setDescription(oc.getString("content"));
						
						//判断本地版本号跟服务器版本号是否相一致
						if(Float.valueOf(version) < Float.valueOf(info.getVersion())){
							//本地版本号小于服务器版本号   显示对话框
							if (!info.getDescription().equals("")
									|| info.getDescription() != null) {
								showUpdateDialog(info.getDescription());
							} else {
								showUpdateDialog("fix bugs");
							}
						}else{
							//是正在开发的版本 或者不需要更新
							Toaster.showOneToast(R.string.check_latest);
						}
						
						
					}
					
						
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				progressDialog.cancel();
				Toast.makeText(mContext, "更新失败了", Toast.LENGTH_SHORT).show();
				Log.d("=========", "failfure = " + arg3);
			}
		});
	}

	/**
	 * 检查更新的progressDialog
	 */
	private void showProgressDialog() {
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage("检查更新中...");
		progressDialog.show();
	}

	/**
	 * 新版本更新提醒对话框
	 */
	private void showUpdateDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("发现新版本");
		builder.setMessage(msg);
		builder.setNegativeButton("稍后再说", null);
		builder.setPositiveButton("立即下载", positiveListener);
		builder.create();
		builder.show();
	}

	private OnClickListener positiveListener = new OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			// 显示下载进度对话框
			Intent intent = new Intent(
					mContext,
					UpgradeService.class);
			intent.putExtra(
					UpgradeService.DOWNLOAD_URL,
					info.getUrl());
			mContext.startService(intent);
		}
	};

}
