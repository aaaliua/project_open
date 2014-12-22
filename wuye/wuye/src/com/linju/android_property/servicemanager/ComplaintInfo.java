package com.linju.android_property.servicemanager;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.linju.android_property.adapter.complaint_img_adapter;
import com.linju.android_property.adapter.repair_img_adapter;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.viewutils.NoScorllGridView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
/**
 * 投诉详细内容页面
 * @author Administrator
 *
 */
public class ComplaintInfo extends BaseActivity implements OnClickListener{

	public static final String EXTRA_OBJ = "complaint:obj";

	@InjectExtra(value = EXTRA_OBJ, optional = true)
	ComplaintBean bean;

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;

	// 文本组件
	@InjectView(R.id.titles)
	WPTextView title;
	@InjectView(R.id.type)
	WPTextView type;
	@InjectView(R.id.man)
	WPTextView man;
	@InjectView(R.id.tel)
	WPTextView tel;
	@InjectView(R.id.date)
	WPTextView date;
	@InjectView(R.id.repairInfo)
	WPTextView Info;
	@InjectView(R.id.status)
	WPTextView status;
	@InjectView(R.id.statusinfo)
	WPTextView statusinfo;
	@InjectView(R.id.Photogridview)
	NoScorllGridView photoGrid;

	
	@InjectView(R.id.statuslayout)
	RelativeLayout sta;
	
	@InjectView(R.id.flot_but)
	Button feedBut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_info);
		back.setText(getString(R.string.complaint_title));
		titlebar.setText(getString(R.string.complaint_info_title));
		back.setOnClickListener(this);
		feedBut.setOnClickListener(this);
		initView();
	}

	private void initView() {
		title.setText(bean.getTitle());
		Info.setText(bean.getContent());
		date.setText(bean.getCreated_at());
		status.setText(bean.getStatus());
		statusinfo.setText(bean.getFeedback());
		// 初始化img
		if (bean.getImages() != null && bean.getImages().size() > 0) {
			complaint_img_adapter adapter = new complaint_img_adapter(this,
					bean.getImages());
			photoGrid.setAdapter(adapter);
		}
		
		if(ComplaintBean.STATUE_DONE.equals(bean.getStatus())){
			sta.setVisibility(View.VISIBLE);
			feedBut.setVisibility(View.GONE);
		}else{
			sta.setVisibility(View.GONE);
			feedBut.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.flot_but:
			sta.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

}
