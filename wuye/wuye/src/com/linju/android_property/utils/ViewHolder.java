package com.linju.android_property.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * adapter中的ViewHolder   例：SmartImageView img = ViewHolder.GetChars(convertView, R.id.img);
 * @author Administrator
 *
 */
public class ViewHolder {

	@SuppressWarnings("unchecked")
	public static <T extends View> T GetChars(View view,int id){
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if(viewHolder == null){
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if(childView == null){
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		
		return (T)childView;
			
	}
}
