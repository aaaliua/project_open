package com.linju.android_property.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.linju.android_property.application.AppApplication;
import com.linju.android_property.entity.Building_info_Bean;
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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 信息管理的adapter
 * @author LT
 *
 */
public class BuildingAdapter extends BaseAdapter{
	static final int ANIMATION_DURATION = 200;
	private LayoutInflater mInflater;
	private List<Building_info_Bean> lists;
	private Context context;
	public BuildingAdapter(Context context,List<Building_info_Bean> lists) {
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

	
	public void deleteCell(final View v, final int index) {
		AnimationListener al = new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				lists.remove(index);

//				ViewHolder vh = (ViewHolder)v.getTag();
//				vh.needInflate = true;

				notifyDataSetChanged();
			}
			@Override public void onAnimationRepeat(Animation animation) {}
			@Override public void onAnimationStart(Animation animation) {}
		};

		collapse(v, al);
	}
	private void collapse(final View v, AnimationListener al) {
		final int initialHeight = v.getMeasuredHeight();

		Animation anim = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 0) {
//					v.setVisibility(View.GONE);
				}
				else {
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (al!=null) {
			anim.setAnimationListener(al);
		}
		anim.setDuration(ANIMATION_DURATION);
		v.startAnimation(anim);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Building_info_Bean bean = lists.get(position);
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_building, null);
		}
		TextView building_number = ViewHolder.GetChars(convertView, R.id.building_number);  
		TextView type = ViewHolder.GetChars(convertView, R.id.type);
		TextView number = ViewHolder.GetChars(convertView, R.id.number);
		TextView desc = ViewHolder.GetChars(convertView, R.id.desc);
		
		building_number.setText(bean.getName());
		type.setText(bean.getHouse_klass());
		number.setText(bean.getHouse_count());
		desc.setText(bean.getDescription());
		return convertView;
	}

}
