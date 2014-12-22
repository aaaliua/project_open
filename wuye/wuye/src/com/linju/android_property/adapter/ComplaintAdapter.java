package com.linju.android_property.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.utils.ImageOptions;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.LetterImageView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 信息管理的adapter
 * @author LT
 *
 */
public class ComplaintAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<ComplaintBean> lists;
	private Context context;
	public ComplaintAdapter(Context context,List<ComplaintBean> lists) {
		super();
		this.context = context;
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
		ComplaintBean bean = lists.get(position);
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_complaint, null);
		}
		TextView content = ViewHolder.GetChars(convertView, R.id.title);  
		TextView postTitle = ViewHolder.GetChars(convertView, R.id.post_title);
		TextView date = ViewHolder.GetChars(convertView, R.id.date);
		TextView status = ViewHolder.GetChars(convertView, R.id.status);
		
		status.setText(bean.getStatus());
		status.setBackgroundResource(ComplaintBean.STATUE_DONE.equals(bean.getStatus())?R.drawable.rect_pressed_list:R.drawable.rect_blue_pressed_list);
		content.setText(bean.getContent());
		date.setText(bean.getCreated_at());
		postTitle.setText(bean.getTitle());
		return convertView;
	}

}
