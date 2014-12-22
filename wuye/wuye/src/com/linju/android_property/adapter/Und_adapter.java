package com.linju.android_property.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.linju.android_property.entity.UndEntity;
import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
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
public class Und_adapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter{

	
	private List<UndEntity> lists;
	private LayoutInflater mInflater; 
	public Und_adapter(Context context,List<UndEntity> lists) {
		super();
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
		ImageView imgView = ViewHolder.GetChars(convertView, R.id.childicon);
		WPTextView titleView = ViewHolder.GetChars(convertView, R.id.childtitle);
		String title = lists.get(position).getChildtitle();
		int icon = lists.get(position).getChildIcon();
		titleView.setText(title);
		imgView.setBackgroundResource(icon);
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return lists.get(position).getHeaderId();
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.gridhead_item, null);
		}
		ImageView headimg = ViewHolder.GetChars(convertView, R.id.headicon);
		WPTextView headtitle = ViewHolder.GetChars(convertView, R.id.headtitle);
		String title = lists.get(position).getTitle();
		int icon = lists.get(position).getTitleIcon();
		headimg.setImageResource(icon);
		headtitle.setText(title);
		return convertView;
	}


}
