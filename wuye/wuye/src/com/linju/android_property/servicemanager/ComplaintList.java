package com.linju.android_property.servicemanager;

import java.util.ArrayList;

import org.apache.http.Header;

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
import com.linju.android_property.adapter.ComplaintAdapter;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 投诉列表
 * @author Administrator
 *
 */
public class ComplaintList extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;

	@InjectView(R.id.app_loading)
	View loadinglayout;

	@InjectView(R.id.lists)
	PullToRefreshListView mListView;

	@InjectView(R.id.empty)
	RelativeLayout empey;

	private ArrayList<ComplaintBean> beans;
	private ComplaintAdapter adapter;

	public  int limite = 15; // 每页10条
	public  int offsetint = 0; // 分页标识

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_complaint_list);
		titlebar.setText(getString(R.string.complaint_title));
		back.setOnClickListener(this);
		
		
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("没有任何数据哦!");
		mListView.setEmptyView(empey);
		mListView.setShowIndicator(false);
		getList();
	}

	// 获取服务器上投诉列表
	private void getList() {
		RequestParams params = new RequestParams();
		params.put(BasicMember.OFFSET, offsetint + "");
		params.put(BasicMember.LIMITE, limite + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());

		AppApplication.getHttpClient().get(RequestURL.GET_COMPLAINT_LIST, params,
				new AsyncHttpResponseHandler() {

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
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseJsonbean(content, false);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1, arg2);
					}
					
					

				});
	}

	private void parseJsonbean(String json, boolean isLoadMore) {
		if (isLoadMore == true) {

		} else {
			if (beans != null) {
				beans.clear();
			}
			beans = ParseJson.GetComplaintJSON(json);
			// 获取数据判断数据是否为空
			if (beans != null) {

				adapter = new ComplaintAdapter(this, beans);
				// 判断数据是否小于10条
				mListView.onRefreshComplete();
				if (beans.size() < limite) {
					mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
				} else {
					mListView.setMode(Mode.BOTH);
				}
			}
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ComplaintBean b =beans.get(position-1);
		Intent it = new Intent(this,ComplaintInfo.class);
		it.putExtra(ComplaintInfo.EXTRA_OBJ, b);
		startActivity(it);
		
		
		//actiity的跳转动画
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}

}
