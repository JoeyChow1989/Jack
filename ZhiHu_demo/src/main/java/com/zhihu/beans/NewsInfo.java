package com.zhihu.beans;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

//{"image":"http:\/\/pic4.zhimg.com\/4308af5f6276399b3d230d951b6f00e7.jpg","type":0,"id":8215475,"ga_prefix":"042617","title":"用「泡学」把妹撩汉的他们，奔向了幸福的反面"}

public class NewsInfo{
	
	private String image;
	private int type;
	private int id;
	private int ga_prefix;
	private String title;
	private SoftReference<Bitmap> imageBitmap;
	
	@Override
	public String toString(){
		return "image"+image + "type" + type+ "ga_prefix"+ ga_prefix+ "title" +title;
	}
	
	public SoftReference<Bitmap> getImageBitmap() {
		return imageBitmap;
	}
	public void setImageBitmap(SoftReference<Bitmap> imageBitmap) {
		this.imageBitmap = imageBitmap;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(int ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}