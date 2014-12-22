package com.linju.android_property.entity;

import java.io.Serializable;
import java.util.List;

public class ComplaintBean implements Serializable {
	public static final String STATUE_DONE = "已处理";
	public static final String STATUE_UNDONE = "待处理";
	// {id，title，content，created_at,status,feedback,images{id, image}})
	private String id;
	private String title;
	private String content;
	private String created_at;
	private String status;
	private String feedback;
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

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public List<ImageBean> getImages() {
		return images;
	}

	public void setImages(List<ImageBean> images) {
		this.images = images;
	}

	public ComplaintBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplaintBean(String id, String title, String content,
			String created_at, String status, String feedback, List<ImageBean> images) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.created_at = created_at;
		this.status = status;
		this.feedback = feedback;
		this.images = images;
	}


}
