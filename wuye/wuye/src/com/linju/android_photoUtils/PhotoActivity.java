package com.linju.android_photoUtils;

import java.util.ArrayList;
import java.util.List;

import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property2.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class PhotoActivity extends BaseActivity {

	private ArrayList<View> listViews = null;
	private ViewPager pager;
	private MyPageAdapter adapter;
	private int count;
	private int ScaleAngle=0;
	
	private ImageView img;
	
	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	public int max;

	LinearLayout photo_relativeLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		photo_relativeLayout = (LinearLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);

		for (int i = 0; i < Bimp.bmp.size(); i++) {
			bmp.add(Bimp.bmp.get(i));
		}
		for (int i = 0; i < Bimp.drr.size(); i++) {
			drr.add(Bimp.drr.get(i));
		}
		max = Bimp.max;

		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});
		Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (listViews.size() == 1) {
					Bimp.bmp.clear();
					Bimp.drr.clear();
					Bimp.max = 0;
					FileUtils.deleteDir();
					finish();
				} else {
					String newStr = drr.get(count).substring( 
							drr.get(count).lastIndexOf("/") + 1,
							drr.get(count).lastIndexOf("."));
					bmp.remove(count);
					drr.remove(count);
					del.add(newStr);
					max--;
					pager.removeAllViews();
					listViews.remove(count);
					adapter.setListViews(listViews);
					adapter.notifyDataSetChanged();
				}
			}
		});
		Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
		photo_bt_enter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Bimp.bmp = bmp;
				Bimp.drr = drr;
				Bimp.max = max;
				for(int i=0;i<del.size();i++){				
					FileUtils.delFile(del.get(i)+".JPEG"); 
				}
				finish();
			}
		});
		
		
	   Button photo_bt_xuan=(Button)findViewById(R.id.photo_bt_xuan);
		photo_bt_xuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
				String newStr1 = drr.get(count).substring( 
						drr.get(count).lastIndexOf("/") + 1,
						drr.get(count).lastIndexOf("."));

				 Bitmap mySourceBmp = bmp.get(count);
			     int widthOrig = mySourceBmp.getWidth();
			     int heightOrig = mySourceBmp.getHeight();
				
				ScaleAngle=ScaleAngle+90;
        		Matrix matrix = new Matrix();
        		/*ʹ��Matrix.postScale ����ά��*/
        		matrix.postScale(1.0f, 1.0f);
        		/*ʹ��Matrix.setRotate ������תBitmap*/
        		matrix.setRotate(ScaleAngle);
        		/*�����µ�Bitmap����*/
        		Bitmap resizedBitmap = Bitmap.createBitmap(mySourceBmp, 0 , 0, widthOrig, heightOrig, matrix, true);
        	   
        		FileUtils.saveBitmap(resizedBitmap, newStr1);
        
        		
        		
                BitmapDrawable myNewBitmapDrawable = new BitmapDrawable(resizedBitmap);
        		
                
                
        		((ImageView) listViews.get(count)).setImageDrawable(myNewBitmapDrawable);
        		
              
                
                
        		
        		
        		
        		
        		
//        		listViews.clear();
//        		for (int i = 0; i < Bimp.bmp.size(); i++) {
//        			initListViews(Bimp.bmp.get(i));//
//        		}
//
//        		pager.removeAllViews();
//        		adapter = new MyPageAdapter(listViews);// ����adapter
//        		pager.setAdapter(adapter);// ����������
        		
			}
		});

		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < bmp.size(); i++) {
			initListViews(bmp.get(i),i);//
		}

		adapter = new MyPageAdapter(listViews);// ����adapter
		pager.setAdapter(adapter);// ����������
		Intent intent = getIntent();
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private void initListViews(Bitmap bm,int i) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		img = new ImageView(this);// ����textView����
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bm);
	    img.setId(i);
		img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		listViews.add(img);// ���view
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {// ҳ��ѡ����Ӧ����
			count = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {// �����С�����

		}

		public void onPageScrollStateChanged(int arg0) {// ����״̬�ı�

		}
	};

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;// content

		private int size;// ҳ��

		public MyPageAdapter(ArrayList<View> listViews) {// ���캯��
															// ��ʼ��viewpager��ʱ�����һ��ҳ��
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {// �Լ�д��һ�����������������
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public int getCount() {// ��������
			return size;
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {// ����view����
			((ViewPager) arg0).removeView(listViews.get(arg1 % size));
		}

		public void finishUpdate(View arg0) {
		}

		public Object instantiateItem(View arg0, int arg1) {// ����view����
			try {
				((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}
}
