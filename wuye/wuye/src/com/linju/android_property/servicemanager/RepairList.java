package com.linju.android_property.servicemanager;

import java.io.File;
import java.util.ArrayList;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.DownloadUtils;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StringUtils;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 报修的activity  list
 * 
 * @author Administrator
 * 
 */
public class RepairList extends BaseActivity implements OnClickListener,
		AnimationListener, OnItemClickListener, OnCheckedChangeListener,
		OnRefreshListener2 {

	// 0:未处理 1:处理中 2:已完成 3:已取消）
	public static final int unfinish = 0;
	public static final int finish = 1;
	public static final int processing = 2;
	public static final int cancelled = 3;

	public static final String sug = "&";

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
	
	@InjectView(R.id.action_bar)
	View actionbar;
	@InjectView(R.id.query)
	EditText query;
	@InjectView(R.id.search_clear)
	ImageButton clear;
	
	@InjectView(R.id.mainTitle)    //页面搜索跟分类
	RelativeLayout searchTitle;
	@InjectView(R.id.filter)
	RadioGroup mRadioGroup;

	@InjectView(R.id.mainLinelayout)
	LinearLayout mainLayout;
	@InjectView(R.id.empty)
	RelativeLayout empey;
	@InjectView(R.id.lists)
	PullToRefreshListView mListView;

	private ListView mlist;

	private ArrayList<Talk_repair_Bean> beans;
	private RepairAdapter adapter;
	public  int limite = 10; // 每页10条
	public  int offsetint = 0; // 分页标识
	public int type = 0; // 选中后的类型 默认是0
	
	public static int offsetintlocal = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_list);
		editOrAdd.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		titlebar.setText(getResources().getString(R.string.baoxiu));
		back.setOnClickListener(this);
		clear.setVisibility(View.VISIBLE);

		mRadioGroup.setOnCheckedChangeListener(this);
		// listview的刷新监听
		mListView.setOnRefreshListener(this);
		// clear.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if(actionbar.getVisibility() == View.GONE){
		// actionbar.setVisibility(View.VISIBLE);
		// }else{
		// actionbar.setVisibility(View.GONE);
		// }
		// }
		// });

		query.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					actionbar.setVisibility(View.GONE);
				} else {
					actionbar.setVisibility(View.VISIBLE);
				}
			}
		});

		initListView();

	}

	// 初始化listview的数据
	private void initListView() {
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("没有任何数据哦!");
		mListView.setEmptyView(empey);

		mlist = mListView.getRefreshableView();
		
		// 默认获取未完成的列表
		getlistData(unfinish);
	}

	private void getlistData(int i) {
		mListView.onRefreshComplete();
		loadinglayout.setVisibility(View.VISIBLE);
		// 网络请求 获取报修信息数据 根据I的类型
		RequestParams params = new RequestParams();
		params.put(BasicMember.OFFSET, offsetint + "");
		params.put(BasicMember.LIMITE, limite + "");
		params.put(BasicMember.STATUS, i + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());

		AppApplication.getHttpClient().get(RequestURL.GET_REPAIR_LIST, params,
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
						parseJsonbean(content,false);
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
			ArrayList<Talk_repair_Bean> be = ParseJson.Get_Repair_JSON(json);
			if(be != null && be.size() > 0 ){
				beans.addAll(be);
				mListView.onRefreshComplete();
				adapter.notifyDataSetChanged();
				//判断数据是否小于10条 
				if(be.size() <= limite){
					mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
				}else{
					mListView.setMode(Mode.BOTH);
				}
			}
		}else{
			
				if(beans != null){
					beans.clear();
				}
				beans = ParseJson.Get_Repair_JSON(json);
				//获取数据判断数据是否为空 
				if(beans != null && beans.size() > 0 ){
					
					adapter = new RepairAdapter(this,beans);
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
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		default:
			break;
		}
	}

	
	//手势动画  
	private float lastX = 0;
	private float lastY = 0;
	private boolean mIsTitleHide = false;
	private boolean mIsAnim = false;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(event);
		if (mIsAnim || mlist.getCount() <= 80) {
			return false;
		}
		final int action = event.getAction();

		float x = event.getX();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastY = y;
			lastX = x;
			return false;
		case MotionEvent.ACTION_MOVE:
			float dY = Math.abs(y - lastY);
			float dX = Math.abs(x - lastX);
			boolean down = y > lastY ? true : false;
			lastY = y;
			lastX = x;
			if (dX < 8 && dY > 8 && !mIsTitleHide && !down) {
				Animation anim = AnimationUtils.loadAnimation(RepairList.this,
						R.anim.push_top_in);
				// anim.setFillAfter(true);
				anim.setAnimationListener(RepairList.this);
				searchTitle.startAnimation(anim);
			} else if (dX < 8 && dY > 8 && mIsTitleHide && down) {
				Animation anim = AnimationUtils.loadAnimation(RepairList.this,
						R.anim.push_top_out);
				// anim.setFillAfter(true);
				anim.setAnimationListener(RepairList.this);
				searchTitle.startAnimation(anim);
			} else {
				return false;
			}
			mIsTitleHide = !mIsTitleHide;
			mIsAnim = true;
			break;
		default:
			return false;
		}
		return false;
	}

	@Override
	public void onAnimationStart(Animation animation) {

		searchTitle.setVisibility(View.VISIBLE);
		if (mIsTitleHide) {
			LinearLayout.LayoutParams lp = (LayoutParams) mainLayout
					.getLayoutParams();
			lp.setMargins(0, 0, 0, 0);
			mainLayout.setLayoutParams(lp);
		} else {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) searchTitle
					.getLayoutParams();
			lp.setMargins(0, 0, 0, 0);
			searchTitle.setLayoutParams(lp);
			LinearLayout.LayoutParams lp1 = (LayoutParams) mainLayout
					.getLayoutParams();
			// lp1.setMargins(0,
			// getResources().getDimensionPixelSize(R.dimen.title_height),
			// 0, 0);
			mainLayout.setLayoutParams(lp1);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (mIsTitleHide) {
			searchTitle.setVisibility(View.GONE);
		} else {

		}
		mIsAnim = false;

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	//列表点击事件
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//根据类型来判断是否处理
		Talk_repair_Bean b = beans.get(position-1);
		Intent it = null;
		if(Talk_repair_Bean.unfinish.equals(b.getStatus())){
			
			it = new Intent(this,RepairInfo.class);
			it.putExtra(RepairInfo.EXTRAOBJ, b);
		}else{

			it = new Intent(this,RepairInfoDone.class);
			it.putExtra(RepairInfoDone.EXTRAOBJ, b);
		}
		startActivity(it);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}

	//单选按钮的切换事件
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == R.id.btn1) {
			offsetintlocal = 0;
			type = unfinish;
			getlistData(unfinish);
		} else if (checkedId == R.id.btn2) {
			offsetintlocal = 0;
			type = finish;
			getlistData(finish);
		} else if (checkedId == R.id.btn3) {
			offsetintlocal = 0;
			type = processing;
			getlistData(processing);
		} else if (checkedId == R.id.btn4) {
			offsetintlocal = 0;
			type = cancelled;
			getlistData(cancelled);
		}
	}

	// 下拉刷新
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
			String url = RequestURL.GET_REPAIR_LIST + "?"
					+ Login_Bean.USER_SUBDISTRICTADDRESSID + "="
					+ LoginActivity.getUser().getSubdistrict_address_id() + sug
					+ BasicMember.STATUS + "=" + type + sug
					+ BasicMember.OFFSET + "=" + offsetint + sug
					+ BasicMember.LIMITE + "=" + limite;

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

	// 上啦刷新
	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
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
		params.put(BasicMember.OFFSET, String.valueOf((offsetintlocal += limite)));
		params.put(BasicMember.LIMITE, limite + "");
		params.put(BasicMember.STATUS, type + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());

		AppApplication.getHttpClient().get(RequestURL.GET_REPAIR_LIST, params,
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
