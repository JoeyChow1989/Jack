package com.zhihu.activity;

import java.net.URL;

import com.zhihu.R;
import com.zhihu.beans.NewsDetail;
import com.zhihu.utils.HttpUtils;
import com.zhihu.utils.JsonUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsActivity extends Activity implements OnClickListener {

	// 新闻获取接口
	public static final String NEWS_PATH_START = "http://news-at.zhihu.com/api/4/news/";

	private static final int UPDATE_IMAGEVIEW = 0;
	private static final int UPDATE_TEXTVIEW = 1;
	private static final int UPDATE_BODYTEXT = 2;

	// 标题栏按钮部分
	private LinearLayout backBtn;
	private LinearLayout shareBtn;
	private LinearLayout collectBtn;
	private LinearLayout commentBtn;
	private LinearLayout praiseBtn;

	// 组件定义
	private TextView newsTitle;
	private TextView newsImageResource;
	private ImageView newsImage;
	private TextView bodyText;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == UPDATE_TEXTVIEW) {
				NewsDetail newsDetail =(NewsDetail)msg.obj;
				String title = newsDetail.getTitle();
				String image_source = newsDetail.getImage_source();
				newsTitle.setText(title);
				newsImageResource.setText(image_source);
			}else if(msg.what == UPDATE_IMAGEVIEW){
				Bitmap bitmap = (Bitmap)msg.obj;
				newsImage.setImageBitmap(bitmap);
			}else if(msg.what == UPDATE_BODYTEXT){
				bodyText.setText((Spanned) msg.obj);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// 设置阅读模式
		setDayOrNightMode();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		// 获取新闻的接口Id
		Intent intent = getIntent();
		int newsId = intent.getIntExtra("id", 0);

		// 获取新闻内容
		if (newsId != 0)
			getURLData(newsId);

		// 初始化界面更新
		initView();
	}

	// 初始化界面
	private void initView() {
		bodyText = (TextView) findViewById(R.id.bodyText);
		newsImage = (ImageView)findViewById(R.id.newsImage);
		newsImageResource = (TextView)findViewById(R.id.newsImageResource);
		newsTitle = (TextView)findViewById(R.id.newsTitle);

		backBtn = (LinearLayout) findViewById(R.id.backBtn);
		shareBtn = (LinearLayout) findViewById(R.id.shareBtn);
		collectBtn = (LinearLayout) findViewById(R.id.collectBtn);
		commentBtn = (LinearLayout) findViewById(R.id.commentBtn);
		praiseBtn = (LinearLayout) findViewById(R.id.praiseBtn);

		backBtn.setOnClickListener(this);
		shareBtn.setOnClickListener(this);
		collectBtn.setOnClickListener(this);
		commentBtn.setOnClickListener(this);
		praiseBtn.setOnClickListener(this);
	}

	// 设置阅读模式
	private void setDayOrNightMode() {
		if (getIsNightMode()) {
			setTheme(R.style.AppTheme_Night);
		} else {
			setTheme(R.style.AppTheme_Day);
		}
	}

	// 获取阅读模式
	private boolean getIsNightMode() {
		SharedPreferences sp = getSharedPreferences("userInfo", 0);
		return sp.getBoolean("isNightMode", false);
	}

	// 获取新闻内容
	private void getURLData(int newsId) {
		final String path = NEWS_PATH_START + newsId;
		new Thread() {
			public void run() {
				String jsonData = HttpUtils.getJsonData(path);
				NewsDetail newsDetail = JsonUtils.getNewsDetail(jsonData);

				// 获取完数据发回主线程更新
				Message msg = new Message();
				msg.what = UPDATE_TEXTVIEW;
				msg.obj = newsDetail;
				handler.sendMessage(msg);

				//获取标题图片
				String image = newsDetail.getImage();
				getTitleImage(image);

				//获取主体新闻body
				String bodyData = newsDetail.getBody();
				getBodySpanned(bodyData);
			}
		}.start();
	}

	//获取标题图片
	private void getTitleImage(final String image){
		new Thread(){
			public void run(){
				Bitmap bitmap = HttpUtils.getBitmap(image);
				// 获取完数据发回主线程更新
				Message msg = new Message();
				msg.what = UPDATE_IMAGEVIEW;
				msg.obj = bitmap;
				handler.sendMessage(msg);
			}
		}.start();
	}

	//获取主体新闻body
	private void getBodySpanned(String bodyData){
		ImageGetter imgGetter = new Html.ImageGetter() {
			public Drawable getDrawable(String source) {
				Drawable drawable = null;
				URL url;
				try {
					url = new URL(source);
					drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
				} catch (Exception e) {
					return null;
				}
				//改变图片大小
				int height = drawable.getIntrinsicHeight();
				int width = drawable.getIntrinsicWidth();
				if(width < 100){
					drawable.setBounds(0, 0, 40, 40);
				}else{
					double scale = (WelcomeActivity.windowWidth-40)/width;
					width = WelcomeActivity.windowWidth-40;
					height = (int)scale*height;
					drawable.setBounds(0, 0, width, height);
				}
				return drawable;
			}
		};
		Spanned html = Html.fromHtml(bodyData, imgGetter, null);
		// 获取完数据发回主线程更新
		Message msg = new Message();
		msg.what = UPDATE_BODYTEXT;
		msg.obj = html;
		handler.sendMessage(msg);
	}

	// 点击事件设置
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.backBtn:
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				break;
			case R.id.commentBtn:
				break;
			case R.id.collectBtn:
				break;
			case R.id.shareBtn:
				break;
			case R.id.praiseBtn:
				break;
			default:
		}
	}
}
