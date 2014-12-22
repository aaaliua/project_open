package com.dazhongcun.merchants.adapter;


import java.util.List;

import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.application.AppApplication;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.merchants.utils.ImageOptions;
import com.dazhongcun.photoview.ImagePagerActivity;
import com.dazhongcun.widget.FancyButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewGalleyAdapter extends RecyclerView.Adapter<RecyclerViewGalleyAdapter.ViewHolder> {
    private final Context mContext;
    private final String[] datas;
    public final  Activity ac;

    public RecyclerViewGalleyAdapter(Context context, String[] datas,Activity ac) {
    	mContext = context;
    	this.datas = datas;
    	this.ac = ac;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    	View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.galley_list_item, parent, false);
        return new ViewHolder(mContext,view,ac,datas);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
    	String ent = datas[position];
    	AppApplication.getImageLoader().displayImage(ent, viewHolder.img,ImageOptions.defaultOptions);
    	
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mTextView;
        private final Context mContext;
        
        private final Activity ac;
        
        
        
        public ImageView img;
        
        
        public ViewHolder(Context mCon,View v, Activity acd) {
            super(v);
            mTextView = v;
            mContext = mCon;
            this.ac = acd;
          
            this.img = (ImageView)v.findViewById(R.id.galley_img);
          
            
            mTextView.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                    Toast.makeText(mContext, "当前点击的位置："+getPosition(), Toast.LENGTH_SHORT).show();
                }  
            }); 
            
        }
        public ViewHolder(Context mCon,View v, Activity acd,final String[] datas) {
        	super(v);
        	mTextView = v;
        	mContext = mCon;
        	this.ac = acd;
        	
        	this.img = (ImageView)v.findViewById(R.id.galley_img);
        	
        	
        	mTextView.setOnClickListener(new OnClickListener() {  
        		@Override  
        		public void onClick(View v) {  
        			Intent intent = new Intent(mContext,
        					ImagePagerActivity.class);
        			intent.putExtra(ImagePagerActivity.IMAGES, datas);
        			intent.putExtra(ImagePagerActivity.IMAGE_POSITION, getPosition());
        			mContext.startActivity(intent);
        			
        		}  
        	}); 
        	
        }
    }
}