package com.linju.android_property.entity;

import java.io.Serializable;
import java.util.List;


public class Notice_Manage_Bean implements Serializable {
	// id，title, content，updated_at，good_count，bad_count，images：{id， image_url}
	private String id;
	private String title;
	private String content;
	private String updated_at;
	private String good_count;
	private String bad_count;
	private List<ImageBean> images;



	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getUpdated_at() {
		return updated_at;
	}


	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}


	public String getGood_count() {
		return good_count;
	}


	public void setGood_count(String good_count) {
		this.good_count = good_count;
	}


	public String getBad_count() {
		return bad_count;
	}


	public void setBad_count(String bad_count) {
		this.bad_count = bad_count;
	}


	public List<ImageBean> getImages() {
		return images;
	}


	public void setImages(List<ImageBean> images) {
		this.images = images;
	}




	
}
