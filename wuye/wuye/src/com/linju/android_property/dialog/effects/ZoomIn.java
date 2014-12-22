package com.linju.android_property.dialog.effects;

import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.nineoldandroids.animation.ObjectAnimator;

public class ZoomIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
        		ObjectAnimator.ofFloat(view,"scaleX",0.45f,1.0f),
                ObjectAnimator.ofFloat(view,"scaleY",0.45f,1.0f),
                ObjectAnimator.ofFloat(view,"alpha",0,1)

        );
    }
}

