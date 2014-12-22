package com.linju.android_property.entity;

/**
 * 首页的九宫格类似
 * @author Administrator
 *
 */
public class UndEntity {

	
	private long headerId;   //标题的ID
	private int titleIcon;  //标题的icon
	private String childId;  //子ID
	private String title;   //标题
	private String childtitle; //子内容的文字
	private int childIcon;    //子内容的icon
	
	
	/**
	 * 
	 * @param headerId  组id
	 * @param titleIcon 标题图标/子ID
	 * @param title     标题
	 * @param childtitle  子标题
	 * @param childIcon  子标题的icon
	 */
	public UndEntity(long headerId, int titleIcon, String title,
			String childtitle, int childIcon) {
		super();
		this.headerId = headerId;
		this.titleIcon = titleIcon;
		this.title = title;
		this.childtitle = childtitle;
		this.childIcon = childIcon;
	}
	public UndEntity(long headerId, String childId, String title,
			String childtitle, int childIcon) {
		super();
		this.headerId = headerId;
		this.childId = childId;
		this.title = title;
		this.childtitle = childtitle;
		this.childIcon = childIcon;
	}
	
	
	public String getChildId() {
		return childId;
	}
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public long getHeaderId() {
		return headerId;
	}
	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}
	public int getTitleIcon() {
		return titleIcon;
	}
	public void setTitleIcon(int titleIcon) {
		this.titleIcon = titleIcon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChildtitle() {
		return childtitle;
	}
	public void setChildtitle(String childtitle) {
		this.childtitle = childtitle;
	}
	public int getChildIcon() {
		return childIcon;
	}
	public void setChildIcon(int childIcon) {
		this.childIcon = childIcon;
	}
	
	
	
}
