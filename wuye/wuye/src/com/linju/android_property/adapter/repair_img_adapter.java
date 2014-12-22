package com.linju.android_property.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.ImageBean;
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
public class repair_img_adapter extends BaseAdapter{

	List<ImageBean> data;
	private LayoutInflater mInflater; 
	
	public repair_img_adapter(Context context ,List<ImageBean> data) {
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
			convertView  = mInflater.inflate(R.layout.repair_img_item, null);
		}
		ImageView imgView = ViewHolder.GetChars(convertView, R.id.img);
		if(data.size() > 0 ){
			
			AppApplication.getImageLoader().displayImage(RequestURL.mDomaintest + data.get(position).getImage(), imgView,ImageOptions.options);
		}else{
			AppApplication.getImageLoader().displayImage(null, imgView,ImageOptions.options);
		}
		return convertView;
	}




}
