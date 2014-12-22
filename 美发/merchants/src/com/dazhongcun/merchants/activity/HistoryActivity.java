package com.dazhongcun.merchants.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.dazhongcun.baseactivity.BaseFragmentActivity;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.fragment.HistoryTaskFragment;
import com.dazhongcun.merchants.fragment.TaskFragment;
import com.dazhongcun.merchants.fragment.TestTaskFragment;
import com.dazhongcun.widget.FragmentViewPagerAdapter;
import com.dazhongcun.widget.PagerSlidingTabStrip;
import com.umeng.analytics.MobclickAgent;

//历史记录列表 
public class HistoryActivity extends BaseFragmentActivity implements
		OnClickListener {

	public static final String TAG = "HistoryActivity";
	
	@InjectView(R.id.backbtn)
	View backbtn;

	@InjectView(R.id.sliding_tabs)
	private PagerSlidingTabStrip mSlidingTabLayout;
	@InjectView(R.id.viewpager)
	private ViewPager mViewPager;

	final String[] TITLES = { "全部", "已完成", "已取消" };
	List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_activity);

		backbtn.setOnClickListener(this);

		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < TITLES.length; i++) {
			Fragment fm = null;
			if (i == 0) {

				fm = new HistoryTaskFragment(HistoryTaskFragment.ALL);
			} else if (i == 1) {
				fm = new HistoryTaskFragment(HistoryTaskFragment.DONE);
			} else if (i == 2) {
				fm = new HistoryTaskFragment(HistoryTaskFragment.CANCEL);
			}
			fragments.add(fm);
		}

		mViewPager.setAdapter(new MyframPagerAdapter(
				getSupportFragmentManager(), mViewPager, fragments));

		mSlidingTabLayout.setViewPager(mViewPager);
		mSlidingTabLayout.setIndicatorColor(getResources().getColor(
				R.color.merchants_color));
		mSlidingTabLayout.setDividerColor(getResources()
				.getColor(R.color.white));
		mSlidingTabLayout.setIndicatorHeight(12);
		mSlidingTabLayout.setTextColorResource(R.color.merchants_color);
		mSlidingTabLayout.setTextFocusColorResource(R.color.black);
		mSlidingTabLayout.setUnderlineColorResource(R.color.divide_line);

	}

	// viewpag适配器
	public class MyframPagerAdapter extends FragmentViewPagerAdapter {

		public MyframPagerAdapter(FragmentManager fragmentManager,
				ViewPager viewPager, List<Fragment> fragments) {
			super(fragmentManager, viewPager, fragments);
		}

		// 为指示器显示标题使用
		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == backbtn.getId()) {
			onBackPressed();
		}
	}

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
