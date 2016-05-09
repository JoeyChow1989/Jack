package com.zhihu.beans;

import java.util.List;

public class NewsDetail {
//	body:
//"image_source":"Up in the Air",
//"title":"自用手机在德国机场被扣税两千多？看清规矩就能避免",
//"image":"http:\/\/pic3.zhimg.com\/206b8b2cea68042b8ced4f22706948a6.jpg",
//"share_url":"http:\/\/daily.zhihu.com\/story\/8219339",
//"js":[],
//"ga_prefix":"042813",
//"images":["http:\/\/pic3.zhimg.com\/2caf2245e4ce56c8178e47450453b50e.jpg"],
//"type":0,
//"id":8219339,
//"css":["http:\/\/news-at.zhihu.com\/css\/news_qa.auto.css?v=4b3e3"]
	private String body;
	private String image_source;
	private String title;
	private String image;
	private String share_url;
	private List<String> js;
	private String ga_prefix;
	private List<String> images;
	private int type;
	private int id;
	private List<String> css;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getImage_source() {
		return image_source;
	}
	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public List<String> getJs() {
		return js;
	}
	public void setJs(List<String> js) {
		this.js = js;
	}
	public String getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
	
	
	

}
