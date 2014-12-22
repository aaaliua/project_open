package com.herotculb.tencentmap;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class AdddressAdapter extends BaseAdapter{
	private List<Pois> comments;
	private LayoutInflater inflater;
//	private AsyncImageLoader loader;
	private Context mContext;
	private FinalBitmap finalBitmap;
	
	public AdddressAdapter(Context context, List<Pois> comments) {
		this.setSellerlistInfos(comments);
		this.mContext=context;
		this.inflater = LayoutInflater.from(mContext);
		finalBitmap = FinalBitmap.create(mContext);
		
	}
	public AdddressAdapter(Handler handler, List<Pois> list,
			ListView listView) {
		this.setSellerlistInfos(comments);
	}

	private void setSellerlistInfos(
			List<Pois> comments) {
		if (comments!=null) {
			this.comments=comments;
		} else {
			this.comments = new ArrayList<Pois>();
		}
	}
	public void changeData(List<Pois> objectTeachers) {
		this.setSellerlistInfos(objectTeachers);
		this.notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Pois getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return comments.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Pois comment = getItem(position);
		String addressStr = comment.getAddress();
		String titleStr = comment.getTitle();
		String juliStr = comment.get_distance();
		
		
		
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_address, null);
			holder = new ViewHolder();
			holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
			holder.text_juli = (TextView) convertView.findViewById(R.id.text_juli);
			holder.text_address = (TextView) convertView.findViewById(R.id.text_address);
			
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(addressStr)) {
			holder.text_address.setText(addressStr);
		}

		if (!TextUtils.isEmpty(titleStr)) {
			holder.text_title.setText(titleStr);
		}
		if (!TextUtils.isEmpty(juliStr)) {
			holder.text_juli.setText(juliStr+"m");
		}
		return convertView;
	}
	

	class ViewHolder{
		TextView text_title,text_juli,text_address;
	}
}
