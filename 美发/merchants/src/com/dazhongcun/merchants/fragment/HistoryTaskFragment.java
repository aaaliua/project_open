package com.dazhongcun.merchants.fragment;

import java.util.List;

import com.aaaliua.actionbarpulltorefresh.PullRefreshLayout;
import com.aaaliua.actionbarpulltorefresh.PullToRefreshLayout;
import com.aaaliua.actionbarpulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.dazhongcun.baseactivity.BaseFragment;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.activity.HistoryActivity;
import com.dazhongcun.merchants.activity.MainTabActivity;
import com.dazhongcun.merchants.adapter.HistoryListAdapter;
import com.dazhongcun.merchants.adapter.HistoryRecyclerViewAdapter;
import com.dazhongcun.merchants.adapter.HistoryRecyclerViewAdapterDestory;
import com.dazhongcun.merchants.adapter.RecyclerViewAdapter;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.merchants.entity.Status;
import com.dazhongcun.merchants.user.BasicMember;
import com.dazhongcun.merchants.user.LoginActivity;
import com.dazhongcun.merchants.utils.ParseJson;
import com.dazhongcun.merchants.utils.RequestUri;
import com.dazhongcun.views.Toaster;
import com.dazhongcun.widget.SwipeRefreshLayoutImp;
import com.dazhongcun.widget.SwipeRefreshLayoutImp.Mode;
import com.dazhongcun.widget.SwipeRefreshLayoutImp.OnLoadListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;



/**
 * 订单fragmetn
 * @author LT
 *
 */
public class HistoryTaskFragment extends BaseFragment {

	public static int ALL = 1;
	public static int CANCEL = 2;
	public static int DONE = 3;
	
	@InjectView(R.id.app_loading)
	View v;
	
	@InjectView(R.id.list_view)
	RecyclerView recyclerView;
	
	private int typeID = 1;
	
	
	boolean loading = false;
	private List<MakeEntity> makes;
	private HistoryRecyclerViewAdapterDestory adapter;

	public int number = 1;
	
	private   boolean loadFlag = false;
	
	
	private String currentCode ;
	public HistoryTaskFragment(int typeid) {
		this.typeID = typeid;
	}
	public HistoryTaskFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history_task, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(LoginActivity.getLoginKey() == -1){
			//用户没有登录 跳转登录
			return;
		}
		
		
		
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        
//		 LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()); 
//		 linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//		 recyclerView.setLayoutManager(linearLayoutManager); 
        
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				System.out.println(loadFlag);
				if (!loading && layoutManager.findLastVisibleItemPosition() == makes.size() - 1 && loadFlag) {
					loading = true;
					MakeEntity obj = new MakeEntity();
					obj.setLoadtype(MakeEntity.LOADMORE);
					adapter.add(obj);
					//去加载更多
					loadMore();
				}

			}
		});
		getMakeDate();
		
    }
	
	private void loadMore(){
		RequestParams params = new RequestParams();
		params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey() + "");
		params.put(BasicMember.PARAMS_STATUS,currentCode);
		params.put(RequestUri.CODE, RequestUri.MAKE_CODE);
		params.put(BasicMember.PARAMS_PAGESIZE, BasicMember.size+"");
		params.put(BasicMember.PARAMS_PAGENUMBER, (number += 1) +"");
		System.out.println(RequestUri.BASE_URL + params.toString());
		
		AppApplication.getHttpClient().get(RequestUri.BASE_URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onStart() {
						// 网络请求开始
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseJson(content,true);
					}

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						// 设置list的Emptyview
					}

					@Override
					public void onFinish() {
						
					}

				});
	}
	
	private void getMakeDate(){
		
			RequestParams params = new RequestParams();
			params.put(BasicMember.PARAMS_USERID, LoginActivity.getLoginKey()+"");
			if(typeID == ALL){
				StringBuffer buf = new StringBuffer();
				buf.append("1");
				buf.append(",");
				buf.append("2");
				buf.append(",");
				buf.append("3");
				buf.append(",");
				buf.append("4");
				buf.append(",");
				buf.append("5");
				currentCode = buf.toString();
				params.put(BasicMember.PARAMS_STATUS,buf.toString());
			}else if(typeID == DONE){
				currentCode =  4+"";
				params.put(BasicMember.PARAMS_STATUS, 4+"");
			}else if(typeID == CANCEL){
				currentCode =  5+"";
				params.put(BasicMember.PARAMS_STATUS, 5+"");
			}
			params.put(RequestUri.CODE, RequestUri.MAKE_CODE);
			params.put(BasicMember.PARAMS_PAGESIZE, BasicMember.size+"");
			params.put(BasicMember.PARAMS_PAGENUMBER, number +"");
			System.out.println(RequestUri.BASE_URL + "?"+params.toString());
			//不确定的dialog
			AppApplication.getHttpClient().get(RequestUri.BASE_URL,params ,new AsyncHttpResponseHandler(){
				private boolean isSuc = false;
				@Override
				public void onStart() {
					//网络请求开始
					 v.setVisibility(View.VISIBLE);
				}

				@Override
				@Deprecated
				public void onSuccess(String content) {
					parseJson(content,false);
				}
				
				@Override
				@Deprecated
				public void onFailure(Throwable error) {
					v.setVisibility(View.GONE);
					//设置list的Emptyview
				}

				@Override
				public void onFinish() {
					v.setVisibility(View.GONE);
				}

			});
	}
	
	
	private void parseJson(String json,boolean isload){
		
		if(isload){
			List<MakeEntity> more = ParseJson.getMakeEntityList(json);
			if (more != null) {
				if(more.size() ==BasicMember.size){
					loadFlag = true;
				}else{
					loadFlag = false;
				}
				adapter.remove(adapter.getItemCount() - 1);
				adapter.addAll(more);
				loading = false;
//				Toaster.showOneToast("数据添加");
			}else{
				adapter.remove(adapter.getItemCount() - 1);
				Toaster.showOneToast("数据加载完成");
			}
		}else{
			makes = ParseJson.getMakeEntityList(json);
			if(makes != null){
				if(makes.size() ==BasicMember.size){
					loadFlag = true;
				}else{
					loadFlag = false;
				}
				adapter = new HistoryRecyclerViewAdapterDestory(getActivity(), makes, getActivity());
				recyclerView.setAdapter(adapter);
				
			}else{
				Status st = ParseJson.getStatus(json);
				Toaster.showOneToast(st.getMsg());
			}
		}
		
	}

	public static final String TAG = "HistoryTaskFragment";
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}
}
