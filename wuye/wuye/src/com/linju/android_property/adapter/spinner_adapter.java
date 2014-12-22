package com.linju.android_property.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.GetEmployeeBean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.entity.UndEntity;
import com.linju.android_property.utils.ImageOptions;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

/**
 * 维修管理的gridview适配器
 * 
 * 
 * @blog 
 * 
 * @author lt
 *
 */
public class spinner_adapter extends BaseAdapter{

	List<GetEmployeeBean> data;
	private LayoutInflater mInflater; 
	
	public spinner_adapter(Context context ,List<GetEmployeeBean> data) {
		super();
		mInflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.itme, null);
		}
		TextView title = ViewHolder.GetChars(convertView, android.R.id.text1);
		title.setText(data.get(position).getName());
		return convertView;
	}




}
