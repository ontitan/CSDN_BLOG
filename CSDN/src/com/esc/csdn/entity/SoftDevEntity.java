package com.esc.csdn.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.netshull.csdn.R;

import com.esc.csdn.IParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SoftDevEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String titleUrl;
	private String pubTime;
	private String readCount;
	private String commentCount;
	private String picUrl;
	private String content;
	private List<String>tags;
	private Bitmap bitmap;
	public String getTitle() {
		return title;
	}


	



	public SoftDevEntity(String title, String titleUrl, String pubTime,
			String readCount, String commentCount, String picUrl,
			String content, List<String> tags,Bitmap bitmap) {
		this.title = title;
		this.titleUrl = titleUrl;
		this.pubTime = pubTime;
		this.readCount = readCount;
		this.commentCount = commentCount;
		this.picUrl = picUrl;
		this.content = content;
		this.tags = tags;
		this.bitmap = bitmap;
	}


	public SoftDevEntity(String title, String titleUrl, String pubTime,
			String readCount, String commentCount, String picUrl,
			String content, List<String> tags) {
		this.title = title;
		this.titleUrl = titleUrl;
		this.pubTime = pubTime;
		this.readCount = readCount;
		this.commentCount = commentCount;
		this.picUrl = picUrl;
		this.content = content;
		this.tags = tags;
	}




	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}





	public SoftDevEntity() {
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public String getReadCount() {
		return readCount;
	}
	public void setReadCount(String readCount) {
		this.readCount = readCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}






	public SoftDevEntity(String title, String titleUrl, String pubTime,
			String readCount, String picUrl, String content) {
		super();
		this.title = title;
		this.titleUrl = titleUrl;
		this.pubTime = pubTime;
		this.readCount = readCount;
		this.picUrl = picUrl;
		this.content = content;
	}
}
