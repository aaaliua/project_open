package com.linju.android_property.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.Property_Fee_Bean;
import com.linju.android_property.entity.Property_fee_Histroy_Bean;
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
public class PropertyHistoryAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Property_fee_Histroy_Bean> lists;
	private Context context;
	public PropertyHistoryAdapter(Context context,List<Property_fee_Histroy_Bean> lists) {
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
		
		Property_fee_Histroy_Bean bean = lists.get(position);
		
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_history_property, null);
		}
		WPTextView date = ViewHolder.GetChars(convertView, R.id.date);
		WPTextView money = ViewHolder.GetChars(convertView, R.id.money);
		WPTextView type = ViewHolder.GetChars(convertView, R.id.type);
		
		date.setText(bean.getData());
		money.setText(bean.getMoney());
		type.setText(bean.getPayment_type_name());
		
		
		return convertView;
	}

	
}
