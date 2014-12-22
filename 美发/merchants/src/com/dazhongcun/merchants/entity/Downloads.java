package com.dazhongcun.merchants.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "downloads")
public class Downloads implements Serializable {

	public static final String ID = "d_id";
	public static final String URL = "url";
	public static final String DIR_TYPE = "dir_type";
	public static final String SUB_PATH = "sub_path";
	public static final String SCANNING = "scanning";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String VISIBILITY = "visibility";
	public static final String NETWORK_TYPES = "network_types";
	public static final String MIME_TYPE = "mime_type";
	public static final String HEADER = "header";
	public static final String DOWNLOAD_ID = "download_id";
	public static final String DOWNLOAD_PKG = "download_pkg";
	
	@DatabaseField(generatedId = true,useGetSet=true,columnName=ID)
	private long id;
	//下载路径
	@DatabaseField(useGetSet=true,columnName=URL)
	private String url;
	//下载存储文件夹
	@DatabaseField(useGetSet=true,columnName=DIR_TYPE)
	private String dirType;
	//下载文件名
	@DatabaseField(useGetSet=true,columnName=SUB_PATH)
	private String subPath;
	//表示允许MediaScanner扫描到这个文件，默认不允许。
	@DatabaseField(useGetSet=true,columnName=SCANNING)
	private boolean scanning;
	//设置下载中通知栏提示的标题
	@DatabaseField(useGetSet=true,columnName=TITLE)
	private String title;
	//设置下载中通知栏提示的介绍
	@DatabaseField(useGetSet=true,columnName=DESCRIPTION)
	private String description;
	//表示下载进行中和下载完成的通知栏是否显示
	@DatabaseField(useGetSet=true,columnName=VISIBILITY)
	private int visibility;
	//表示下载允许的网络类型，默认在任何网络下都允许下载。
	@DatabaseField(useGetSet=true,columnName=NETWORK_TYPES)
	private int networkType;
	//设置下载文件的mineType。
	@DatabaseField(useGetSet=true,columnName=MIME_TYPE)
	private String mimeType;
	//添加请求下载的网络链接的http头
	@DatabaseField(useGetSet=true,columnName=HEADER)
	private String header;
	//下载完成后的id
	@DatabaseField(useGetSet=true,columnName=DOWNLOAD_ID)
	private long downloadId;
	//下载完成后打开该文件的包名
	@DatabaseField(useGetSet=true,columnName=DOWNLOAD_PKG)
	private String downloadPKG;
	
	public Downloads() {
		// TODO Auto-generated constructor stub
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDirType() {
		return dirType;
	}

	public void setDirType(String dirType) {
		this.dirType = dirType;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	public boolean getScanning() {
		return scanning;
	}

	public void setScanning(boolean scanning) {
		this.scanning = scanning;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getNetworkType() {
		return networkType;
	}

	public void setNetworkType(int networkType) {
		this.networkType = networkType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public long getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(long downloadId) {
		this.downloadId = downloadId;
	}

	public String getDownloadPKG() {
		return downloadPKG;
	}

	public void setDownloadPKG(String downloadPKG) {
		this.downloadPKG = downloadPKG;
	}

}
