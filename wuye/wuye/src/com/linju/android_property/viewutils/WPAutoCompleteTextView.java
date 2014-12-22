package com.linju.android_property.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class WPAutoCompleteTextView extends AutoCompleteTextView{

	public WPAutoCompleteTextView(Context context) {
		super(context);
		TypefaceCache.setCustomTypeface(context, this, null);
	}
	
	public WPAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypefaceCache.setCustomTypeface(context, this, attrs);
	}


	public WPAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		TypefaceCache.setCustomTypeface(context, this, attrs);
	}

}
