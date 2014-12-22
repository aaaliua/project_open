package com.linju.android_property.adapter;

import java.util.zip.Inflater;

import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.LetterImageView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 信息管理的adapter
 * @author LT
 *
 */
public class MailAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private int[] mailList;
	private Context context;
	public MailAdapter(Context context,int[] mailList) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.mailList = mailList;
	}

	@Override
	public int getCount() {
		return mailList.length;
	}

	@Override
	public Object getItem(int position) {
		return mailList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int nameRes =  (Integer) getItem(position);
		String name = context.getResources().getString(nameRes);
		if(convertView == null){
			convertView  = mInflater.inflate(R.layout.list_item_mail, null);
		}
		LetterImageView letterImageView = ViewHolder.GetChars(convertView, R.id.iv_avatar);
		TextView title = ViewHolder.GetChars(convertView, R.id.tv_name);
		letterImageView.setOval(true);
		letterImageView.setLetter(name.charAt(0));
		title.setText(name);
		return convertView;
	}

}
