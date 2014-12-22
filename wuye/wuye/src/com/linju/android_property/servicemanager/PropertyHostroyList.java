package com.linju.android_property.servicemanager;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.PropertyAdapter;
import com.linju.android_property.adapter.PropertyHistoryAdapter;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.Building_info_Bean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Property_Fee_Bean;
import com.linju.android_property.entity.Property_fee_Histroy_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StartActivityUtils;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PropertyHostroyList extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	@InjectExtra(value = PropertyInfo.EXTRA_OBJ , optional = true)
	Property_Fee_Bean bean;
	
	
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
	
	private PropertyHistoryAdapter adapter;
	private List<Property_fee_Histroy_Bean> beans;
	private String pullUrl;
	public  int limite = 10; // 每页10条
	public  int offsetint = 0; // 分页标识
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_propert_list);
		titlebar.setText(getString(R.string.lou_property_history));
		back.setOnClickListener(this);
		
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("没有任何数据哦!");
		mListView.setEmptyView(empey);
		mListView.setShowIndicator(false);
		
		getList();
	}

	
	// 获取服务器上列表
		private void getList() {
			RequestParams params = new RequestParams();
			params.put(BasicMember.OFFSET, offsetint + "");
			params.put(BasicMember.LIMITE, limite + "");
			params.put(Property_fee_Histroy_Bean.HIS_ID, bean.getId());
			params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
					.getUser().getSubdistrict_address_id());
			
			
			AppApplication.getHttpClient().get(RequestURL.GET_PROPERTY_HOSTROY_LIST,
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
				List<Property_fee_Histroy_Bean> be = ParseJson.property_fee_histroyJSON(json);
				if(be != null && be.size() > 0 ){
					beans.addAll(be);
					mListView.onRefreshComplete();
					adapter.notifyDataSetChanged();
					//判断数据是否小于10条 
					if(be.size() <= limite){
						mListView.setMode(Mode.DISABLED);
					}else{
						mListView.setMode(Mode.PULL_UP_TO_REFRESH);
					}
				}
			}else{
				
					if(beans != null){
						beans.clear();
					}
					beans = ParseJson.property_fee_histroyJSON(json);
					//获取数据判断数据是否为空 
					if(beans != null && beans.size() > 0 ){
						
						adapter = new PropertyHistoryAdapter(this,beans);
						//判断数据是否小于10条 
						mListView.onRefreshComplete();
						if(beans.size() < limite){
							mListView.setMode(Mode.DISABLED);
						}else{
							mListView.setMode(Mode.PULL_UP_TO_REFRESH);
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
	}

	
}
