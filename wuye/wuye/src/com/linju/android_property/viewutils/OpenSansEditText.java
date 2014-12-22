package com.linju.android_property.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义的edittext   使用assets的字体
 * @author Administrator
 *
 */
public class OpenSansEditText extends EditText {
    public OpenSansEditText(Context context) {
        super(context);
        TypefaceCache.setCustomTypeface(context, this, null);
    }

    public OpenSansEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypefaceCache.setCustomTypeface(context, this, attrs);
    }

    public OpenSansEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypefaceCache.setCustomTypeface(context, this, attrs);
    }
}
