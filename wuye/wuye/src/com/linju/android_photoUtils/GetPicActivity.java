package com.linju.android_photoUtils;

import java.io.Serializable;
import java.util.List;

import roboguice.inject.InjectView;
import roboguice.util.Ln.BaseConfig;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 自定义的图片选择器
 * @author Administrator
 *
 */
public class GetPicActivity extends BaseActivity implements OnClickListener{
	public static final String EXTRA_IMAGE_LIST = "imagelist";

	
	
	// ArrayList<Entity> dataList;//用来装载数据源的列表
	List<ImageBucket> dataList;
	@InjectView(R.id.listview)
	ListView mListView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static Bitmap bimap;
	
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);
		titlebar.setText(getString(R.string.selectImage));
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
       
		initData();
		initView();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// /**
		// * 这里，我们假设已经从网络或者本地解析好了数据，所以直接在这里模拟了10个实体类，直接装进列表中
		// */
		// dataList = new ArrayList<Entity>();
		// for(int i=-0;i<10;i++){
		// Entity entity = new Entity(R.drawable.picture, false);
		// dataList.add(entity);
		// }
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.loading_error);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		
		
		back.setOnClickListener(this);
		
		
		adapter = new ImageBucketAdapter(GetPicActivity.this, dataList);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/**
				 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
				 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
				 */
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				/**
				 * 通知适配器，绑定的数据发生了改变，应当刷新视图
				 */
				// adapter.notifyDataSetChanged();
				Intent intent = new Intent(GetPicActivity.this,
						ImageGridActivity.class);
				intent.putExtra(GetPicActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);	
				intent.putExtra(ImageGridActivity.EXTRA_TITLE, (Serializable)dataList.get(position).bucketName);
				startActivity(intent);
				finish();
			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.back:
//		   if (Bimp.act_bool){
//			Intent intent = new Intent(GetPicActivity.this,PublishedActivity.class);
//			Bundle bundle = new Bundle();
//    		bundle.putBoolean("pubsort", true);
//			intent.putExtras(bundle);
//			startActivity(intent);
//			Bimp.act_bool=false;
//			}
//			this.finish();
//			
//			break;
//
//		default:
//			break;
//		}
	}
}