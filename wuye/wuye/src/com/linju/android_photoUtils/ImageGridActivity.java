package com.linju.android_photoUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import com.linju.android_photoUtils.ImageGridAdapter.TextCallback;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.servicemanager.PostAdd;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


public class ImageGridActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static final String EXTRA_TITLE = "imagetitle";
	
	@InjectExtra(value = EXTRA_TITLE,optional = true)
	private String title;
	// ArrayList<Entity> dataList;
	List<ImageItem> dataList;
	@InjectView(R.id.gridview)
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	
	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择"+Bimp.MAXSIZE+"张图片", 400).show();
				break;

            default:
                break;
            }
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_grid);
        titlebar.setText(title);
        editOrAdd.setVisibility(View.VISIBLE);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);
      
		initView();
		editOrAdd.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext();) {
                    list.add(it.next());
                }

				if (Bimp.act_bool) {					
					Intent intent = new Intent(ImageGridActivity.this,PostAdd.class);
					Bundle bundle = new Bundle();
		    		bundle.putBoolean("pubsort", true);
					intent.putExtras(bundle);
					startActivity(intent);					
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++) {
					if (Bimp.drr.size() < Bimp.MAXSIZE) {
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

        });
    }

	private void initView() {
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			onBackPressed();
//				    Bimp.bmp.clear();
//					Bimp.drr.clear();
//					Bimp.max = 0;										
//					FileUtils.deleteDir();
//						
//				   if (Bimp.act_bool) {
//					
//				
//					Intent intent = new Intent(ImageGridActivity.this,PostAdd.class);
//					Bundle bundle = new Bundle();
//		    		bundle.putBoolean("pubsort", true);
//					intent.putExtras(bundle);
//					startActivity(intent);
//					Bimp.act_bool=false;
//			      }
//					
//					finish();
					
					
			}
		});
		
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback() {
			public void onListen(int count) {
				if (count==0) {
				
					editOrAdd.setText("完成");
					
				}else {
					editOrAdd.setText("完成" + "(" + count + ")");
				}
				
			}
		});

        gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				
				adapter.notifyDataSetChanged();
			}

        });

    }
}
