package com.dazhongcun.merchants.fragment;

import com.dazhongcun.application.BaseApplication;
import com.dazhongcun.baseactivity.BaseFragment;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.adapter.RecyclerViewAdapter;
import com.dazhongcun.merchants.adapter.RecyclerViewGalleyAdapter;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.database.BaseAppDbHelper;
import com.dazhongcun.merchants.entity.Status;
import com.dazhongcun.merchants.entity.UserEntity;
import com.dazhongcun.merchants.user.BasicMember;
import com.dazhongcun.merchants.user.LoginActivity;
import com.dazhongcun.merchants.user.SettingActivity;
import com.dazhongcun.merchants.utils.ImageOptions;
import com.dazhongcun.merchants.utils.ParseJson;
import com.dazhongcun.merchants.utils.RequestUri;
import com.dazhongcun.views.Toaster;
import com.dazhongcun.widget.SwipeRefreshLayoutImp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 任务管理的fragmetn
 * 
 * @author LT
 * 
 */
public class MyFragment extends BaseFragment implements OnRefreshListener {

	@InjectView(R.id.swipe)
	SwipeRefreshLayout layout;

	@InjectView(R.id.more)
	View moreView;

	@InjectView(R.id.name)
	TextView name;
	@InjectView(R.id.address)
	TextView address;
	@InjectView(R.id.storeAddress)
	TextView storeAddress;
	@InjectView(R.id.storeName)
	TextView storeName;

	@InjectView(R.id.userImage)
	ImageView img; // 头像
	@InjectView(R.id.service)
	TextView service; // 服务
	@InjectView(R.id.praise)
	TextView praise; // 点赞

	@InjectView(R.id.profile)
	TextView profile; // 个人简介

	@InjectView(R.id.recycler_img)
	RecyclerView recycler_img; // 作品集
	private BaseAppDbHelper<UserEntity> dbHelper = new BaseAppDbHelper<UserEntity>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		System.out.println("onActivityCreated");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");
		return inflater.inflate(R.layout.fragment_my, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		System.out.println("onViewCreated");
		
		layout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,  
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		layout.setOnRefreshListener(this);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
				getActivity());
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		recycler_img.setLayoutManager(linearLayoutManager);

		// recycler_img.setHasFixedSize(true);
		// recycler_img.setItemAnimator(new DefaultItemAnimator());
		// recycler_img.setLayoutManager(new
		// LinearLayoutManager(getActivity()));
		// recycler_img.addItemDecoration(new
		// DividerItemDecoration(getActivity(),
		// DividerItemDecoration.HORIZONTAL_LIST));

		// UserEntity dblogin = new UserEntity();
		// dblogin = dbHelper.queryObjForEq(UserEntity.class,
		// UserEntity.JSON_ID, LoginActivity.getLoginKey());
		// if(dblogin != null){
		// name.setText(dblogin.getName());
		// address.setText(dblogin.getAddress());
		// storeAddress.setText(dblogin.getAddress());
		//
		// }

		if (LoginActivity.getLoginKey() == -1) {
			// 尚未登录
			Intent it = new Intent();
			it.setAction(BaseApplication.EXIT_APP);
			getActivity().sendBroadcast(it);
			getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
			getActivity().finish();
		} else {
			// 已经是登录状态 获取详细信息
			getInfo();
		}

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(
						new Intent(getActivity(), SettingActivity.class));
			}
		});
	}

	private void getInfo() {
		RequestParams params = new RequestParams();
		params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
		params.put(RequestUri.CODE, RequestUri.INFO_CODE);
		// 不确定的dialog
		Log.d("aaaliua", RequestUri.BASE_URL + "?" + params.toString());
		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {
					private boolean isSuc = false;
					final ProgressDialog progressDialog = new ProgressDialog(
							getActivity());

					@Override
					public void onStart() {
						// 网络请求开始
						progressDialog.setMessage("正在拉取数据");
						progressDialog.setCancelable(false);
						progressDialog.show();
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseUserJson(content);
						Log.d("aaaliua", content);
						isSuc = true;
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						isSuc = false;
						if (progressDialog != null
								&& progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}

					@Override
					public void onFinish() {
						if (!isSuc) {
							Toaster.showDefToast(getActivity(), "数据请求失败");
						}

						if (progressDialog != null
								&& progressDialog.isShowing()) {
							progressDialog.dismiss();
						}
					}

				});
	}

	private void parseUserJson(String json) {
		UserEntity bean = ParseJson.get_loginJSON(json);
		if (bean != null) {

			UserEntity dblogin = new UserEntity();
			dblogin = dbHelper.queryObjForEq(UserEntity.class,
					UserEntity.JSON_ID, LoginActivity.getLoginKey());
			if (dblogin == null) {
				// 没有的话就创建对象 插入一条数据
				int i = dbHelper.create(bean);
			} else {
				// 如果有的话 吧数据库中的ID引用到请求后解析完成后的对象
				bean.setId(dblogin.getId());
				// 如果有localID就跟新数据库 否则插入数据库
				dbHelper.createOrUpdate(bean);
			}

			name.setText(bean.getName());
			address.setText(bean.getAddress());
			storeAddress.setText(bean.getAddress());
			service.setText("服务" + bean.getServicefraction());
			praise.setText(bean.getPraise());
			profile.setText(bean.getSpecialty());
			storeName.setText(bean.getShopname());
			AppApplication.getImageLoader().displayImage(bean.getImagePath(),
					img, ImageOptions.defaultOptions);
			if (bean.getWorks() != null) {
				String[] datas = bean.getWorks().split("##");
				RecyclerViewGalleyAdapter adapter = new RecyclerViewGalleyAdapter(
						getActivity(), datas, getActivity());
				recycler_img.setAdapter(adapter);
			}

		} else {
			Status st = ParseJson.getStatus(json);
			Toaster.showOneToast(st.getMsg());
		}
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getInfo();
				layout.setRefreshing(false);
			}
		}, 1000);
	}
	
	
	public static final String TAG = "MyFragment";
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

}
