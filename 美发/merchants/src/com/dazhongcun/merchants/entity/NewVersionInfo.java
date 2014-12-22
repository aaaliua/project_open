package com.dazhongcun.merchants.entity;

/**
 * 新版本信息bean
 * @author gonglei
 *
 */
public class NewVersionInfo {
	/**
	 * 版本号
	 */
	private String version = "";
	/**
	 * 新版本下载url
	 */
	private String url = "";
	/**
	 * 新版本更新描述
	 */
	private String description = "";

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
