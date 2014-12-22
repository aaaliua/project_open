package com.linju.android_property.servicemanager;

import java.io.File;
import java.io.IOException;

import roboguice.inject.InjectView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.linju.android_photoUtils.Bimp;
import com.linju.android_photoUtils.GetPicActivity;
import com.linju.android_photoUtils.PhotoActivity;
import com.linju.android_property.application.AppApplication;
import com.linju.android_property.base.BaseActivity;
import com.linju.android_property.dialog.ActionSheetDialog;
import com.linju.android_property.dialog.AlertDialog;
import com.linju.android_property.dialog.Effectstype;
import com.linju.android_property.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.linju.android_property.dialog.ActionSheetDialog.SheetItemColor;
import com.linju.android_property.utils.FileUtils;
import com.linju.android_property.viewutils.NoScorllGridView;
import com.linju.android_property.viewutils.WPTextView;
import com.linju.android_property2.R;

/**
 * 添加公告activity
 * 
 * @author Administrator
 * 
 */
public class PostAdd extends BaseActivity implements OnClickListener {

	@InjectView(R.id.back)
	Button back;
	@InjectView(R.id.title)
	WPTextView titlebar;
	@InjectView(R.id.edit_or_add)
	Button editOrAdd;
	@InjectView(R.id.more)
	View more;

	@InjectView(R.id.Photogridview)
	NoScorllGridView noScorllGridView;

	private GridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_post_add);
		titlebar.setText(getString(R.string.post_add_title));
		editOrAdd.setText(getString(R.string.post));
		back.setOnClickListener(this);
		editOrAdd.setOnClickListener(this);
		initScrollView();
		
	}

	private void initScrollView() {
		noScorllGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScorllGridView.setAdapter(adapter);

		noScorllGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 判断是否点击的是最后一个
				if (arg2 == Bimp.bmp.size()) {
					new ActionSheetDialog(PostAdd.this)
							.builder()
							.setCancelable(false)
							.setCanceledOnTouchOutside(false)
							.addSheetItem(
									getString(R.string.app_select_camera),
									SheetItemColor.Blue,
									new OnSheetItemClickListener() {
										@Override
										public void onClick(int which) {
											//拍照选择
											photo();
										}
									})
							.addSheetItem(
									getString(R.string.app_select_phonePhoto),
									SheetItemColor.Blue,
									new OnSheetItemClickListener() {
										@Override
										public void onClick(int which) {
											//从相册选择
											Intent it = new Intent(PostAdd.this,GetPicActivity.class);
											startActivity(it);
										}
									}).show();
				} else {

				 //图片画廊
					Intent intent = new Intent(PostAdd.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			Bimp.bmp.clear();
			Bimp.drr.clear();
			Bimp.max = 0;
			Bimp.act_bool = true;
			FileUtils.deleteDir();
			onBackPressed();
			break;
		case R.id.edit_or_add:
			// 优先做判断 是否几个文本框有空值

			new AlertDialog(this)
					.builder()
					.setTitle(getString(R.string.post_add_title) + "?")
					// 用HTML工具类给某些文字加上颜色
					.setMsg("确认发布该条公告？").withDuration(120)
					.withEffect(Effectstype.Slideleft)
					.setPositiveButton("确认", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					}).show();

		default:
			break;
		}
	}

	private static String pathpic = "";
	private static final int TAKE_PICTURE = 0x000000;

	//拍照方法
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(
				AppApplication.mSdcardImageCamera + File.separator,
				String.valueOf(System.currentTimeMillis()) + ".jpg");
		pathpic = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < Bimp.MAXSIZE && resultCode == -1) {
				Bimp.drr.add(pathpic);
				//拍照后   是否需要执行 根据restart来做相应的操作  否则会执行两次  报数组越界
//				if (adapter != null) {
//					adapter.update();
//				}
			}
			break;

		}
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.add_item_hover));
				if (position == Bimp.MAXSIZE) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								if(!new File(path).exists()){
									return;  
								}
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								// Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {
								
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
		
	}
}
