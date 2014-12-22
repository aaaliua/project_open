package com.dazhongcun.merchants.activity;


import com.dazhongcun.meifa.merchants.R;
import com.dazhongcun.utils.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

public class About extends ActionBarActivity {

	TextView mTextView;
	ImageView mImageView;

	private static final int RightToLeft = 1;
	private static final int LeftToRight = 2;
	private static final int DURATION = 30000;
	private RectF mDisplayRect = new RectF();
	private final Matrix mMatrix = new Matrix();
	private int mDirection = RightToLeft;
	private ValueAnimator mCurrentAnimator;
	private float mScaleFactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(0, 0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mainToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//		getSupportActionBar().setTitle("About");
		mTextView = (TextView) findViewById(R.id.about_body);
		mImageView = (ImageView) findViewById(R.id.image);
		moveBackground();
		mTextView.setText(Html.fromHtml(getString(R.string.about_cody,
				Utils.getVersionName(this))));
		mTextView.setMovementMethod(new LinkMovementMethod());
		
		
		mainToolbar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (Utils.hasHoneycomb()) {
			View demoContainerView = findViewById(R.id.image);
			demoContainerView.setAlpha(0);
			ViewPropertyAnimator animator = demoContainerView.animate();
			animator.alpha(1);
			if (Utils.hasICS()) {
				animator.setStartDelay(250);
			}
			animator.setDuration(1000);
		}
	}

	protected void moveBackground() {
		if (Utils.hasHoneycomb()) {
			mImageView.post(new Runnable() {
				@Override
				public void run() {
					mScaleFactor = (float) mImageView.getHeight()
							/ (float) mImageView.getDrawable()
									.getIntrinsicHeight();
					mMatrix.postScale(mScaleFactor, mScaleFactor);
					mImageView.setImageMatrix(mMatrix);
					animate();
				}
			});
		}
	}

	private void animate() {
		updateDisplayRect();
		if (mDirection == RightToLeft) {
			animate(mDisplayRect.left, mDisplayRect.left
					- (mDisplayRect.right - mImageView.getWidth()));
		} else {
			animate(mDisplayRect.left, 0.0f);
		}
	}

	private void updateDisplayRect() {
		mDisplayRect.set(0, 0, mImageView.getDrawable().getIntrinsicWidth(),
				mImageView.getDrawable().getIntrinsicHeight());
		mMatrix.mapRect(mDisplayRect);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void animate(float from, float to) {
		mCurrentAnimator = ValueAnimator.ofFloat(from, to);
		mCurrentAnimator
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float value = (Float) animation.getAnimatedValue();

						mMatrix.reset();
						mMatrix.postScale(mScaleFactor, mScaleFactor);
						mMatrix.postTranslate(value, 0);

						mImageView.setImageMatrix(mMatrix);

					}
				});
		mCurrentAnimator.setDuration(DURATION);
		mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				if (mDirection == RightToLeft) {

					mDirection = LeftToRight;
					// mImageView.setImageResource(R.drawable.santander2);
				} else {

					mDirection = RightToLeft;
					// mImageView.setImageResource(R.drawable.santander);
				}

				animate();
			}
		});
		mCurrentAnimator.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, 0);
	}
}
