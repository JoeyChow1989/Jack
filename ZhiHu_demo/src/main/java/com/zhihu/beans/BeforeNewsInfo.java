package com.zhihu.beans;

import java.util.List;

public class BeforeNewsInfo {

	private String date;
	private List<NewsInfo> stories;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<NewsInfo> getStories() {
		return stories;
	}
	public void setStories(List<NewsInfo> stories) {
		this.stories = stories;
	}
	
}
