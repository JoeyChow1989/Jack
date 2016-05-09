package com.zhihu.activity;

import com.zhihu.R;
import com.zhihu.utils.HttpUtils;
import com.zhihu.utils.JsonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	public static final String WELCOME_PATH = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

	private static final int UPDATE_IMAGE = 0;

	// 屏幕高宽
	public static int windowWidth;
	public static int windowHeight;

	private ImageView welcomeImage;

	private Bitmap welcomeBitmap;

	// 更新获取到的图片并开始动画
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == UPDATE_IMAGE) {
				welcomeImage.setImageBitmap((Bitmap) msg.obj);
				// 设置动画
				AnimationSet anim = new AnimationSet(false);
				ScaleAnimation sAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				sAnim.setDuration(3000);
				AlphaAnimation aAnim = new AlphaAnimation(1.0f, 0.5f);
				aAnim.setDuration(3000);
				anim.addAnimation(sAnim);
				anim.addAnimation(aAnim);
				welcomeImage.startAnimation(anim);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		//网络连接判断
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if(networkInfo != null){
			if(networkInfo.isAvailable()){
				if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
					Toast.makeText(this, "wifi网络", 0).show();
				}else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
					Toast.makeText(this, "2G/3G网络", 0).show();
				}else{
					Toast.makeText(this, "其他网络", 0).show();
				}
			}else{
				Toast.makeText(this, "当前网络不可用", 0).show();
			}
		}else{
			Toast.makeText(this, "无网络连接", 0).show();
		}

		// 获取屏幕高宽s
		WindowManager wm = this.getWindowManager();
		windowHeight = wm.getDefaultDisplay().getHeight();
		windowWidth = wm.getDefaultDisplay().getWidth();

		// 初始化视图界面
		initView();

		// 获取欢迎界面图像
		new Thread() {
			public void run() {
				String jsonData = HttpUtils.getJsonData(WELCOME_PATH);
				if (jsonData == null) {

				} else {
					String BitmapPath = JsonUtils.getWelcomeImagePath(jsonData);
					welcomeBitmap = HttpUtils.getBitmap(BitmapPath);
					Message msg = new Message();
					msg.what = UPDATE_IMAGE;
					msg.obj = welcomeBitmap;
					handler.sendMessage(msg);
				}
			}
		}.start();

		// 3秒后关闭欢迎界面
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
			}
		}.start();

	}

	private void initView() {
		welcomeImage = (ImageView) findViewById(R.id.welcomeImage);
	}

}
