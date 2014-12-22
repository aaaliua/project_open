package com.linju.android_property.entity;

import java.io.Serializable;
import java.util.List;

public class Content_menu implements Serializable{

	
	
	
	
	
	public static final String JSON_MAP = "content";
	public static final String CONTENT_ID = "content_id";
	public static final String CONTENT_TITLE = "content_title";
	public static final String CONTENT_MENU = "content_menu";
	
	private String contentID;
	private String contentTitle;
	private List<Content_menu_child> menus;
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public List<Content_menu_child> getMenus() {
		return menus;
	}
	public void setMenus(List<Content_menu_child> menus) {
		this.menus = menus;
	}
	
	
	
}
