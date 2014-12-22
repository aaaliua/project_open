package com.dazhongcun.merchants.activity;

import java.util.ArrayList;
import java.util.List;

import com.dazhongcun.baseactivity.BaseTabActivity;
import com.dazhongcun.entity.TabItem;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.fragment.MyFragment;
import com.dazhongcun.merchants.fragment.TaskFragment;
import com.dazhongcun.merchants.fragment.TestTaskFragment;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainTabActivity extends BaseTabActivity {

	private List<TabItem> mItems; // 保存tabitem的个数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		// 这里会执行重写的继承类的方法：prepare(),initTabSpec()
		// 设置首次进入默认显示的第一个tab
		setCurrentTab(0);
		
		
	}

	@Override
	protected void prepare() {
		mItems = new ArrayList<TabItem>();
		


		// 任务管理的tab
		TabItem tasktab = new TabItem(getResources().getString(
				R.string.make_fragment_title), // title
				R.drawable.selector_mood_make, // icon
				R.drawable.bottom_transparent_light_holo, // background
				TaskFragment.class, // fragment
				null, false);
		mItems.add(tasktab);
		TabItem tasktab2 = new TabItem(getResources().getString(
				R.string.my_fragment_title), // title
				R.drawable.selector_mood_my, // icon
				R.drawable.bottom_transparent_light_holo, // background
				MyFragment.class, // fragment
				null, false);
		mItems.add(tasktab2);
//		TabItem tasktab3 = new TabItem(getResources().getString(
//				R.string.my_fragment_title), // title
//				R.drawable.selector_mood_my, // icon
//				R.drawable.bottom_transparent_light_holo, // background
//				TestTaskFragment.class, // fragment
//				null, false);
//		mItems.add(tasktab3);
//		TabItem tasktab3 = new TabItem(getResources().getString(
//				R.string.make_fragment_title), // title
//				R.drawable.selector_mood_make, // icon
//				R.drawable.bottom_transparent_light_holo, // background
//				TaskFragment.class, // fragment
//				null, false);
//		mItems.add(tasktab3);
//		TabItem tasktab4 = new TabItem(getResources().getString(
//				R.string.my_fragment_title), // title
//				R.drawable.selector_mood_my, // icon
//				R.drawable.bottom_transparent_light_holo, // background
//				TaskFragment.class, // fragment
//				null, false);
//		mItems.add(tasktab4);

	}

	@Override
	protected int getCenterPosition() {
		return 100;
	}

	@Override
	protected int getTabsBg() {
		return 0;
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}

	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		// 可以再图标上做些角标的设置
		// TODO Auto-generated method stub
		textView.setPadding(3, 3, 3, 2);
		textView.setTextSize(10f);
		textView.setText(mItems.get(position).getTitle());
		// textView.setBackgroundResource(mItems.get(position).getBg());
		// textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems
		// .get(position).getIcon(), 0, 0);
		// if ("zh".equals(AppApplication.language)) {
		// if(position == 2){
		// showBadge(textView, position);
		// }
		// }else{
		// if(position == 1){
		// showBadge(textView, position);
		// }
		// }
	}

	@Override
	protected String getTabItemId(int position) {
		return String.valueOf(position);
	}

	@Override
	protected boolean getTabIsGone(int position) {
		return mItems.get(position).isGone();
	}

	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}

	@Override
	protected Class<?> getTabItemFragment(int position) {
		return mItems.get(position).getFragment();
	}

	@Override
	protected Bundle getTabItemBundle(int position) {
		return mItems.get(position).getBunder();
	}

	@Override
	protected void setTabItemicon(ImageView imgView, int position) {
		imgView.setImageResource(mItems.get(position).getIcon());
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);		
		setIntent(intent);// 必须要调用这句
	}

	//设置背景
	@Override
	protected void setTabbg(View bgView, int position) {
//		bgView.setBackgroundColor(Color.parseColor("#ff4289"));
//		bgView.setBackgroundResource(mItems.get(position).getBg());  //ff4289
		bgView.setBackgroundResource(R.drawable.list_selected);
	}
	
	
	public static final String TAG = "MainTabActivity";
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
