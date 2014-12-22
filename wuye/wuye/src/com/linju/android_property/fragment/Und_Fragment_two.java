package com.linju.android_property.fragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.linju.android_property.activity.InfoActivity;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.Und_adapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.database.BaseAppDbHelper;
import com.linju.android_property.entity.Content_menu;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.UndEntity;
import com.linju.android_property.personmanager.PositionManager;
import com.linju.android_property.servicemanager.ComplaintList;
import com.linju.android_property.servicemanager.PostList;
import com.linju.android_property.servicemanager.RepairInfo;
import com.linju.android_property.servicemanager.RepairList;
import com.linju.android_property.user.UserInfoActivity;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StringUtils;
import com.linju.android_property.viewutils.FragmentViewPagerAdapter;
import com.linju.android_property.viewutils.PagerSlidingTabStrip;
import com.linju.android_property.viewutils.PagerSlidingTabStrip.IconTabProvider;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;


/**
 * 小区管理的fragmetn
 * @author LT
 *
 */
public class Und_Fragment_two extends BaseFragment implements OnClickListener{
//	private final String[] TITLES = { "客服管理", "服务管理", "业主信息", "人事管理", "资产管理" };
	//标题栏
	@InjectView(R.id.back)
	View back;
	@InjectView(R.id.title)
	WPTextView title;
	@InjectView(R.id.more)
	ImageView info;
	
	@Inject Resources res;
	
	//内容
	@InjectView(R.id.tabs)
	private PagerSlidingTabStrip mTabs;
	@InjectView(R.id.pager)
	private ViewPager mPager;
	public static MyframPagerAdapter adapter;
	List<Fragment> fragments;
	
	private List<Content_menu> lists;
	
	int color = Color.parseColor("#4D99CB");
	private int currentColor = color;  //#FFC74B46
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		back.setVisibility(View.GONE);
		info.setVisibility(View.VISIBLE);
		info.setOnClickListener(this);
		title.setText(getString(R.string.community_fragment_title));
		
		//获取或有模块列表  从raw资源文件中获取对应的所有模块
		String json = StringUtils.GetRaw(res, R.raw.content);
		if(json != null){
			lists = ParseJson.getMenuList(json);
		}
		
		
		//初始化所有的fragment列表
		if(lists != null){
			fragments = new ArrayList<Fragment>();
			for(int i = 0 ;i<lists.size();i++){
				Fragment fm = new ContentFragment(lists.get(i));
				fragments.add(fm);
			}
		}
		
		//使用自定义的fragmentadapter来初始化
		adapter = new MyframPagerAdapter(getActivity().getSupportFragmentManager(),mPager,fragments);
		//为viewpager设置适配器
//		mPager.setAdapter(adapter);
		
		//设置viewpager的间距 
//		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
//				.getDisplayMetrics());
//		mPager.setPageMargin(pageMargin);
		
		//为viewpager设置指示器   使用指示器 一定要实现adapter中的getPageTitle方法
		mTabs.setViewPager(mPager);
		mTabs.setIndicatorColor(getResources().getColor(R.color.action_bar_color));
		mTabs.setIndicatorHeight(5);
		mTabs.setTextColorResource(R.color.software_textColor_unselected);
		mTabs.setDividerColor(getResources().getColor(android.R.color.transparent));
		mTabs.setBackgroundColor(getResources().getColor(R.color.white));
		mTabs.setViewPagerAnimation(true);
		mTabs.setTabPaddingLeftRight((int)getResources().getDimension(R.dimen.tab_padding_LR));
		adapter.notifyDataSetChanged();
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_und_two, null);
		return v;
	}

	
//	public class MyframPagerAdapter extends FragmentPagerAdapter{
//
//		private final String[] TITLES = { "客服管理", "服务管理", "业主信息", "人事管理", "资产管理" };
//		
//		public MyframPagerAdapter(FragmentManager fm) {
//			super(fm);
//		}
//
//		@Override
//		public CharSequence getPageTitle(int position) {
//			return TITLES[position];
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			return new ContentFragment(TITLES[position]); 
//		}
//
//		
//
//		@Override
//		public int getCount() {
//			return TITLES.length;
//		}
//	}
	public class MyframPagerAdapter extends FragmentViewPagerAdapter {

		
		
		public MyframPagerAdapter(FragmentManager fragmentManager,
				ViewPager viewPager, List<Fragment> fragments) {
			super(fragmentManager, viewPager, fragments);
		}

		//为指示器显示标题使用
		@Override
		public CharSequence getPageTitle(int position) {
			return lists.get(position).getContentTitle();
		}
		

	}


	@Override
	public void onClick(View v) {
		if(v.getId() == info.getId()){
			startActivity(new Intent(getActivity(),UserInfoActivity.class));
			getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
		}
	}
	
}
