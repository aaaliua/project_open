package com.linju.android_property.servicemanager;

import java.util.ArrayList;

import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.ComplaintAdapter;
import com.linju.android_property.adapter.NoticeAdapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.dialog.ActionSheetDialog;
import com.linju.android_property.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.linju.android_property.dialog.ActionSheetDialog.SheetItemColor;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Notice_Manage_Bean;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StartActivityUtils;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 公告列表
 * @author Administrator
 *
 */
public class PostList extends BaseActivity implements OnClickListener,OnItemClickListener,OnItemLongClickListener{
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
	
	private NoticeAdapter adapter;
	private ArrayList<Notice_Manage_Bean> beans;
	public static int limite = 15; // 每页10条
	public static int offsetint = 0; // 分页标识
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_post_list);
		back.setOnClickListener(this);
		titlebar.setText(getString(R.string.gonggao));
		editOrAdd.setVisibility(View.VISIBLE);
		editOrAdd.setText(getString(R.string.add));
		editOrAdd.setOnClickListener(this);
		
		
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("还没有任何的公告信息!");
		mListView.setEmptyView(empey);
		mListView.setShowIndicator(false); 
		getListDate();
	}
	
	private void getListDate(){
		RequestParams params = new RequestParams();
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		params.put(Login_Bean.FROM, "2");
		AppApplication.getHttpClient().get(RequestURL.GET_POST_LIST,params, new AsyncHttpResponseHandler(){

			@Override
			@Deprecated
			public void onFailure(Throwable error) {
				super.onFailure(error);
			}

			@Override
			public void onFinish() {
				super.onFinish();
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
			
		});

	}
	
	private void parseJsonbean(String json, boolean isLoadMore) {
		if (isLoadMore == true) {

		} else {
			if (beans != null) {
				beans.clear();
			}
			beans = ParseJson.get_notice_list(json);
			// 获取数据判断数据是否为空
			if (beans != null) {

				adapter = new NoticeAdapter(this, beans);
				// 判断数据是否小于limite
				mListView.onRefreshComplete();
				if (beans.size() < limite) {
					mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
				} else {
					mListView.setMode(Mode.BOTH);
				}
			}
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(this);
			mListView.getRefreshableView().setOnItemLongClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.edit_or_add:
			startActivity(new Intent(this,PostAdd.class));
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			break;
	}
}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Notice_Manage_Bean bean = beans.get(position -1);
		Intent it = new Intent(this,PostContentActivity.class);
		it.putExtra(PostContentActivity.EXTRA_OBJ, bean);
		StartActivityUtils.startActivity(this, it);
	}
	
	
	
	//长按后的弹出框
	class PopupWindowPic extends PopupWindow{

		public PopupWindowPic(Context context , View v) {
			View view = View.inflate(context, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
			LinearLayout llLayout = (LinearLayout)view.findViewById(R.id.ll_popup);
			llLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));
			//设置宽高
			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);     
			setOutsideTouchable(true);  //外部触摸不隐藏
			setContentView(view);
			showAtLocation(v, Gravity.BOTTOM, 0, 0);
			update();
			
			
			Button camera = (Button)view.findViewById(R.id.item_popupwindows_camera);
			Button photo = (Button)view.findViewById(R.id.item_popupwindows_Photo);
			Button cancel = (Button)view.findViewById(R.id.item_popupwindows_cancel);
			camera.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			photo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}
		
	}



	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		new PopupWindowPic(this,view);
		return true;
	}
}
