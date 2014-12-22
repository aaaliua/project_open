package com.linju.android_property.viewutils;

import com.linju.android_property2.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Simplest custom view possible, using CircularProgressDrawable
 * 简单的loading图
 */
public class CustomView extends View {

  private CircularProgressDrawable mDrawable;

  public CustomView(Context context) {
    this(context, null);
  }

  public CustomView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
	try {
	    TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.circulprogress);
	    //获取圆圈大小 默认18
	    float dimes = a.getFloat(R.styleable.circulprogress_mdimes, 18);   
	    //获取颜色值 默认白色
	    int color = a.getColor(R.styleable.circulprogress_mcolor, Color.parseColor("#ffffff"));
	    //获取外圈动画时间
	    int angel_duration = a.getInt(R.styleable.circulprogress_mangel, 2000);
	    //获取自加自减的动画时间
	    int sweep_duration = a.getInt(R.styleable.circulprogress_msweep, 600);
	   //获取完成后  要记得释放
	    a.recycle();
	    //实例化圆圈
	    mDrawable = new CircularProgressDrawable(color, dimes,angel_duration,sweep_duration);
	    mDrawable.setCallback(this);
	}catch(Exception e){
		e.printStackTrace();
	}
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (visibility == VISIBLE) {
      mDrawable.start();
    } else {
      mDrawable.stop();
    }
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mDrawable.setBounds(0, 0, w, h);
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    mDrawable.draw(canvas);
  }

  @Override
  protected boolean verifyDrawable(Drawable who) {
    return who == mDrawable || super.verifyDrawable(who);
  }
}