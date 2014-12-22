package com.linju.android_property.viewutils;

import com.linju.android_property2.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * 自定义的LinearLayout  根据组件多少  隐藏做出动画  
 * 需要设置该view的animateLayoutChanges = true  跟需要隐藏显示的view的animateLayoutChanges
 * @author Administrator
 *
 */
public class WPLinearLayoutSizeBound extends LinearLayout {
    private final int mMaxWidth;
    private final int mMaxHeight;

    public WPLinearLayoutSizeBound(Context context) {
        super(context);
        mMaxWidth = 0;
        mMaxHeight = 0;
    }

    public WPLinearLayoutSizeBound(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.WPLinearLayoutSizeBound);
        mMaxWidth = a.getDimensionPixelSize(R.styleable.WPLinearLayoutSizeBound_maxWidth,
                Integer.MAX_VALUE);
        mMaxHeight = a.getDimensionPixelSize(R.styleable.WPLinearLayoutSizeBound_maxHeight,
                Integer.MAX_VALUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (mMaxWidth > 0 && mMaxWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, measureMode);
        }
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mMaxHeight > 0 && mMaxHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, measureMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
