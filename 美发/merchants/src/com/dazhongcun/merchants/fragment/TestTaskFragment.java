package com.dazhongcun.merchants.fragment;

import com.aaaliua.actionbarpulltorefresh.PullToRefreshLayout;
import com.aaaliua.actionbarpulltorefresh.PullToRefreshLayout.OnRefreshListener;
import com.dazhongcun.baseactivity.BaseFragment;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.activity.MainTabActivity;
import com.dazhongcun.merchants.adapter.RecyclerViewAdapter;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.user.BasicMember;
import com.dazhongcun.merchants.user.LoginActivity;
import com.dazhongcun.merchants.utils.RequestUri;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * 订单fragmetn
 * @author LT
 *
 */
public class TestTaskFragment extends BaseFragment implements OnRefreshListener{

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_task_a, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if(LoginActivity.getLoginKey() == -1){
			//用户没有登录 跳转登录
			return;
		}


        
    }

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	
	
}
