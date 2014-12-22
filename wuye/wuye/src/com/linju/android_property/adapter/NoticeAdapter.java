package com.linju.android_property.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.Notice_Manage_Bean;
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
public class NoticeAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Notice_Manage_Bean> lists;
	private Context context;
	public NoticeAdapter(Context context,List<Notice_Manage_Bean> lists) {
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
		Notice_Manage_Bean bean = lists.get(position);
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_noticet, null);
		}
		TextView title = ViewHolder.GetChars(convertView, R.id.title);
		TextView date = ViewHolder.GetChars(convertView, R.id.date);
		title.setText(bean.getTitle());
		date.setText(bean.getUpdated_at());
		return convertView;
	}

}
