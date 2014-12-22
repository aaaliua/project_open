package com.chiemy.pagetransformerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScreenSlidePageFragment extends Fragment {
	private int color = -1;
	private int position;
	public ScreenSlidePageFragment(int position) {
		// TODO Auto-generated constructor stub
		this.position = position;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    	View v = view.findViewById(R.id.content);
    	v.setBackgroundColor(randomizeColor());
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    }
    public int randomizeColor() {
    	color = Color.rgb((int) (Math.random() * 255),
				(int) (Math.random() * 255), (int) (Math.random() * 255));
    	return color;
	}
}
