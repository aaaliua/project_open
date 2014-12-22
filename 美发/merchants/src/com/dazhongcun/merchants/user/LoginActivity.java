package com.dazhongcun.merchants.user;

import roboguice.inject.InjectView;

import com.dazhongcun.baseactivity.BaseActivity;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.activity.MainTabActivity;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.database.BaseAppDbHelper;
import com.dazhongcun.merchants.entity.Status;
import com.dazhongcun.merchants.entity.UserEntity;
import com.dazhongcun.merchants.utils.ParseJson;
import com.dazhongcun.merchants.utils.RequestUri;
import com.dazhongcun.utils.PreferencesUtils;
import com.dazhongcun.views.ButtonRectangle;
import com.dazhongcun.views.CheckBox;
import com.dazhongcun.views.Toaster;
import com.dazhongcun.widget.OpenSansEditText;
import com.dazhongcun.widget.WPTextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;
import com.umeng.analytics.MobclickAgent;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener {

	@InjectView(R.id.sign_in_button)
	WPTextView sigin; // 登陆按钮
	@InjectView(R.id.sign_in_progress_bar)
	RelativeLayout progresslayout; // 进度组件

	@InjectView(R.id.username)
	OpenSansEditText username; // 用户名
	@InjectView(R.id.password)
	OpenSansEditText password; // 密码
	@InjectView(R.id.material_checkBox)
	CheckBox autoSign; // 自动登录按钮
	@InjectView(R.id.resetPassword)
	WPTextView resetPassword; // 忘记密码

	@InjectView(R.id.hints)
	WPTextView hints;

	private BasicMember basicMember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

//		createShut();
		// 开启logcat输出，方便debug，发布时请关闭
//		 XGPushConfig.enableDebug(this, true);
		// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
		// XGIOperateCallback)带callback版本
		// 如果需要绑定账号，请使用registerPush(getApplicationContext(),"account")版本
		// 具体可参考详细的开发指南
		// 传递的参数为ApplicationContext
		Context context = getApplicationContext();
		XGPushManager.registerPush(context, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.d("TPush", "注册成功，设备token为：" + data);
//				 Toaster.showOneToast(XGPushConfig.getToken(LoginActivity.this));
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
			}
		});
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);
		// Toaster.showOneToast(XGPushConfig.getToken(this));
		initnotifacationView();

		basicMember = new BasicMember();
		sigin.setOnClickListener(this);

		if (getTokenID() != null) {
			// 自动登陆
			if ("".equals(getTokenID())) {

			} else {

				autoLogin();
			}
		}
	}

	/**
	 * 创建桌面快捷方式
	 * 这样做可能并不友好。更好的做法是，第一次运行程序的时候，提示用户是否创建桌面快捷方式，让用户选择。以后再次运行就不再进行提示了。
	 */
	public void createShut() {
		// 创建添加快捷方式的Intent
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		String title = getResources().getString(R.string.app_name);
		// 加载快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				LoginActivity.this, R.drawable.ic_launcher);
		// 创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
		Intent myIntent = new Intent(LoginActivity.this,
				LoginActivity.class);
		addIntent.putExtra("duplicate", false); 
		// 设置快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 设置快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 设置快捷方式对应的Intent
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		// 发送广播添加快捷方式
		sendBroadcast(addIntent);
		Toaster.showOneToast("快捷方式已创建");
	}

	private void initnotifacationView() {
		XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
		build.setSound(
				RingtoneManager.getActualDefaultRingtoneUri(
						getApplicationContext(), RingtoneManager.TYPE_ALARM)) // 设置声音
				// setSound(
				// Uri.parse("android.resource://" + getPackageName()
				// + "/" + R.raw.wind)) 设定Raw下指定声音文件
				.setDefaults(Notification.DEFAULT_VIBRATE) // 振动
				.setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
		// 设置自定义通知layout,通知背景等可以在layout里设置
		build.setLayoutId(R.layout.notification);
		// 设置自定义通知内容id
		build.setLayoutTextId(R.id.content);
		// 设置自定义通知标题id
		build.setLayoutTitleId(R.id.title);
		// 设置自定义通知图片id
		build.setLayoutIconId(R.id.icon);
		// 设置自定义通知图片资源
		build.setLayoutIconDrawableId(R.drawable.ic_launcher);
		// 设置状态栏的通知小图标
		build.setIcon(R.drawable.ic_launcher);
		// 设置时间id
		build.setLayoutTimeId(R.id.time);

		// build.setNotificationLargeIcon(R.drawable.logo22);
		// 客户端保存build_id
		// XGPushManager.setPushNotificationBuilder(this,0 , build);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_in_button:
			// 表单校验
			if (valid()) {
				if (progresslayout.getVisibility() == View.GONE) { // 处理用户登陆的业务逻辑
																	// 用户登陆完成
																	// 隐藏不确定进度条
					progresslayout.setVisibility(View.VISIBLE);
				} else {
					progresslayout.setVisibility(View.GONE);
				}
				username.setEnabled(false);
				password.setEnabled(false);
				autoSign.setEnabled(false);
				resetPassword.setEnabled(false);
				// handler.sendEmptyMessageDelayed(1, 4500);
				handler.sendEmptyMessage(2);
				httpLogin(v);
			}
			break;

		}
	}

	// 表单验证
	private boolean valid() {
		try {
			String account = username.getText().toString().trim();
			basicMember.setLoginname(account);
			if (TextUtils.isEmpty(account)) {
				Toaster.showResIdToast(this, R.string.login_id_input_empty);
				username.requestFocus();
				return false;
			}/*
			 * else { if (account.contains("@") && account.contains(".")) { if
			 * (StringUtils.isEmail(account)) {
			 * basicMember.setUserEmail(account); } } else if
			 * (StringUtils.isMobileNO(account)) {
			 * basicMember.setUserNum(account); } else {
			 * Toaster.showResIdToast(this, R.string.login_id_input_error);
			 * loginId.setText(""); loginId.requestFocus(); return false; } }
			 */
			basicMember.setLoginpassword(password.getText().toString().trim());
			if (TextUtils.isEmpty(basicMember.getLoginpassword())) {
				Toaster.showResIdToast(this, R.string.login_pass_input_empty);
				password.requestFocus();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toaster.showResIdToast(this, R.string.login_input_error);
		}
		return true;
	}

	private void autoLogin() {
		RequestParams params = new RequestParams();
		params.put(BasicMember.LOING_TOKENID, getTokenID());
		params.put(BasicMember.LOING_APPTOKENID,
				XGPushConfig.getToken(LoginActivity.this));
		params.put(RequestUri.CODE, RequestUri.LOGIN_CODE);
		// 不确定的dialog
		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {
					private boolean isSuc = false;
					final ProgressDialog progressDialog = new ProgressDialog(
							LoginActivity.this);

					@Override
					public void onStart() {
						// 网络请求开始
						progressDialog.setMessage("正在拉取数据");
						progressDialog.setCancelable(false);
						progressDialog.show();
						hints.setVisibility(View.VISIBLE);
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseUserJson(content);
						isSuc = true;
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						isSuc = false;
						hints.setText("请求登陆失败");
						if (progressDialog != null
								&& progressDialog.isShowing()) {
							progressDialog.dismiss();
						}

					}

					@Override
					public void onFinish() {
						if (!isSuc) {

						}
						if (progressDialog != null
								&& progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}

				});
	}

	private void httpLogin(final View v) {
		RequestParams params = new RequestParams();
		params.put(BasicMember.LOING_NAME, basicMember.getLoginname());
		params.put(BasicMember.LOING_PASSWORD, basicMember.getLoginpassword());
		params.put(BasicMember.LOING_APPTOKENID,
				XGPushConfig.getToken(LoginActivity.this));
		params.put(RequestUri.CODE, RequestUri.LOGIN_CODE);
		// 不确定的dialog
		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {
					private boolean isSuc = false;

					@Override
					public void onStart() {
						// 网络请求开始
						hints.setVisibility(View.VISIBLE);
						v.setEnabled(false);
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseUserJson(content);
						isSuc = true;
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						v.setEnabled(true);
						isSuc = false;
						handler.sendEmptyMessage(1);
					}

					@Override
					public void onFinish() {
						if (!isSuc) {
							handler.sendEmptyMessage(1);
						}
						v.setEnabled(true);
					}

				});
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				// 请求登陆失败
				hints.setText("登录请求失败");
				username.setEnabled(true);
				password.setEnabled(true);
				autoSign.setEnabled(true);
				resetPassword.setEnabled(true);
				progresslayout.setVisibility(View.GONE);
			} else if (msg.what == 2) {
				// 准备请求登陆
				username.setEnabled(false);
				password.setEnabled(false);
				autoSign.setEnabled(false);
				resetPassword.setEnabled(false);
				hints.setText(R.string.default_login_hint);
			} else if (msg.what == 3) {
				username.setEnabled(true);
				password.setEnabled(true);
				autoSign.setEnabled(true);
				resetPassword.setEnabled(true);
				progresslayout.setVisibility(View.GONE);
			} else if (msg.what == 0) {
				startActivity(new Intent(LoginActivity.this,
						MainTabActivity.class));
				progresslayout.setVisibility(View.GONE);
				username.setEnabled(true);
				password.setEnabled(true);
				autoSign.setEnabled(true);
				resetPassword.setEnabled(true);
				LoginActivity.this.finish();
			}

		}

	};

	private BaseAppDbHelper<UserEntity> dbHelper = new BaseAppDbHelper<UserEntity>();

	// 解析用户信息
	private void parseUserJson(String json) {
		UserEntity bean = ParseJson.get_loginJSON(json);
		if (bean != null && bean.getU_id() != 0) {

			XGPushManager.registerPush(this, bean.getMoble() + "",
					bean.getMoble() + "", 1, null, new XGIOperateCallback() {
						@Override
						public void onSuccess(Object data, int flag) {
							Log.d("TPush", "注册成功，设备token为：" + data);
//							Toaster.showOneToast(XGPushConfig.getToken(LoginActivity.this));
						}

						@Override
						public void onFail(Object data, int errCode, String msg) {
							Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息："
									+ msg);
						}
					});

			// 保存或者更新用户信息到数据库
			setLoginKey(bean.getU_id());
			if (autoSign.isCheck()) // 自动登陆按钮为选中
				setTokenID(bean.getTokenId());
			// 查询数据库中是否有跟ID想等的数据
			UserEntity dblogin = new UserEntity();
			dblogin = dbHelper.queryObjForEq(UserEntity.class,
					UserEntity.JSON_ID, bean.getU_id());
			if (dblogin == null) {
				// 没有的话就创建对象 插入一条数据
				int i = dbHelper.create(bean);
			} else {
				// 如果有的话 吧数据库中的ID引用到请求后解析完成后的对象
				bean.setId(dblogin.getId());
				// 如果有localID就跟新数据库 否则插入数据库
				dbHelper.createOrUpdate(bean);
			}
			handler.sendEmptyMessage(0);
		} else {

			Status st = ParseJson.getStatus(json);
			hints.setText(st.getMsg());
			handler.sendEmptyMessage(3);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	// 设置用户的TokenID就是服务器上的ID 用sharedprefrence保存用户的ID
	public static void setTokenID(String tokenid) {
		PreferencesUtils.setStringPreferences(AppApplication.getContext(),
				AppApplication.PREFERENCE_NAME, UserEntity.JSON_TOKENID,
				tokenid);
	}

	// 获取用户的Tokenid 为空则尚未登录
	public static String getTokenID() {
		return PreferencesUtils.getStringPreference(
				AppApplication.getContext(), AppApplication.PREFERENCE_NAME,
				UserEntity.JSON_TOKENID, "");
	}

	// 设置用户的key就是服务器上的ID 用sharedprefrence保存用户的ID
	public static void setLoginKey(long user_id) {
		PreferencesUtils.setLongPreference(AppApplication.getContext(),
				AppApplication.PREFERENCE_NAME, UserEntity.JSON_ID, user_id);
	}

	// 获取用户的key
	public static long getLoginKey() {
		return PreferencesUtils.getLongPreference(AppApplication.getContext(),
				AppApplication.PREFERENCE_NAME, UserEntity.JSON_ID, -1);
	}

	public static final String TAG = "LoginActivity";

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
		MobclickAgent.onPause(this);
	}

}
