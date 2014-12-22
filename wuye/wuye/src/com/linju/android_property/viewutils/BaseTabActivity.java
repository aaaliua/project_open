package com.linju.android_property.viewutils;

import java.util.Timer;
import java.util.TimerTask;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseFragmentActivity;
import com.linju.android_property.utils.ScreenUtils;
import com.linju.android_property.viewutils.SystemBarTintManager.SystemBarConfig;
import com.linju.android_property2.R;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.TabSpec;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

public abstract class BaseTabActivity extends BaseFragmentActivity {

	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	private TabWidget mTabWidget;
	// 定义一个布局
	private LayoutInflater mLayoutInflater;

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_host);

		// 实例化布局对象
		mLayoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		mTabWidget = mTabHost.getTabWidget();
		mTabWidget.setDividerDrawable(null);
		mTabWidget.setStripEnabled(false); // need android2.2

		prepare();
		initTabSpec();
		findViewById(R.id.tabsBg).setBackgroundResource(getTabsBg());

	}

	/**
	 * 在初始化界面之前调用
	 */
	abstract protected void prepare();

	/**
	 * 返回要显示到中间的item的position
	 */
	abstract protected int getCenterPosition();

	/**
	 * 初始化选项卡
	 */
	private void initTabSpec() {
		int count = getTabItemCount();
		int filterCount = getFilterCount();
		int w = ScreenUtils.getWidth(this);
		int c = (int) Math.floor(w / 4);
		int o = (int) Math.floor((w - c) / 4);
		int m = w - c - 4 * o;
		int h = ScreenUtils.dip2px(this, 54);
		int h1 = ScreenUtils.dip2px(this, 8);
		int h2 = ScreenUtils.dip2px(this, 72);
		for (int i = 0; i < count; i++) {
			View tabItem = mLayoutInflater.inflate(R.layout.tab_item, null);
			if (i == getCenterPosition()) {
				LayoutParams l = new LinearLayout.LayoutParams(c, h2);
				l.setMargins(0, 0, 0, -1);
				tabItem.setLayoutParams(l);
			} else {
				LayoutParams l;
				// if(i == 4){
				// l = new LinearLayout.LayoutParams(o+m, h);
				// }else{
				l = new LinearLayout.LayoutParams(w / filterCount, h); // tabhost里面的子菜单的高宽
				// }
				l.setMargins(0, 0, 0, 0);
				tabItem.setLayoutParams(l);
			}
			// 设置tab的背景
			LinearLayout view = (LinearLayout) tabItem.findViewById(R.id.tabbg);
			setTabbg(view, i);
			// 设置icon之类的东西
			ImageView tvTabicon = (ImageView) tabItem
					.findViewById(R.id.tab_icon);
			setTabItemicon(tvTabicon, i);
			// set text view
			TextView tvTabItem = (TextView) tabItem
					.findViewById(R.id.tab_item_tv);
			setTabItemTextView(tvTabItem, i);
			// set id
			String tabItemId = getTabItemId(i);
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			if (getTabIsGone(i)) {
				tabItem.setVisibility(View.GONE);
			}
			tabSpec.setIndicator(tabItem);

			// 将Tab按钮添加进Tab选项卡中
			Bundle b = getTabItemBundle(i);
			mTabHost.addTab(tabSpec, getTabItemFragment(i), b);
		}
	}

	private int getFilterCount() {
		int filterCount = 0;
		int size = getTabItemCount();
		for (int gone = 0; gone < size; gone++) {
			if (!getTabIsGone(gone)) {
				filterCount++;
			}
		}
		return filterCount;
	}

	/**
	 * 返回设置所有tabwidget背景的图片资源
	 * 
	 * @return
	 */
	abstract protected int getTabsBg();

	/**
	 * 获取TabItem数量
	 * 
	 * @return
	 */
	abstract protected int getTabItemCount();

	/**
	 * 设置TabItem的图标和标题等
	 * 
	 * @param textView
	 * @param position
	 */
	abstract protected void setTabItemTextView(TextView textView, int position);

	abstract protected void setTabItemicon(ImageView imgView, int position);

	abstract protected void setTabbg(View bgView, int position);

	/**
	 * tab唯一的id
	 * 
	 * @param position
	 * @return
	 */
	abstract protected String getTabItemId(int position);

	/**
	 * tabitem是否隐藏
	 * 
	 * @param position
	 * @return
	 */
	abstract protected boolean getTabIsGone(int position);

	/**
	 * 点击tab时触发的事件
	 * 
	 * @param position
	 * @return
	 */
	abstract protected Intent getTabItemIntent(int position);

	abstract protected Class<?> getTabItemFragment(int position);

	abstract protected Bundle getTabItemBundle(int position);

	protected void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}

	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 点击实体键返回按钮 退出app
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toaster.showOneToast("再按一次退出APP");
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				try {
					// 执行完全退出APP的方法
					myExit();
					// BaseApplication application = (BaseApplication)
					// getApplicationContext();
					// application.getActivityManager().popAllActivityExceptOne(null);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return false;
	}

	/**
	 * 发送广播关闭所有Activity 必须继承BaseActivity
	 */
	protected void myExit() {
		Intent it = new Intent();
		it.setAction(AppApplication.EXIT_APP);
		this.sendBroadcast(it);
		System.exit(0);
		super.finish();
	}

	public FragmentTabHost getmTabHost() {
		return mTabHost;
	}

	public void setmTabHost(FragmentTabHost mTabHost) {
		this.mTabHost = mTabHost;
	}

	public TabWidget getmTabWidget() {
		return mTabWidget;
	}

	public void setmTabWidget(TabWidget mTabWidget) {
		this.mTabWidget = mTabWidget;
	}

}
