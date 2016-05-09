package com.zhihu.beans;

import java.util.List;

//{
//	"date":"20160427","stories":[
//{"images":["http:\/\/pic2.zhimg.com\/661cb9a906168f0b3a3f8a171beb382d.jpg"],"type":0,"id":8221013,"ga_prefix":"042715","title":"顶尖科学家说，得癌症只是因为「运气不好」？"},
//{"images":["http:\/\/pic1.zhimg.com\/1f059bd0e3c3e32e6fe0dc1105057fc8.jpg"],"type":0,"id":8221192,"ga_prefix":"042714","title":"这家商场出了个规定，让顾客自己选想去男厕所还是女厕所"},
//{"images":["http:\/\/pic1.zhimg.com\/52abf4516ff296f165c7bab61da5e818.jpg"],"type":0,"id":8219522,"ga_prefix":"042713","title":"了解这个病，或许可以避免一些儿童睾丸坏死的悲剧"},
//{"images":["http:\/\/pic2.zhimg.com\/43e509f3cbed4b053cfe6611a7868c3d.jpg"],"type":0,"id":8219938,"ga_prefix":"042712","title":"如果有 500 万，你是买学区房还是移民？"},
//{"images":["http:\/\/pic2.zhimg.com\/b9bdccc020565def9c72655016fb0b51.jpg"],"type":0,"id":8211883,"ga_prefix":"042711","title":"女友警告我「不要用你的心理学来分析我」，怎么办？"},
//{"images":["http:\/\/pic1.zhimg.com\/7fd5407d551e4a5306f2ac6427e16fd8.jpg"],"type":0,"id":8215887,"ga_prefix":"042710","title":"我是一个签证官，每天都在听各种中国人讲自己的故事"},
//{"images":["http:\/\/pic2.zhimg.com\/da7a687744be98f5c13bde46d3f23dd9.jpg"],"type":0,"id":8210456,"ga_prefix":"042709","title":"我也想为环保尽一份力，于是找到了他们"},
//{"images":["http:\/\/pic3.zhimg.com\/d53ab4467b9125f138882276ad76c176.jpg"],"type":0,"id":8219136,"ga_prefix":"042708","title":"在美美的山水画里，两个小人打来打去真的好吗？"},
//{"images":["http:\/\/pic2.zhimg.com\/46389e957cbe7d6f26b547ed29dbcf31.jpg"],"type":0,"id":8212950,"ga_prefix":"042707","title":"在澳洲买一堆房的中国土豪，和买不起房的澳洲人民"},
//{"images":["http:\/\/pic3.zhimg.com\/6b65c99e30c5fc33d9c16670dedbe78a.jpg"],"type":0,"id":8217961,"ga_prefix":"042707","title":"在中国，黑客真的能侵入银行？那得看是什么银行"},
//{"images":["http:\/\/pic2.zhimg.com\/e23f046541948f82c32724790e368c49.jpg"],"type":0,"id":8199208,"ga_prefix":"042707","title":"当领导这件事，女性天生比比男性差吗？"},
//{"images":["http:\/\/pic3.zhimg.com\/13b5173bb8ff37c51841d61fcc6beaba.jpg"],"type":0,"id":8219299,"ga_prefix":"042707","title":"读读日报 24 小时热门 TOP 5 · 我以为这种事离自己很远"},
//{"images":["http:\/\/pic1.zhimg.com\/3ccd39a9d57f9b18a1d38c15d3196f44.jpg"],"type":0,"id":8206687,"ga_prefix":"042706","title":"瞎扯 · 如何正确地吐槽"},
//{"images":["http:\/\/pic2.zhimg.com\/950041d23d0f6c07de87d53e34fef6a1.jpg"],"type":0,"id":8217201,"ga_prefix":"042706","title":"这里是广告 · 25W，如何从北京车展选一款品味之车？"}],
//	"top_stories":[
//{"image":"http:\/\/pic3.zhimg.com\/9d9ec0d6d48dcbafa4f61e040770ef4e.jpg","type":0,"id":8221192,"ga_prefix":"042714","title":"这家商场出了个规定，让顾客自己选想去男厕所还是女厕所"},
//{"image":"http:\/\/pic3.zhimg.com\/6109e2641b0b0c4c7b4db4f2a627130e.jpg","type":0,"id":8212950,"ga_prefix":"042707","title":"在澳洲买一堆房的中国土豪，和买不起房的澳洲人民"},
//{"image":"http:\/\/pic1.zhimg.com\/639d2ed1737f6e8c1cc37c2cf193d69c.jpg","type":0,"id":8219299,"ga_prefix":"042707","title":"读读日报 24 小时热门 TOP 5 · 我以为这种事离自己很远"},
//{"image":"http:\/\/pic2.zhimg.com\/5e5dd386b75275867c77ede7762a28fd.jpg","type":0,"id":8215887,"ga_prefix":"042710","title":"我是一个签证官，每天都在听各种中国人讲自己的故事"},
//{"image":"http:\/\/pic4.zhimg.com\/4308af5f6276399b3d230d951b6f00e7.jpg","type":0,"id":8215475,"ga_prefix":"042617","title":"用「泡学」把妹撩汉的他们，奔向了幸福的反面"}


public class LatstNewsInfo {
	
	private String date;
	private List<NewsInfo> stories;
	private List<NewsInfo> top_stories;
	
	@Override
	public String toString(){
		return "date"+date + "stories" + stories+ "top_stories"+ top_stories;
	}
	
	public String getDate() {
		return date;
	}
	public void setData(String date) {
		this.date = date;
	}
	public List<NewsInfo> getStories() {
		return stories;
	}
	public void setStories(List<NewsInfo> stories) {
		this.stories = stories;
	}
	public List<NewsInfo> getTop_stories() {
		return top_stories;
	}
	public void setTop_stories(List<NewsInfo> top_stories) {
		this.top_stories = top_stories;
	}
	
}


