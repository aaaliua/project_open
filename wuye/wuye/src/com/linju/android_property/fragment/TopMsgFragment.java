package com.linju.android_property.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linju.android_property.adapter.MailAdapter;
import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.dialog.Effectstype;
import com.linju.android_property.dialog.MaterialDialog;
import com.linju.android_property.message.fragment.InBoxFragment;
import com.linju.android_property.message.fragment.SendBoxFragment;
import com.linju.android_property.viewutils.SystemBarTintManager;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property.viewutils.SystemBarTintManager.SystemBarConfig;
import com.linju.android_property2.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;


/**
 * 信息管理的fragmetn
 * @author LT
 *
 */
@SuppressLint("NewApi")
public class TopMsgFragment extends BaseFragment implements OnClickListener{
	
	private static final int PAGE_NEWS = 0;
	private static final int PAGE_REPLY = 1;
	
	@InjectView(R.id.btn_in)
	Button in;					//收件箱 
	@InjectView(R.id.btn_send)
	Button send;				//发件箱
	@InjectView(R.id.news_pager)
	ViewPager mViewPager;		//装载fragment
	@InjectView(R.id.actionbar)
	RelativeLayout actionbar;
	List<Fragment> views;
	
	@InjectView(R.id.btn_write_msg)
	View write;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		in.setBackgroundResource(R.drawable.left_top_btn_press);
		initView();
		setCurPage(PAGE_NEWS);
		
//		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//	        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
//	        tintManager.setStatusBarTintEnabled(true);
//	        tintManager.setStatusBarTintResource(R.color.action_bar_color);
//	        SystemBarConfig config = tintManager.getConfig();
//	        actionbar.setPadding(0, config.getPixelInsetTop(true), 0, 0);
//		}
	}
	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getActivity().getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_news_fragment, null);
	}

	
	
	private void initView() {
		views = new ArrayList<Fragment>();
		InBoxFragment inbox = new InBoxFragment();
		
		SendBoxFragment sendbox = new SendBoxFragment();
		views.add(inbox);
		views.add(sendbox);
		
		mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				setCurPage(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		in.setOnClickListener(this);
		send.setOnClickListener(this);
		write.setOnClickListener(this);
	}
	
	
	
	public void setCurPage(int pageIndex) {
		clearBtnSelectionState();
		switch (pageIndex) {
		case PAGE_NEWS:
			in.setBackgroundResource(R.drawable.left_top_btn_press);
			break;
		case PAGE_REPLY:
			send.setBackgroundResource(R.drawable.right_top_btn_press);
			break;
		
		}
	}
	
	public void clearBtnSelectionState() {
		in.setBackgroundResource(R.drawable.left_btn_selector);
		send.setBackgroundResource(R.drawable.right_btn_selector);
	}

	
	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return views.get(arg0);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		
		
	}
	@Override
	public void onClick(View v) {
		if (v == in) {
			setCurPage(PAGE_NEWS);
			mViewPager.setCurrentItem(PAGE_NEWS, true);
		} else if (v == send) {
			setCurPage(PAGE_REPLY);
			mViewPager.setCurrentItem(PAGE_REPLY, true);
		} else if(v == write){
			
			
//			final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
//			dlg.setCancelable(false);
//			dlg.show();
//			Window window = dlg.getWindow();
//			window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//			// *** 主要就是在这里实现这种效果的.
//			 // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
//			window.setContentView(R.layout.view_alertdialog_edit);
////			window.setGravity(Gravity.TOP);
//			Button but = (Button)window.findViewById(R.id.flot_but);
//			but.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					dlg.cancel();
//				}
//			});
			
			
			
			new com.linju.android_property.dialog.AlertDialog(getActivity()).builder().setTitle("MaterialDialog")
			.setMsg("嗨！这是一个 MaterialDialog. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。" +
                    "它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^")
			.withDuration(400)
			.withEffect(Effectstype.Zoomin)
			.withOutEffect(Effectstype.ZoomOut)
			.setCancelable(false)
			.setPositiveButton("确认使用", new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			}).setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			}).show();
			
			
//			MaterialDialog mMaterialDialog;
//			 mMaterialDialog = new MaterialDialog(getActivity());
//		        mMaterialDialog.setTitle("MaterialDialog");
//		        mMaterialDialog.setMessage("Hi! This is a MaterialDialog. It's very easy to use, you just new it, " +
//		                "then the beautiful AlertDialog will show automatedly. It is artistic, conforms to Google Material Design." +
//		                " I hope that you can like it, enjoys it. ^ ^");
//		        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
//		            @Override
//		            public void onClick(View v) {
//		                mMaterialDialog.dismiss();
//		                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_LONG).show();
//
//		            }
//		        });
//
//		        mMaterialDialog.setNegativeButton("CANCLE", new View.OnClickListener() {
//		            @Override
//		            public void onClick(View v) {
//		                mMaterialDialog.dismiss();
//		                Toast.makeText(getActivity(), "Cancle", Toast.LENGTH_LONG).show();
//		            }
//		        });
//		        mMaterialDialog.show();
//		    }
			
		}
	}
	
	
}
	

