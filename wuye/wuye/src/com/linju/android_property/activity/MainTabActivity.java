package com.linju.android_property.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.linju.android_property.entity.TabItem;
import com.linju.android_property.fragment.MaterialTaskFragment;
import com.linju.android_property.fragment.MsgFragment;
import com.linju.android_property.fragment.TaskFragment;
import com.linju.android_property.fragment.TopMsgFragment;
import com.linju.android_property.fragment.Und_Fragment_two;
import com.linju.android_property.viewutils.BaseTabActivity;
import com.linju.android_property2.R;

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
		// 小区管理的tab
		TabItem undtab = new TabItem(getResources().getString(
				R.string.community_fragment_title), // title
				R.drawable.selector_mood_home, // icon
				R.drawable.bottom_transparent_light_holo, // background
				Und_Fragment_two.class, // fragment
				null, false);
		mItems.add(undtab);

		// 消息管理的tab
		TabItem messagetab = new TabItem(getResources().getString(
				R.string.message_fragment_title), // title
				R.drawable.selector_mood_message, // icon
				R.drawable.bottom_transparent_light_holo, // background
				TopMsgFragment.class, // fragment
				null, false);
		mItems.add(messagetab);

		// 任务管理的tab
		TabItem tasktab = new TabItem(getResources().getString(
				R.string.task_fragment_title), // title
				R.drawable.selector_mood_task, // icon
				R.drawable.bottom_transparent_light_holo, // background
				TaskFragment.class, // fragment
				null, false);
		mItems.add(tasktab);
		// 测试tab
		TabItem testtab = new TabItem(getResources().getString(
				R.string.task_fragment_title), // title
				R.drawable.selector_mood_message, // icon
				R.drawable.bottom_transparent_light_holo, // background
				MaterialTaskFragment.class, // fragment
				null, false);
		mItems.add(testtab);

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
		textView.setPadding(3, 3, 3, 9);
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
	protected void setTabbg(View bgView, int position) {
		bgView.setBackgroundResource(mItems.get(position).getBg());
	}
	
}
