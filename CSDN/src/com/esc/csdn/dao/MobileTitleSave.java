package com.esc.csdn.dao;

public class MobileTitleSave {
	
	private int id;
	private String titleUrl;
	private String title;
	public MobileTitleSave(String titleUrl, String title) {
		super();
		this.titleUrl = titleUrl;
		this.title = title;
	}
	public String getTitleUrl() {
		return titleUrl;
	}
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MobileTitleSave() {
	}

	
	
	
}
