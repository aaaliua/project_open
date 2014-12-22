package com.linju.android_property.fragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.linju.android_property.adapter.Und_adapter;
import com.linju.android_property.adapter.Und_simple_adapter;
import com.linju.android_property.base.BaseFragment;
import com.linju.android_property.entity.Content_menu;
import com.linju.android_property.entity.Content_menu_child;
import com.linju.android_property.entity.UndEntity;
import com.linju.android_property.ownerManager.BuildingInfoList;
import com.linju.android_property.servicemanager.ComplaintList;
import com.linju.android_property.servicemanager.PostList;
import com.linju.android_property.servicemanager.PropertyList;
import com.linju.android_property.utils.StartActivityUtils;
import com.linju.android_property.utils.ViewHolder;
import com.linju.android_property.viewutils.LetterImageView;
import com.linju.android_property.viewutils.NoScorllGridView;
import com.linju.android_property.viewutils.Toaster;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;
import com.readystatesoftware.viewbadger.BadgeView;


/**
 * 功能模块的分类
 * @author LT
 *
 */
public class ContentFragment extends BaseFragment implements OnItemClickListener{

	@InjectView(R.id.content)
	NoScorllGridView mGridView;
	private List<UndEntity> datas;
	
	private Content_menu menu;
	public ContentFragment() {
		super();
	}
	public ContentFragment(Content_menu menu) {
		super();
		this.menu = menu;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		datas = new ArrayList<UndEntity>();
//		long kefuID = 1;
//		String title_kefu = getString(R.string.kefu_guanli);;
//		int titleIconkf = R.drawable.img2;
//		UndEntity bx = new UndEntity(kefuID,titleIconkf,title_kefu,getString(R.string.baoxiu),R.drawable.kefu_baoxiu);
//		UndEntity da = new UndEntity(kefuID,titleIconkf,title_kefu,getString(R.string.danganshi),R.drawable.kefu_dangan);
//		UndEntity ts = new UndEntity(kefuID,titleIconkf,title_kefu,getString(R.string.tousu),R.drawable.kefu_tousu);
//		UndEntity wy = new UndEntity(kefuID,titleIconkf,title_kefu,getString(R.string.wuye),R.drawable.kefu_wuye);
//		UndEntity gg = new UndEntity(kefuID,titleIconkf,title_kefu,getString(R.string.gonggao),R.drawable.kefu_gonggao);
//		//客服
//		datas.add(gg);
//		datas.add(bx);
//		datas.add(ts);
//		datas.add(wy);
//		datas.add(da);
		
		if(menu != null){
			for(Content_menu_child child : menu.getMenus()){
				UndEntity und = null;
				if("1".equals(child.getType_id())){ //公告
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_gonggao);
				
				}else if("2".equals(child.getType_id())){ //档案
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_dangan);
				
				}else if("3".equals(child.getType_id())){ //报修管理
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_baoxiu);
				
				}else if("4".equals(child.getType_id())){ //小区投诉
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_tousu);
				
				}else if("5".equals(child.getType_id())){ //物业费
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_wuye);
				
				}else if("6".equals(child.getType_id())){ //服务管理
					
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.fuwu_guanli);
				
				}else if("7".equals(child.getType_id())){ //预定服务
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.fuwu_yudin);
				
				}else if("8".equals(child.getType_id())){ //订单管理
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.fuwu_dindan);
				
				}else if("9".equals(child.getType_id())){ //楼宇信息
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.yezhu_louyu);
				
				}else if("10".equals(child.getType_id())){ //房产信息
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.yezhu_fangchan);
				
				}else if("11".equals(child.getType_id())){ //停车位
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.yezhu_tingchewei);
				
				}else if("12".equals(child.getType_id())){ //认证
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.yezhu_renzheng);
				
				}else if("13".equals(child.getType_id())){ //部门设置
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.renshi_bumen);
				
				}else if("14".equals(child.getType_id())){ //员工管理
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.renshi_yuangong);
				
				}else if("15".equals(child.getType_id())){ //职位设置
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.renshi_zhiwei);
				
				}else if("16".equals(child.getType_id())){ //库存资产
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.zichan_kucun);
				
				}else if("17".equals(child.getType_id())){ //固定资产
				
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.zichan_guding);
				
				}
				
				else{
					und = new UndEntity(Long.valueOf(menu.getContentID()),child.getType_id(),menu.getContentTitle(),child.getType_name(),R.drawable.kefu_wuye);
				}
					
					
			datas.add(und);
			}
		}
		
		
		initGridview();
		
	}

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_content, null);
	}
	
	private void initGridview(){
		Und_simple_adapter adapter = new Und_simple_adapter(getActivity(), datas);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);
	}
	
	
	//子菜单的点击  各个功能进入相应的模块
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		UndEntity ent = (UndEntity)parent.getItemAtPosition(position);
		Toaster.showOneToast(ent.getChildId());
		Intent it = null;
		if("1".equals(ent.getChildId())){
			it = new  Intent(getActivity(),PostList.class);
		}else if("4".equals(ent.getChildId())){
			it = new  Intent(getActivity(),ComplaintList.class);
		}else if("9".equals(ent.getChildId())){
			it = new  Intent(getActivity(),BuildingInfoList.class);
		}else if("5".equals(ent.getChildId())){
			it = new  Intent(getActivity(),PropertyList.class);
		}
		
		if(it != null){
			
			StartActivityUtils.startActivity(getActivity(), it);
		}
	}
	
}
