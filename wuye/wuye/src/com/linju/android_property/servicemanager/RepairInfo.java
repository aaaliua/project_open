package com.linju.android_property.servicemanager;

import java.util.List;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
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
import com.linju.android_property.cache.ConfigCache;
import com.linju.android_property.dialog.AlertDialog;
import com.linju.android_property.dialog.Effectstype;
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
 * 维修详细分配
 * @author Administrator
 *
 */
public class RepairInfo extends BaseActivity implements OnClickListener{

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
	@InjectView(R.id.send)
	WPTextView tasksend;
	@InjectView(R.id.Photogridview)
	NoScorllGridView photoGrid;
	
	@InjectView(R.id.repair_position)
	Spinner sp;
	@InjectView(R.id.repair_response)
	Spinner rs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_info);
		titlebar.setText(getString(R.string.repair_task));
		back.setOnClickListener(this);
		tasksend.setOnClickListener(this);
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
		//初始化img
		if(bean.getImages()!= null && bean.getImages().size() > 0){
			repair_img_adapter adapter = new repair_img_adapter(this,bean.getImages());
			photoGrid.setAdapter(adapter);
		}
		
		getPosition();
	}
	
	//获取部门信息
	private void getPosition(){
		RequestParams params = new RequestParams();
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		params.put(BasicMember.POSITION_TYPE, BasicMember.position);
		//请求时候的dialog
		final Dialog mDialog = Progress_Dialog.createDialogPro(this, getResources().getString(R.string.app_loading));
		AppApplication.getHttpClient().get(RequestURL.GET_POSITION,params, new AsyncHttpResponseHandler(){

			private boolean isSuc = false;
			@Override
			public void onStart() {
				mDialog.show();
			}

			@Override
			@Deprecated
			public void onSuccess(String content) {
				parsePosition(content,1);   //解析返回的数据
				isSuc = true;
			}
			
			@Override
			@Deprecated
			public void onFailure(Throwable error) {
				if(mDialog != null && mDialog.isShowing()){
					mDialog.dismiss();
				}
				Toaster.showOneToast(R.string.app_loading_fail);
				isSuc = false;
			}

			@Override
			public void onFinish() {
				if(mDialog != null && mDialog.isShowing()){
					mDialog.dismiss();
				}
			}
			
		});
	}
	
	/**
	 * 
	 * @param jsonString 服务器json数据
	 * @param type   判断是部门还是责任人的类型   1=部门 2=责任人
	 */
	private void parsePosition(String jsonString,int type){
		if(type == 1){
			
			positionList = ParseJson.getEmployeeData(jsonString);
			//获取到了部门的信息  再接下来获取部门内部人员的信息  首先设置初始化的时候默认的值
			if(positionList != null ){
				spinner_adapter adapter = new spinner_adapter(this, positionList);
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(new spinnerItem());
				getPostionPos(positionList.get(0).getId());
			}
		}else if(type == 2){
			//责任人
			spinner_adapter adapter = null;
			position_pos_list = ParseJson.getEmployeeData(jsonString);
			//获取到了部门的信息  再接下来获取部门内部人员的信息  首先设置初始化的时候默认的值
			if(position_pos_list != null ){
				adapter = new spinner_adapter(this, position_pos_list);
				rs.setAdapter(adapter);
			}
		}
	}
	//获取部门内部人员信息
	private void getPostionPos(String id){
		
		RequestParams params = new RequestParams();
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		params.put(BasicMember.POSITION_DEPARTMENT_ID, id);
		//请求时候的dialog
		final Dialog mDialog = Progress_Dialog.createDialogPro(this, getResources().getString(R.string.app_loading));
		AppApplication.getHttpClient().get(RequestURL.REPAIR_DISTRIBUTED,params, new AsyncHttpResponseHandler(){

			private boolean isSuc = false;
			@Override
			public void onStart() {
				mDialog.show();
			}

			@Override
			@Deprecated
			public void onSuccess(String content) {
				parsePosition(content,2);   //解析返回的数据
				isSuc = true;
			}
			
			@Override
			@Deprecated
			public void onFailure(Throwable error) {
				if(mDialog != null && mDialog.isShowing()){
					mDialog.dismiss();
				}
				Toaster.showOneToast(R.string.app_loading_fail);
				isSuc = false;
			}

			@Override
			public void onFinish() {
				if(mDialog != null && mDialog.isShowing()){
					mDialog.dismiss();
				}
			}
			
		});
	}
	
	//点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.send:
			if(sp.getSelectedItem() == null){
				new AlertDialog(this).builder()
				.setMsg("请选择部门!")
				.withDuration(100)
				.withEffect(Effectstype.Slideleft)
				.setNegativeButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
				return;
			}
			GetEmployeeBean position = (GetEmployeeBean)sp.getSelectedItem();
			if(rs.getSelectedItem() == null){
				new AlertDialog(this).builder()
				.setMsg("请选择责任人，如无，请更换部门!")
				.withDuration(100)
				.withEffect(Effectstype.Slideleft)
				.setNegativeButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
				return;
			}
			GetEmployeeBean man = (GetEmployeeBean)rs.getSelectedItem();
			String positionmsg = position.getName();
			String positionman = man.getName();
			new AlertDialog(this).builder().setTitle("分配任务")
			//用HTML工具类给某些文字加上颜色
			.setMsg(Html.fromHtml("分配任务给"+"<font color='red'>"+positionmsg+"</font>"+"的"+"<font color='red'>"+positionman+"</font>"))
			.withDuration(100)
			.withEffect(Effectstype.Slideleft)
			.setPositiveButton("确认分配", new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			}).setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			}).show();
			break;
		default:
			break;
		}
	}

	//spinner下拉的点击事件
	class spinnerItem implements OnItemSelectedListener{


		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			GetEmployeeBean bean  = positionList.get(position);
			getPostionPos(bean.getId());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
		
	}

	
}
