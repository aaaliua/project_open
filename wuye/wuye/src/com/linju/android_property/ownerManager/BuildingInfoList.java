package com.linju.android_property.ownerManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import roboguice.inject.InjectView;
import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.linju.android_property.activity.LoginActivity;
import com.linju.android_property.adapter.BuildingAdapter;
import com.linju.android_property.adapter.ComplaintAdapter;
import com.linju.android_property.adapter.RepairAdapter;
import com.linju.android_property.adapter.spinner_adapter;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.dialog.ActionSheetDialog;
import com.linju.android_property.dialog.Effectstype;
import com.linju.android_property.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.linju.android_property.dialog.ActionSheetDialog.SheetItemColor;
import com.linju.android_property.dialog.effects.BaseEffects;
import com.linju.android_property.entity.Building_info_Bean;
import com.linju.android_property.entity.ComplaintBean;
import com.linju.android_property.entity.GetEmployeeBean;
import com.linju.android_property.entity.Login_Bean;
import com.linju.android_property.entity.Talk_repair_Bean;
import com.linju.android_property.servicemanager.ComplaintInfo;
import com.linju.android_property.user.UserInfoSettingActivity;
import com.linju.android_property.utils.BasicMember;
import com.linju.android_property.utils.DownloadUtils;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property.utils.ParseJson;
import com.linju.android_property.utils.RequestURL;
import com.linju.android_property.utils.StringUtils;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 楼宇列表
 * 
 * @author Administrator
 * 
 */
public class BuildingInfoList extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener ,OnRefreshListener2{
	public static final int INFO = 1;
	public static final int ADD = 2;
	public static final int EDIT = 3;

	int count;
	private String pullUrl;
	
	private String typeID;
	private Building_info_Bean bsss;   //popupwindow中需要
	private Building_info_Bean newobj;  //dialog中需要

	private List<GetEmployeeBean> position_pos_list; // 职位

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;
	@InjectView(R.id.empty)
	RelativeLayout empey;

	@InjectView(R.id.app_loading)
	View loadinglayout;

	@InjectView(R.id.lists)
	PullToRefreshListView mListView;


	private List<Building_info_Bean> beans;
	private BuildingAdapter adapter;

	public  int limite = 10; // 每页10条
	public  int offsetint = 0; // 分页标识

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_complaint_list);
		titlebar.setText(getString(R.string.louyu_title));
		editOrAdd.setVisibility(View.VISIBLE);
		editOrAdd.setText(getString(R.string.app_add));
		editOrAdd.setOnClickListener(this);
		back.setOnClickListener(this);

		mListView.setOnRefreshListener(this);
		mListView.setShowIndicator(false);
		
		TextView t = (TextView) empey.findViewById(R.id.hint);
		t.setText("没有任何数据哦!");
		mListView.setEmptyView(empey);
		getKlassType();
		getList();
	}

	// 获取房屋类型
	private void getKlassType() {
		AppApplication.getHttpClient().get(RequestURL.GET_HOUSE_KLASS,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						super.onFailure(error);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parsePosition(content);
					}

				});
	}

	private void parsePosition(String jsonString) {
		position_pos_list = ParseJson.getEmployeeData(jsonString);
	}

	// 获取服务器上列表
	private void getList() {
		RequestParams params = new RequestParams();
		params.put(BasicMember.OFFSET, offsetint + "");
		params.put(BasicMember.LIMITE, limite + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		pullUrl = RequestURL.GET_BUILDING_LIST + "?" + params.toString();
		AppApplication.getHttpClient().get(RequestURL.GET_BUILDING_LIST,
				params, new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						super.onFailure(error);
					}

					@Override
					public void onFinish() {
						loadinglayout.setVisibility(View.GONE);
					}

					@Override
					public void onStart() {
						super.onStart();
						loadinglayout.setVisibility(View.VISIBLE);
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						parseJsonbean(content, false);
					}

				});
	}

	private void parseJsonbean(String json, boolean isLoadMore) {
		if (isLoadMore == true) {
			List<Building_info_Bean> be = ParseJson.GetBuildingJSON(json);
			if(be != null && be.size() > 0 ){
				beans.addAll(be);
				mListView.onRefreshComplete();
				adapter.notifyDataSetChanged();
				//判断数据是否小于10条 
				if(be.size() <limite){
					mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
				}else{
					mListView.setMode(Mode.BOTH);
				}
			}else{
				mListView.onRefreshComplete();
				mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
			}
		} else {
			if (beans != null) {
				beans.clear();
			}
			beans = ParseJson.GetBuildingJSON(json);
			// 获取数据判断数据是否为空
			if (beans != null) {

				adapter = new BuildingAdapter(this, beans);
				// 判断数据是否小于10条
				mListView.onRefreshComplete();
				if (beans.size() < limite) {
					mListView.setMode(Mode.PULL_DOWN_TO_REFRESH);
				} else {
					mListView.setMode(Mode.BOTH);
				}
			}
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(this);
			mListView.getRefreshableView().setOnItemLongClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			onBackPressed();
			break;
		case R.id.edit_or_add:

			// window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			Building_info_Bean addBean = new Building_info_Bean();
			showDialog(addBean, ADD,-1);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Building_info_Bean b = beans.get(position - 1);
		showDialog(b, INFO,-1);

		// actiity的跳转动画
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
//		new PopupWindowPic(this, view, position - 1);
		final View psview = view;
		final int pos = position - 1;
		bsss = beans.get(pos);
		
		ActionSheetDialog dialog = new ActionSheetDialog(BuildingInfoList.this)
		.builder()
		.setCancelable(false)
		.setCanceledOnTouchOutside(false)
		.addSheetItem(getString(R.string.edit_info_title), SheetItemColor.Blue,
				new OnSheetItemClickListener() {
					@Override
					public void onClick(int which) {
						showDialog(bsss, EDIT,pos);
					}
				})
		.addSheetItem(getString(R.string.delete), SheetItemColor.Blue,
				new OnSheetItemClickListener() {
					@Override
					public void onClick(int which) {
						new com.linju.android_property.dialog.AlertDialog(
								BuildingInfoList.this).builder().setTitle("警告!")
								.setMsg("确定删除  \""+bsss.getName()+"\"  该条楼宇信息？")
								.withDuration(200)
								.withEffect(Effectstype.Slideleft)
								.setCancelable(false)
								.setPositiveButton("确认", new OnClickListener() {
									@Override
									public void onClick(View v) {
										RequestParams params = new RequestParams();
										params.put(Building_info_Bean.params_id,
												bsss.getId());
										postDelete(params, psview, pos,
												RequestURL.DELETE_BUILDING_INFO);

									}
								}).setNegativeButton("取消", new OnClickListener() {
									@Override
									public void onClick(View v) {
										
									}
								}).show();
					}
				});
		
		dialog.show();
		return true;
	}

	//popup的显示
//	class PopupWindowPic extends PopupWindow {
//
//		public PopupWindowPic(Context context, final View parent,
//				final int position) {
//			bsss = beans.get(position);
//
//			View view = View.inflate(context, R.layout.item_popupwindows, null);
//			view.startAnimation(AnimationUtils.loadAnimation(context,
//					R.anim.fade_ins));
//			LinearLayout llLayout = (LinearLayout) view
//					.findViewById(R.id.ll_popup);
//			llLayout.startAnimation(AnimationUtils.loadAnimation(context,
//					R.anim.push_bottom_in_2));
//			// 设置宽高
//			setWidth(LayoutParams.FILL_PARENT);
//			setHeight(LayoutParams.FILL_PARENT);
//			setBackgroundDrawable(new BitmapDrawable());
//			setFocusable(true);
//			setOutsideTouchable(true); // 外部触摸不隐藏
//			setContentView(view);
//			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//			update();
//
//			Button camera = (Button) view
//					.findViewById(R.id.item_popupwindows_camera);
//			Button photo = (Button) view
//					.findViewById(R.id.item_popupwindows_Photo);
//			Button cancel = (Button) view
//					.findViewById(R.id.item_popupwindows_cancel);
//			//修改
//			camera.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					showDialog(bsss, EDIT,position);
//					dismiss();
//				}
//			});
//			//删除
//			photo.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					new com.linju.android_property.dialog.AlertDialog(
//							BuildingInfoList.this).builder().setTitle("警告!")
//							.setMsg("确定删除  \""+bsss.getName()+"\"  该条楼宇信息？").withDuration(260)
//							.withEffect(Effectstype.Slideleft)
//							.setCancelable(false)
//							.setPositiveButton("确认", new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									RequestParams params = new RequestParams();
//									params.put(Building_info_Bean.params_id,
//											bsss.getId());
//									postDelete(params, parent, position,
//											RequestURL.DELETE_BUILDING_INFO);
//
//								}
//							}).setNegativeButton("取消", new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									
//								}
//							}).show();
//
//					dismiss();
//				}
//			});
//			cancel.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					dismiss();
//				}
//			});
//		}
//
//	}

	// 根据不同类型显示dialog
	private void showDialog(final Building_info_Bean b, int type,final int position) { // type
																	// 1=查看详情
		// 2=添加 3=修改
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.setCancelable(true);
		dlg.show();
		Window window = dlg.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		window.setContentView(R.layout.view_alertdialog_edit);
		// window.setGravity(Gravity.TOP);

		// 房屋类型
		final LinearLayout postlayout = (LinearLayout) window
				.findViewById(R.id.postlayout);
		TextView title = (TextView) window.findViewById(R.id.txt_title);
		final EditText num = (EditText) window.findViewById(R.id.number);
		EditText count = (EditText) window.findViewById(R.id.building_count);
		EditText b_type = (EditText) window
				.findViewById(R.id.building_position);
		final EditText desc = (EditText) window
				.findViewById(R.id.building_desc);
		// 添加/修改都不需要显示房屋数量
		RelativeLayout countlayout = (RelativeLayout) window
				.findViewById(R.id.count_layout);

		// 选择类型的spinner；
		Spinner sp = (Spinner) window.findViewById(R.id.position);
		View load = window.findViewById(R.id.app_loading);
		load.setVisibility(View.GONE);
		Button done = (Button) window.findViewById(R.id.flot_but);
		Button cancel = (Button) window.findViewById(R.id.flot_butcancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});
		// done.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dlg.cancel();
		// }
		// });

		if (INFO == type) { // 查看详情
			// 隐藏取消按钮 ,Spinner ，改变标题 设置editext不可以编辑
			// 显示房屋类型文本
			cancel.setVisibility(View.GONE);
			sp.setVisibility(View.GONE);
			title.setText(getString(R.string.lou_info));
			// 根据类型来判断是显示文本还是spinner
			b_type.setVisibility(View.VISIBLE);

			num.setEnabled(false);
			count.setEnabled(false);
			b_type.setEnabled(false);
			desc.setEnabled(false);
			
			num.setBackgroundResource(R.color.white);
			count.setBackgroundResource(R.color.white);
			b_type.setBackgroundResource(R.color.white);
			desc.setBackgroundResource(R.color.white);
			// 给文本赋值
			num.setText(b.getName());
			count.setText(b.getHouse_count());
			b_type.setText(b.getHouse_klass());
			desc.setText(b.getDescription());
			// 给单个按钮设置文本
			done.setText(getString(R.string.app_done));

			done.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dlg.cancel();
				}
			});

		} else if (ADD == type) { // 添加楼宇
			newobj = new Building_info_Bean();
			countlayout.setVisibility(View.GONE);

			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 获取值 先验证后再执行表单提交
					newobj.setName(num.getText().toString().trim());
					newobj.setDescription(desc.getText().toString().trim());
					newobj.setHouse_klass_id(typeID);

					RequestParams params = new RequestParams();
					params.put(Building_info_Bean.params_name, newobj.getName());
					params.put(Building_info_Bean.params_desc,
							newobj.getDescription());
					params.put(Building_info_Bean.params_klass,
							newobj.getHouse_klass_id());
					postEdit(params, postlayout, dlg,
							RequestURL.ADD_BUILDING_Info,ADD,null,-1);
					// dlg.cancel();
				}
			});

			// 显示Spinner
			sp.setVisibility(View.VISIBLE);
			// 给Spinner添加数据
			if (position_pos_list != null) {
				spinner_adapter adapter = new spinner_adapter(this,
						position_pos_list);
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(new spinnerItem());
			}

		} else if (EDIT == type) { // 修改楼宇
			countlayout.setVisibility(View.GONE);
			title.setText(getString(R.string.lou_edit));
			// 显示Spinner
			sp.setVisibility(View.VISIBLE);
			// 给Spinner添加数据
			if (position_pos_list != null) {
				spinner_adapter adapter = new spinner_adapter(this,
						position_pos_list);
				sp.setAdapter(adapter);
				sp.setOnItemSelectedListener(new spinnerItem());
				sp.setSelection(getTypePosition(b.getHouse_klass_id()), true);
			}

			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 获取值 先验证后再执行表单提交
					b.setName(num.getText().toString().trim());
					b.setDescription(desc.getText().toString().trim());
					b.setHouse_klass_id(typeID);
					
					RequestParams params = new RequestParams();
					params.put(Building_info_Bean.params_id, b.getId());
					params.put(Building_info_Bean.params_name, b.getName());
					params.put(Building_info_Bean.params_desc,
							b.getDescription());
					params.put(Building_info_Bean.params_klass,
							b.getHouse_klass_id());
					
					b.setHouse_klass(getTypeName(typeID));
					
					
					postEdit(params, postlayout, dlg,
							RequestURL.UPDATE_BUILDING_INFO,EDIT,b,position);
					// dlg.cancel();
				}
			});

			// 给文本赋值
			num.setText(b.getName());
			b_type.setText(b.getHouse_klass());
			desc.setText(b.getDescription());
		}
	}

	// spinner下拉的点击事件
	class spinnerItem implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			GetEmployeeBean bean = position_pos_list.get(position);
			typeID = bean.getId();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}

	// 表单提交
	/**
	 * 
	 * @param params   表单对象
	 * @param v			
	 * @param dlg		弹出框
	 * @param URL		请求的URL
	 * @param type		类型是 修改还是删除
	 * @param b			楼宇列表的单个对象
	 * @param position	如果是修改 会传过来位置的信息
	 */
	private void postEdit(RequestParams params, final View v,
			final AlertDialog dlg, String URL,final int type,final Building_info_Bean b,final int position) {

		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());

		AppApplication.getHttpClient().post(URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						super.onFailure(error);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onStart() {
						super.onStart();
						if (v != null) {

							v.setVisibility(View.VISIBLE);
						}
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						Toaster.showOneToast(content);
						if (v != null) {

							v.setVisibility(View.GONE);
						}
						if (dlg != null && dlg.isShowing()) {
							dlg.dismiss();
						}
						
						
						if(type == EDIT){
							//更新adapter中的数据
							if(beans != null){
								beans.set(position, b);
								adapter.notifyDataSetChanged();
							}
						}else if(type == ADD){
							//重庆请求一次服务器
							offsetint = 0; 
							count = 0;
							getList();
						}
					}

				});
	}

	//删除的请求
	private void postDelete(RequestParams params, final View parent,final int Position,String URL) {

		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());

		AppApplication.getHttpClient().post(URL, params,
				new AsyncHttpResponseHandler() {

					@Override
					@Deprecated
					public void onFailure(Throwable error) {
						super.onFailure(error);
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onStart() {
						super.onStart();
					}

					@Override
					@Deprecated
					public void onSuccess(String content) {
						Toaster.showOneToast(content);
						count ++;
						adapter.deleteCell(parent, Position);
					}

				});
	}
	
	//获取类别的name
	private String getTypeName(String typeID){
		String tempString = "";
		if(position_pos_list != null){
			for(GetEmployeeBean bea : position_pos_list){
				if(bea.getId() == typeID){
					tempString = bea.getName();
				}
			}
		}
		return  tempString;
	}
	
	//根据klassID获取spinner所在的下标位置
	private int getTypePosition(String klassID){
		int po =0;
		if(position_pos_list != null){
			for(int i = 0;i<position_pos_list.size();i++){
				if(klassID == position_pos_list.get(i).getId()){
					po = i;
				}
			}
		}
		
		
		return po;
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		
		offsetint = 0; // 让分页标示恢复初始化
		String label = DateUtils.formatDateTime(this.getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		new GetDataTask().execute();
	}
	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String result = null;
			offsetint = 0; // 让分页标示恢复初始化
			count = 0;
			String url = pullUrl;

			File file = new File(AppApplication.mSdcardCache
					+ File.separator+StringUtils.replaceUrlWithPlus(url));
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			try {
				DownloadUtils.download(url, file, false, null);
				result = FileUtils.readTextFile(file);
			} catch (Exception e) {
				// TODO: handle exception
				// Logger.e("ContentListFragment", "下拉刷新", e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				parseJsonbean(result,false);
			}
			// Call onRefreshComplete when the list has been refreshed.
			mListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}
		
	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		String label = DateUtils.formatDateTime(this.getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		int position = beans.size() - 1;
		loadMoreData1(position);
	}
	
	private void loadMoreData1(final int position) {
		RequestParams params = new RequestParams();
		params.put(BasicMember.OFFSET, String.valueOf((offsetint = (offsetint + limite)-count /*offsetint += limite*/)));
		params.put(BasicMember.LIMITE, limite + "");
		params.put(Login_Bean.USER_SUBDISTRICTADDRESSID, LoginActivity
				.getUser().getSubdistrict_address_id());
		count = 0;
		AppApplication.getHttpClient().get(RequestURL.GET_BUILDING_LIST, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						parseJsonbean(content,true);
						mListView.onRefreshComplete();
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						mListView.onRefreshComplete();
						Toaster.showOneToast(R.string.app_loading_fail);
					}
				});
	}
}
