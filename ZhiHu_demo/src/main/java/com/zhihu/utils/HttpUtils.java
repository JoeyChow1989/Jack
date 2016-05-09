package com.zhihu.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.zhihu.activity.WelcomeActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.WindowManager;

public class HttpUtils {
	
	public static String getJsonData(String path){
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if(code == 200){
				InputStream is = conn.getInputStream();
				BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while((line = bufr.readLine())!=null){
					sb.append(line);
				}
				return sb.toString();
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static Bitmap getBitmap(String path){
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code = conn.getResponseCode();
			if(code == 200){
				InputStream is = conn.getInputStream();
				byte[] data = getByteArray(is);
				Options options = new Options();
				options.inJustDecodeBounds = true;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
				
				int scale = 1;
				int scaleX = options.outWidth/WelcomeActivity.windowWidth;
				int scaleY = options.outHeight/WelcomeActivity.windowHeight;
				
				if(scaleX > scaleY && scaleX >1){
					scale = scaleX;
				}
				if(scaleY > scaleX && scaleY >1){
					scale = scaleX;
				}
				
				options.inJustDecodeBounds = false;
				options.inSampleSize = scale;
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
				return bitmap;
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getByteArray(InputStream is){
		if(is != null){
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while((len = is.read(buf))!=-1){
					os.write(buf,0,len);
				}
				return os.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
