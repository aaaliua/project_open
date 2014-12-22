package com.dazhongcun.merchants.adapter;

import java.util.List;

import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.utils.ViewHolder;
import com.dazhongcun.widget.FancyButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryListAdapter extends BaseAdapter {
	private List<MakeEntity> datas;
	private Context mContext;
	private LayoutInflater mInflater; 
	
	public HistoryListAdapter(Context mContext,List<MakeEntity> datas) {
		super();
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
		this.datas = datas;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//R.layout.history_list_item
		
		MakeEntity ent = datas.get(position);
		if(convertView == null){  
			convertView  = mInflater.inflate(R.layout.history_list_item, null);
		}
		
		FancyButton makeID = ViewHolder.GetChars(convertView,R.id.makeID);
		FancyButton status = ViewHolder.GetChars(convertView,R.id.status);
         makeID.setEnabled(false);
         TextView makeMan = ViewHolder.GetChars(convertView,R.id.makeMan);
          TextView makeType = ViewHolder.GetChars(convertView,R.id.makeType);
          TextView makePhone = ViewHolder.GetChars(convertView,R.id.makePhone);
          TextView makeName = ViewHolder.GetChars(convertView,R.id.makeName);
          TextView makeTime = ViewHolder.GetChars(convertView,R.id.makeTime);
          TextView makeDate = ViewHolder.GetChars(convertView,R.id.makedate);
          TextView makeCreateDate = ViewHolder.GetChars(convertView,R.id.makeCreateDate);
		
          
      	makeID.setText(String.format(mContext.getResources().getString(R.string.makeID), ent.getId()));
    	makeMan.setText(ent.getUserName());
    	makeType.setText(ent.getOutType());
    	makePhone.setText(ent.getMobile());
    	makeName.setText(ent.getStyListName());
    	makeTime.setText(ent.getOutTime());
    	makeDate.setText(ent.getOutdate());
    	makeCreateDate.setText(ent.getCreateDate());
    	status.setText(ent.getStatusName());
		
		return convertView;    
	}

}
