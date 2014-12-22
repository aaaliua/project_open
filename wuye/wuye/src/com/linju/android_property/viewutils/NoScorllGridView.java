package com.linju.android_property.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoScorllGridView extends GridView{

	
	
	
	public NoScorllGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public NoScorllGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScorllGridView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	
	
}
