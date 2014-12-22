package com.linju.android_property.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linju.android_property.application.AppApplication;
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
public class RepairAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<Talk_repair_Bean> lists;
	private Context context;
	public RepairAdapter(Context context,List<Talk_repair_Bean> lists) {
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
		
		Talk_repair_Bean bean = lists.get(position);
//		View view = convertView;
//		final ViewHolder holder;
//		
//		if (convertView == null) {
//			view = mInflater.inflate(R.layout.list_item_repair, parent, false);
//			holder = new ViewHolder();
//			holder.img = (ImageView) view.findViewById(R.id.img);
//			holder.title = (TextView) view.findViewById(R.id.title);
//			holder.desc = (TextView) view.findViewById(R.id.desc);
//			holder.hint = (TextView) view.findViewById(R.id.hint);
//			view.setTag(holder);
//		} else {
//			holder = (ViewHolder) view.getTag();
//		}
//		
//		holder.title.setText(bean.getTitle());
//		holder.desc.setText(bean.getUser_name());
//		holder.hint.setText(bean.getCreated_at());
//		
//		if(bean.getImages().size() >0){
//			final String url = RequestURL.mDomaintest+bean.getImages().get(0).getImage_url();
//			AppApplication.getImageLoader().displayImage(url, holder.img,ImageOptions.options);
//		}else{
//			AppApplication.getImageLoader().displayImage(null, holder.img,ImageOptions.options);
//		}
		
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_repair, null);
		}
		ImageView img = ViewHolder.GetChars(convertView, R.id.img);
		TextView title = ViewHolder.GetChars(convertView, R.id.title);
		TextView desc = ViewHolder.GetChars(convertView, R.id.desc);
		TextView hint = ViewHolder.GetChars(convertView, R.id.hint);
		
		title.setText(bean.getTitle());
		desc.setText(bean.getUser_name());
		hint.setText(bean.getCreated_at());
		if(bean.getImages().size() >0){
			final String url = RequestURL.mDomaintest+bean.getImages().get(0).getImage();
			AppApplication.getImageLoader().displayImage(url, img,ImageOptions.defaultOptions);
		}else{
			//防止图片错位  一定要设置没有图片的那个组件的url为空，不然imageloader不会去匹配对象了 ，所以导致图片复用错位
			AppApplication.getImageLoader().displayImage(null, img,ImageOptions.defaultOptions);
		}
		
		return convertView;
	}

	
}
