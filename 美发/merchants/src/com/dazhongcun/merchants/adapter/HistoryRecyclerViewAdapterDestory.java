package com.dazhongcun.merchants.adapter;

import java.util.List;

import org.json.JSONObject;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.merchants.entity.MakeEntity;
import com.dazhongcun.utils.ViewHolder;
import com.dazhongcun.widget.FancyButton;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryRecyclerViewAdapterDestory extends
		RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private final Context mContext;
	private List<MakeEntity> datas;
	public final Activity ac;

	public HistoryRecyclerViewAdapterDestory(Context context, List<MakeEntity> datas,
			Activity ac) {
		mContext = context;
		this.datas = datas;
		this.ac = ac;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		switch (viewType) {
		case MakeEntity.LOADMORE:
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
			ProgressBar bar = (ProgressBar)v.findViewById(R.id.progress);
			return new RecyclerView.ViewHolder(v) {
	          };
		case MakeEntity.ITEM:
			View view = (View) LayoutInflater.from(parent.getContext())
					.inflate(R.layout.history_list_item, parent, false);
			return new ListViewHolder(mContext, view, ac);
		default:
			View views = (View) LayoutInflater.from(parent.getContext())
					.inflate(R.layout.history_list_item, parent, false);
			return new ListViewHolder(mContext, views, ac);
		}

	}

	@Override
	public int getItemViewType(int position) {
		return datas.get(position).getLoadtype();
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		MakeEntity ent = datas.get(position);
		switch (ent.getLoadtype()) {
		case MakeEntity.LOADMORE:

			break;
		case MakeEntity.ITEM:
			ListViewHolder viewHolders = (ListViewHolder) viewHolder;
			viewHolders.makeID.setText(String.format(mContext.getResources()
					.getString(R.string.makeID), ent.getId()));
			viewHolders.makeMan.setText(ent.getUserName());
			viewHolders.makeType.setText(ent.getOutType());
			viewHolders.makePhone.setText(ent.getMobile());
			viewHolders.makeName.setText(ent.getStyListName());
			viewHolders.makeTime.setText(ent.getOutTime());
			viewHolders.makeDate.setText(ent.getOutdate());
			viewHolders.makeCreateDate.setText(ent.getCreateDate());
			viewHolders.status.setText(ent.getStatusName());
			break;
		default:
			ListViewHolder viewHolderd = (ListViewHolder) viewHolder;
			viewHolderd.makeID.setText(String.format(mContext.getResources()
					.getString(R.string.makeID), ent.getId()));
			viewHolderd.makeMan.setText(ent.getUserName());
			viewHolderd.makeType.setText(ent.getOutType());
			viewHolderd.makePhone.setText(ent.getMobile());
			viewHolderd.makeName.setText(ent.getStyListName());
			viewHolderd.makeTime.setText(ent.getOutTime());
			viewHolderd.makeDate.setText(ent.getOutdate());
			viewHolderd.makeCreateDate.setText(ent.getCreateDate());
			viewHolderd.status.setText(ent.getStatusName());
			break;
		}

	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public void remove(int i) {
		datas.remove(i);
		notifyItemRemoved(i);
	}

	public void add(MakeEntity entitye) {
		this.datas.add(entitye);
		notifyItemInserted(datas.size() - 1);
	}
	
	public void addAll(List<MakeEntity> list) {
	      this.datas.addAll(list);
	      notifyDataSetChanged();
	    }

	static class ListViewHolder extends RecyclerView.ViewHolder {

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
		public FancyButton status ;
		public ListViewHolder(Context mCon, View v, Activity acd) {
			super(v);
			mTextView = v;
			mContext = mCon;
			this.ac = acd;
			this.status = (FancyButton) v.findViewById(R.id.status);
			this.makeID = (FancyButton) v.findViewById(R.id.makeID);
			this.makeID.setEnabled(false);
			this.status.setEnabled(false);
			this.makeMan = (TextView) v.findViewById(R.id.makeMan);
			this.makeType = (TextView) v.findViewById(R.id.makeType);
			this.makePhone = (TextView) v.findViewById(R.id.makePhone);
			this.makeName = (TextView) v.findViewById(R.id.makeName);
			this.makeTime = (TextView) v.findViewById(R.id.makeTime);
			this.makeDate = (TextView) v.findViewById(R.id.makedate);
			this.makeCreateDate = (TextView) v
					.findViewById(R.id.makeCreateDate);

			mTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Toast.makeText(mContext, "当前点击的位置：" + getPosition(),
//							Toast.LENGTH_SHORT).show();
				
				}
			});

		}
		
	}
	
	
	
}