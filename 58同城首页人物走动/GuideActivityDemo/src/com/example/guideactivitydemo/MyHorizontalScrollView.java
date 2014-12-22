package com.example.guideactivitydemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView {

	@Override
	public void fling(int velocityX) {
		// TODO Auto-generated method stub
		super.fling(velocityX / 4);
	}

	private Callbacks mCallbacks;

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mCallbacks != null) {
			mCallbacks.onScrollChanged(l);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mCallbacks != null) {
			switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mCallbacks.onDownMotionEvent();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mCallbacks.onUpOrCancelMotionEvent();
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

	public void setCallbacks(Callbacks listener) {
		mCallbacks = listener;
	}

	public static interface Callbacks {
		public void onScrollChanged(int scrollX);

		public void onDownMotionEvent();

		public void onUpOrCancelMotionEvent();
	}
}
