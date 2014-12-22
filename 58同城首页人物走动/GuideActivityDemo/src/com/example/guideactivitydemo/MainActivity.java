package com.example.guideactivitydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends Activity implements
		MyHorizontalScrollView.Callbacks {

	private ImageView sun;
	private ImageView man;
	private MyHorizontalScrollView horizontalScrollView;
	private int mScrollX; 
	private int step;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initAnimation();
	}

	private void initView() {
		sun = (ImageView) findViewById(R.id.sun_guide);
		man = (ImageView) findViewById(R.id.man_guide);
		horizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.scrollview);
		horizontalScrollView.setCallbacks(this);
		horizontalScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
//						onScrollChanged(horizontalScrollView.getScrollX());
					}
				});
	}

	private void initAnimation() {
		RotateAnimation animation = new RotateAnimation(0f, 360f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		LinearInterpolator lin = new LinearInterpolator();
		animation.setInterpolator(lin);
		animation.setDuration(1000 * 3);
		animation.setRepeatCount(-1);
		animation.setFillAfter(true);
		sun.startAnimation(animation);
	}

	@Override
	public void onScrollChanged(int scrollX) {
		// TODO Auto-generated method stub
		/**
		 * 小人的走动 根据变化值进行图片切换，图片跟步骤可以放进数组，这样方便 我这里简单做了实现没有优化代码
		 */
		if (Math.abs(scrollX - mScrollX) > 100) {
			mScrollX = scrollX;
			if (step == 0) {
				man.setImageResource(R.drawable.boy02);
				step = 1;
			} else if (step == 1) {
				man.setImageResource(R.drawable.boy03);
				step = 2;
			} else if (step == 2) {
				man.setImageResource(R.drawable.boy02);
				step = 3;
			} else if (step == 3) {
				man.setImageResource(R.drawable.boy01);
				step = 0;
			}

		}

		/**
		 * 文字的显示：根据滑到某个X值渐变出来 在滑到某个值消失
		 */
		if (scrollX > 100) {
              //渐变显示···
		}
		if (scrollX > 300) {
          //渐变消失
		}

		/**
		 * 云彩的显示：根据滑动得X值设图片的偏移量····
		 */
		
	}

	@Override
	public void onDownMotionEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpOrCancelMotionEvent() {
		// TODO Auto-generated method stub

	}

}
