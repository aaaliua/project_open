
package com.tiger.quicknews.wedget.discrollview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.tiger.quicknews.R;

public class DiscrollvableLastLayout extends RelativeLayout implements Discrollvable {

    private static final String TAG = "DiscrollvableLastLayout";

    private View mLastView1;
    private View mLastView2;
    private View mLastView3;
    // private View mLastView4;

    private float mLastView1TranslationX;
    private float mLastView2TranslationX;
    private float mLastView3TranslationX;
    // private float mLastView4TranslationX;

    private float mLastView1TranslationY;
    private float mLastView2TranslationY;
    private float mLastView3TranslationY;

    // private float mLastView4TranslationY;

    public DiscrollvableLastLayout(Context context) {
        super(context);
    }

    public DiscrollvableLastLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollvableLastLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLastView1 = findViewById(R.id.lastView1);
        mLastView2 = findViewById(R.id.lastView2);
        mLastView3 = findViewById(R.id.lastView3);
        // mLastView4 = findViewById(R.id.lastView4);

        mLastView1TranslationX = mLastView1.getTranslationX();
        mLastView2TranslationX = mLastView2.getTranslationX();
        mLastView3TranslationX = mLastView3.getTranslationX();
        // mLastView4TranslationX = mLastView4.getTranslationX();

        mLastView1TranslationY = mLastView1.getTranslationY();
        mLastView2TranslationY = mLastView2.getTranslationY();
        mLastView3TranslationY = mLastView3.getTranslationY();
        // mLastView4TranslationY = mLastView4.getTranslationY();

    }

    @Override
    public void onResetDiscrollve() {
        mLastView1.setAlpha(0);
        mLastView1.setTranslationX(mLastView1TranslationX);
        mLastView1.setTranslationY(mLastView1TranslationY);
        mLastView2.setAlpha(0);
        mLastView2.setTranslationX(mLastView2TranslationX);
        mLastView2.setTranslationY(mLastView2TranslationY);
        mLastView3.setAlpha(0);
        mLastView3.setTranslationX(mLastView3TranslationX);
        mLastView3.setTranslationY(mLastView3TranslationY);
        // mLastView4.setAlpha(0);
        // mLastView4.setTranslationX(mLastView4TranslationX);
        // mLastView4.setTranslationY(mLastView4TranslationY);
    }

    @Override
    public void onDiscrollve(float ratio) {
        mLastView1.setTranslationX(mLastView1TranslationX * (1 - ratio));
        mLastView1.setTranslationY(mLastView1TranslationY * (1 - ratio));
        mLastView1.setAlpha(ratio);

        mLastView2.setTranslationX(mLastView2TranslationX * (1 - ratio));
        mLastView2.setTranslationY(mLastView2TranslationY * (1 - ratio));
        mLastView2.setAlpha(ratio);

        mLastView3.setTranslationX(mLastView3TranslationX * (1 - ratio));
        mLastView3.setTranslationY(mLastView3TranslationY * (1 - ratio));
        mLastView3.setAlpha(ratio);

        // mLastView4.setTranslationX(mLastView4TranslationX * (1 - ratio));
        // mLastView4.setTranslationY(mLastView4TranslationY * (1 - ratio));
        // mLastView4.setAlpha(ratio);
    }
}
