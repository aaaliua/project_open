package com.chiemy.pagetransformerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScreenSlideBgPageFragment extends Fragment {
	private int color = -1;
	int resources [] = {R.drawable.a, R.drawable.b, R.drawable.c};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_bgslide_page, container, false);
        int position = getArguments().getInt("position");
        ImageView iv = (ImageView) rootView.findViewById(R.id.bg_image);
        iv.setImageResource(resources[position%resources.length]);
        return rootView;
    }
    
    public void randomizeColor() {
    	color = Color.rgb((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255));
	}
}
