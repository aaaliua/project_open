package com.linju.android_property.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.linju.android_property.entity.UndEntity;
import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.readystatesoftware.viewbadger.BadgeView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

/**
 * StickyHeaderGridView的适配器，除了要继承BaseAdapter之外还需要
 * 实现StickyGridHeadersSimpleAdapter接口
 * 
 * @blog 
 * 
 * @author lt
 *
 */
public class Und_simple_adapter extends BaseAdapter{

	public static boolean flag = true;
	
	private List<UndEntity> lists;
	private LayoutInflater mInflater; 
	private Context mContext;
	public Und_simple_adapter(Context context,List<UndEntity> lists) {
		super();
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.gridview_item, null);
		}
		RelativeLayout childtop = ViewHolder.GetChars(convertView, R.id.childtop);
		ImageView imgView = ViewHolder.GetChars(convertView, R.id.childicon);
		WPTextView titleView = ViewHolder.GetChars(convertView, R.id.childtitle);
		String title = lists.get(position).getChildtitle();
		int icon = lists.get(position).getChildIcon();
		titleView.setText(title);
		imgView.setBackgroundResource(icon);
			if(position == 1 || position == 3){
				BadgeView badge = new BadgeView(mContext,childtop);
//				badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//				badge.setBadgeMargin(0, 0);
//				badge.setBackgroundResource(R.drawable.badge_ifaux);
				badge.setBadgeBackgroundColor(Color.parseColor("#DC4733"));
				badge.setText("8");
				badge.setTextSize(12);
				badge.show();
				
//				imgView.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
//				        anim.setInterpolator(new BounceInterpolator());
//				        anim.setDuration(1000);
//				        badge.toggle(anim, null);
//					}
//				});
			}
	
		return convertView;
	}


}
