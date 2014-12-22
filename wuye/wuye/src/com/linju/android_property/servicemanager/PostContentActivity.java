package com.linju.android_property.servicemanager;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.Notice_Manage_Bean;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

@SuppressLint("NewApi")
public class PostContentActivity extends BaseActivity implements OnClickListener{

	
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;
	
	public static final String EXTRA_OBJ = "extra:obj";
	
	@InjectExtra(value = EXTRA_OBJ,optional = true)
	Notice_Manage_Bean bean;
	
	
	@InjectView(R.id.post_title)
	TextView postTitle;
	@InjectView(R.id.date)
	TextView date;
	@InjectView(R.id.Post_content)
	WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_post_content);
		back.setText(getString(R.string.gonggao));
		back.setOnClickListener(this);
		titlebar.setText(getString(R.string.post_content_title));
		
		//webview的各种设置
		// 优先使用缓存：
		mWebView.getSettings()
				.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setDisplayZoomControls(true);
//		mWebView.getSettings().setSupportZoom(true); 
//		mWebView.getSettings().setUseWideViewPort(true); 
//		mWebView.getSettings().setLoadWithOverviewMode(true); 
		
//		int screenDensity = getResources().getDisplayMetrics().densityDpi ;   
//		WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;   
//		switch (screenDensity){   
//		case DisplayMetrics.DENSITY_LOW :  
//		    zoomDensity = WebSettings.ZoomDensity.CLOSE;  
//		    break;  
//		case DisplayMetrics.DENSITY_MEDIUM:  
//		    zoomDensity = WebSettings.ZoomDensity.MEDIUM;  
//		    break;  
//		case DisplayMetrics.DENSITY_HIGH:  
//		    zoomDensity = WebSettings.ZoomDensity.FAR;  
//		    break ;  
//		}  
//		mWebView.getSettings().setDefaultZoom(zoomDensity); 
		
		
		if(bean != null){
			date.setText(bean.getUpdated_at());
			postTitle.setText(bean.getTitle());
			String content = bean.getContent().replace("<img", "<img width=100%");
			mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
		}
	}


	@Override
	public void onClick(View v) {
		if(v.getId() == back.getId()){
			onBackPressed();
		}
	}

	
}
