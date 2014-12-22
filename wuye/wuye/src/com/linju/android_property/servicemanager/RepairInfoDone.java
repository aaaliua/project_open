package com.linju.android_property.servicemanager;

import java.util.List;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.adapter.repair_img_adapter;
import com.linju.android_property.adapter.spinner_adapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.entity.GetEmployeeBean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.viewutils.NoScorllGridView;
import com.linju.android_property.viewutils.Progress_Dialog;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 已完成已取消正在处理的维修界面
 * @author Administrator
 *
 */
public class RepairInfoDone extends BaseActivity implements OnClickListener{

	public static final String EXTRAOBJ  = "repair:obj";
	private List<GetEmployeeBean> positionList;   		//部门
	private List<GetEmployeeBean> position_pos_list;	//职位
	
	@InjectExtra(value = EXTRAOBJ,optional = true)
	Talk_repair_Bean bean;
	
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	
	//文本组件
	@InjectView(R.id.titles)
	WPTextView title;
	@InjectView(R.id.type)
	WPTextView type;
	@InjectView(R.id.man)
	WPTextView man;
	@InjectView(R.id.address)
	WPTextView address;
	@InjectView(R.id.tel)
	WPTextView tel;
	@InjectView(R.id.date)
	WPTextView date;
	@InjectView(R.id.repairInfo)
	WPTextView repairInfo;
	@InjectView(R.id.status)
	WPTextView status;
	@InjectView(R.id.position)
	WPTextView position;
	@InjectView(R.id.positionman)
	WPTextView positionman;
	@InjectView(R.id.Photogridview)
	NoScorllGridView photoGrid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_info_done);
		titlebar.setText(getString(R.string.repair_task));
		back.setOnClickListener(this);
		
		initdata();
		// 建立数据源
//		String[] mItems = getResources().getStringArray(R.array.spinnername);
//		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,R.layout.itme, mItems);
//		//绑定 Adapter到控件
//		sp.setAdapter(_Adapter);
//		rs.setAdapter(_Adapter);
	}
	
	//初始化数据
	private void initdata(){
		title.setText(bean.getTitle().trim());
		man.setText(bean.getUser_name().trim());
		address.setText(bean.getHouse_number().trim());
		tel.setText(bean.getCall().trim());
		date.setText(bean.getCreated_at().trim());
		repairInfo.setText(bean.getContent().trim());
		status.setText(bean.getStatus().trim());
		positionman.setText(bean.getDepartment());
		//初始化img
		if(bean.getImages()!= null && bean.getImages().size() > 0){
			repair_img_adapter adapter = new repair_img_adapter(this,bean.getImages());
			photoGrid.setAdapter(adapter);
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;

		default:
			break;
		}
	}


	
}
