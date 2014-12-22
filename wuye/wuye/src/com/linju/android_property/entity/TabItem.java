package com.linju.android_property.entity;

import android.content.Intent;
import android.os.Bundle;

public class TabItem {
	private String title;		// tab item title
	private int icon;			// tab item icon
	private int bg;			// tab item background
	private Intent intent;	// tab item intent
	private Class<?> fragment; // tab item fragment
	private Bundle bunder;	// tab item bunder
	private boolean isGone;	// tab item gone or not
	
	public TabItem(String title, int icon, int bg, Intent intent, Bundle bundle, boolean isGone) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.intent = intent;
		this.bunder = bundle;
		this.isGone = isGone;
	}
	
	public TabItem(String title, int icon, int bg, Class<?> fragment, Bundle bundle, boolean isGone) {
		super();
		this.title = title;
		this.icon = icon;
		this.bg = bg;
		this.fragment = fragment;
		this.bunder = bundle;
		this.isGone = isGone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getBg() {
		return bg;
	}

	public void setBg(int bg) {
		this.bg = bg;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public Class<?> getFragment() {
		return fragment;
	}

	public void setFragment(Class<?> fragment) {
		this.fragment = fragment;
	}

	public boolean isGone() {
		return isGone;
	}

	public void setGone(boolean isGone) {
		this.isGone = isGone;
	}

	public Bundle getBunder() {
		return bunder;
	}

	public void setBunder(Bundle bunder) {
		this.bunder = bunder;
	}
}
