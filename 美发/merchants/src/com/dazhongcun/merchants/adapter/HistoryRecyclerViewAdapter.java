package com.dazhongcun.merchants.adapter;


import java.util.List;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.widget.FancyButton;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private final List<MakeEntity> datas;
    public final  Activity ac;

    public HistoryRecyclerViewAdapter(Context context, List<MakeEntity> datas,Activity ac) {
    	mContext = context;
    	this.datas = datas;
    	this.ac = ac;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    	View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
//    	LayoutInflater.from(parent.getContext()).
        return new ViewHolder(mContext,view,ac);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        String[] values = mDataset[position].split(",");
//        String countryName = values[0];
//        int flagResId = mContext.getResources().getIdentifier(values[1], "drawable", mContext.getPackageName());
//        viewHolder.mTextView.setText(countryName);
//        viewHolder.mTextView.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);
    	MakeEntity ent = datas.get(position);
    	viewHolder.makeID.setText(String.format(mContext.getResources().getString(R.string.makeID), ent.getId()));
    	viewHolder.makeMan.setText(ent.getUserName());
    	viewHolder.makeType.setText(ent.getOutType());
    	viewHolder.makePhone.setText(ent.getMobile());
    	viewHolder.makeName.setText(ent.getStyListName());
    	viewHolder.makeTime.setText(ent.getOutTime());
    	viewHolder.makeDate.setText(ent.getOutdate());
    	viewHolder.makeCreateDate.setText(ent.getCreateDate());
    	
    	
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mTextView;
        private final Context mContext;
        
        private final Activity ac;
        
        
        
        public FancyButton makeID;
        public TextView makeMan;
        public TextView makeType;
        public TextView makePhone;
        public TextView makeName;
        public TextView makeTime;
        public TextView makeDate;
        public TextView makeCreateDate;
        
        
        
        public ViewHolder(Context mCon,View v, Activity acd) {
            super(v);
            mTextView = v;
            mContext = mCon;
            this.ac = acd;
          
            this.makeID = (FancyButton)v.findViewById(R.id.makeID);
            this.makeID.setEnabled(false);
            this.makeMan = (TextView)v.findViewById(R.id.makeMan);
            this.makeType = (TextView)v.findViewById(R.id.makeType);
            this.makePhone = (TextView)v.findViewById(R.id.makePhone);
            this.makeName = (TextView)v.findViewById(R.id.makeName);
            this.makeTime = (TextView)v.findViewById(R.id.makeTime);
            this.makeDate = (TextView)v.findViewById(R.id.makedate);
            this.makeCreateDate = (TextView)v.findViewById(R.id.makeCreateDate);
          
            
            mTextView.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                    Toast.makeText(mContext, "当前点击的位置："+getPosition(), Toast.LENGTH_SHORT).show();
                	
                	
                }  
            }); 
            
        }
    }
}