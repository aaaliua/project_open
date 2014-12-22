package com.linju.android_property.activity;

import java.io.File;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.linju.android_property2.R;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.utils.ImageOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.os.Build;

@SuppressLint("NewApi")
public class MainActivitytest extends ActionBarActivity {

	public static boolean b = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			View rotabtn = rootView.findViewById(R.id.rotabtn);
			rotabtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					float f = 45f;
					if(b){
						b = false;
						//使用动画让组件动起来  使用方法请看官方API或者百度
						ValueAnimator an = ObjectAnimator.ofFloat(v, View.ROTATION, 0f,f);
						an.setDuration(600);
						an.start();
						
					}else{
						b = true;
						
						ValueAnimator an = ObjectAnimator.ofFloat(v, View.ROTATION, f,0f);
						an.setDuration(600);
						an.start();
						
					}
				}
			});
			
			final View v1 = rootView.findViewById(R.id.test1);
			final View v2 = rootView.findViewById(R.id.test2);
			final View v3 = rootView.findViewById(R.id.test3);
			final View v4 = rootView.findViewById(R.id.test4);
			final View v5 = rootView.findViewById(R.id.test5);
			final View v6 = rootView.findViewById(R.id.test6);
//			
			YoYo.with(Techniques.BounceInUp).duration(800).playOn(v1);
			YoYo.with(Techniques.BounceInUp).duration(900).playOn(v2);
			YoYo.with(Techniques.BounceInUp).duration(1000).playOn(v3);
			YoYo.with(Techniques.BounceInUp).duration(1100).playOn(v4);
			YoYo.with(Techniques.BounceInUp).duration(1200).playOn(v5);
			YoYo.with(Techniques.BounceInUp).duration(1300).playOn(v6);
			
			ImageView image = (ImageView)rootView.findViewById(R.id.image);
			String uri = "http://wenwen.soso.com/p/20110416/20110416104425-1723811127.jpg";
			AppApplication.getImageLoader().displayImage(uri, image,ImageOptions.defaultOptions);
			final RelativeLayout layout = (RelativeLayout)rootView.findViewById(R.id.url_button);
			Button but = (Button)rootView.findViewById(R.id.flot_but);
			
			
			but.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(layout.getVisibility() == View.VISIBLE){
						layout.setVisibility(View.GONE);
						YoYo.with(Techniques.FadeOutDown).duration(900).playOn(v1);
						YoYo.with(Techniques.FadeOutDown).duration(800).playOn(v2);
						YoYo.with(Techniques.FadeOutDown).duration(700).playOn(v3);
						YoYo.with(Techniques.FadeOutDown).duration(600).playOn(v4);
						YoYo.with(Techniques.FadeOutDown).duration(500).playOn(v5);
						YoYo.with(Techniques.FadeOutDown).duration(400).playOn(v6);
					}else{
						layout.setVisibility(View.VISIBLE);
						YoYo.with(Techniques.BounceInUp).duration(600).playOn(v1);
						YoYo.with(Techniques.BounceInUp).duration(700).playOn(v2);
						YoYo.with(Techniques.BounceInUp).duration(800).playOn(v3);
						YoYo.with(Techniques.BounceInUp).duration(900).playOn(v4);
						YoYo.with(Techniques.BounceInUp).duration(1000).playOn(v5);
						YoYo.with(Techniques.BounceInUp).duration(1100).playOn(v6);
					}
					
				
				}
			});
			return rootView;
		}
	}

}
