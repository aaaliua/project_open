package com.linju.android_property.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class TabItem extends TextView {

	// 放大动画特效
	private ScaleAnimation mBigAnimation;

	public TabItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initCustomTabItem();
	}

	public TabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initCustomTabItem();
	}

	public TabItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initCustomTabItem();
	}

	private void initCustomTabItem() {
		mBigAnimation = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mBigAnimation.setDuration(200);
		this.setOnTouchListener(otl);
	}

	private OnTouchListener otl = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
//			final int action = event.getAction();
//			if (MotionEvent.ACTION_DOWN == action) {
//				v.startAnimation(mBigAnimation);
//			}
			return false;
		}
	};

}
