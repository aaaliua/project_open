package com.linju.android_property.servicemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.PropertyAdapter;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.Building_info_Bean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Property_Fee_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.DownloadUtils;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StartActivityUtils;
import com.linju.android_property.utils.StringUtils;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PropertyList extends BaseActivity implements OnClickListener,OnItemClickListener,OnRefreshListener2{

	@InjectView(R.id.app_loading)
	View loadinglayout;
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;
	@InjectView(R.id.empty)
	RelativeLayout empey;
	@InjectView(R.id.lists)
	PullToRefreshListView mListView;
	
	private PropertyAdapter adapter;
	private List<Property_Fee_Bean> beans;
	private String pullUrl;
	public  int limite = 10; // 每页10条
	public  int offsetint = 0; // 分页标识
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_propert_list);
		titlebar.setText(getString(R.string.wuye));
		back.setOnClickListener(this);
		
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("没有任何数据哦!");
		mListView.setEmptyView(empey);
		mListView.setOnRefreshListener(this);
		
		mListView.setShowIndicator(false);      //让其快速滑动不会出现view 还要在XML中加上app:ptrOverScroll="false"其阻止
		getList(); 
	}

	
	// 获取服务器上列表
		private void getList() {
			RequestParams params = new RequestParams();
			params.put(BasicMember.OFFSET, offsetint + "");
			params.put(BasicMember.LIMITE, limite + "");
			params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
					.getUser().getSubdistrict_address_id());
			
			pullUrl = RequestURL.GET_PROPERTY_LIST + "?" + params.toString();
			
			AppApplication.getHttpClient().get(RequestURL.GET_PROPERTY_LIST,
					params, new AsyncHttpResponseHandler() {

						@Override
						@Deprecated
						public void onFailure(Throwable error) {
							super.onFailure(error);
						}

						@Override
						public void onFinish() {
							loadinglayout.setVisibility(View.GONE);
						}

						@Override
						public void onStart() {
							super.onStart();
							loadinglayout.setVisibility(View.VISIBLE);
						}

						@Override
						@Deprecated
						public void onSuccess(String content) {
							parseJsonbean(content, false);
						}

					});
		}
	
		/**
		 * 
		 * @param json json数据
		 * @param loadMore 是否是加载下一页
		 */
		private void parseJsonbean(String json,boolean loadMore){
			if(loadMore == true){
				List<Property_Fee_Bean> be = ParseJson.property_feeJSON(json);
				if(be != null && be.size() > 0 ){
					beans.addAll(be);
					mListView.onRefreshComplete();
					adapter.notifyDataSetChanged();
					//判断数据是否小于10条 
					if(be.size() < limite){
						mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
					}else{
						mListView.setMode(Mode.BOTH);
					}
				}
			}else{
				
					if(beans != null){
						beans.clear();
					}
					beans = ParseJson.property_feeJSON(json);
					//获取数据判断数据是否为空 
					if(beans != null && beans.size() > 0 ){
						
						adapter = new PropertyAdapter(this,beans);
						//判断数据是否小于10条 
						mListView.onRefreshComplete();
						if(beans.size() < limite){
							mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
						}else{
							mListView.setMode(Mode.BOTH);
						}
				}
					mListView.setAdapter(adapter);
					mListView.setOnItemClickListener(this);
			}
			
		}
	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Property_Fee_Bean b = beans.get(position - 1);
		Intent it = new Intent(this,PropertyInfo.class);
		it.putExtra(PropertyInfo.EXTRA_OBJ, b);
		StartActivityUtils.startActivity(this,it);
	}


	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		String label = DateUtils.formatDateTime(this.getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		new GetDataTask().execute();		
	}
	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String result = null;
			offsetint = 0; // 让分页标示恢复初始化
			String url = pullUrl;

			File file = new File(AppApplication.mSdcardCache
					+ File.separator+StringUtils.replaceUrlWithPlus(url));
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			try {
				DownloadUtils.download(url, file, false, null);
				result = FileUtils.readTextFile(file);
			} catch (Exception e) {
				// TODO: handle exception
				// Logger.e("ContentListFragment", "下拉刷新", e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				parseJsonbean(result,false);
			}
			// Call onRefreshComplete when the list has been refreshed.
			mListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		String label = DateUtils.formatDateTime(this.getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		int position = beans.size() - 1;
		loadMoreData1(position);
	}
	private void loadMoreData1(final int position) {
		RequestParams params = new RequestParams();
		params.put(BasicMember.OFFSET, String.valueOf((offsetint = (offsetint + limite))));
		params.put(BasicMember.LIMITE, limite + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		AppApplication.getHttpClient().get(RequestURL.GET_PROPERTY_LIST, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						parseJsonbean(content,true);
						mListView.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						mListView.onRefreshComplete();
						Toaster.showOneToast(R.string.app_loading_fail);
					}
				});
	}
	
}
