package com.flyou.girls.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flyou.girls.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDetialActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView mDownLoad;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private TextView mLoading;
    private String mImageurl;
    private final static String ALBUM_PATH = Environment
            .getExternalStorageDirectory() + "/girls/";
    private Thread saveThread;

    private Bitmap mBitmap;
    private String mFileName;
    private String mSaveMessage;
    private Thread connectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏

        setContentView(R.layout.activity_image_detial);
        initView();
        initDate();

        connectThread = new Thread(connectNet);
        connectThread.start();

    }

    private void initDate()
    {
        mImageurl = getIntent().getStringExtra("imageurl");
        Glide.with(ImageDetialActivity.this)
                .load(mImageurl)
//                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>()
                {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
                    {
                        mLoading.setVisibility(View.GONE);
                        mDownLoad.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(mImageView);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.update();


    }

    private void initView()
    {
        mDownLoad = (TextView) findViewById(R.id.download);
        mLoading = (TextView) findViewById(R.id.laoding);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mDownLoad.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.download:
                saveThread = new Thread(saveFileRunnable);
                saveThread.start();
                break;
        }
    }

    private Runnable connectNet = new Runnable()
    {

        @Override
        public void run()
        {
            try
            {
                String filePath = mImageurl;
                mFileName = System.currentTimeMillis() + ".jpg";
                // 取得的是inputstream，直接从inputstream生成bitmap
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                // 发送消息，通知handler在主线程中更新ui
                connectHanlder.sendEmptyMessage(0);
            } catch (Exception e)
            {
                Toast.makeText(ImageDetialActivity.this, "无法链接网络！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    /*
     * 从网络获取图片
     */
    protected InputStream getImageStream(String path) throws Exception
    {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            return conn.getInputStream();
        }
        return null;
    }

    private Handler connectHanlder = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            // 更新UI，显示图片
            if (mBitmap != null)
            {
                mImageView.setImageBitmap(mBitmap);// display image
            }
        }
    };

    private Runnable saveFileRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            try
            {
                saveFile(mBitmap, mFileName);
                mSaveMessage = "图片保存成功！";
            } catch (Exception e)
            {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };

    /*
     * 保存文件
     */
    protected void saveFile(Bitmap bm, String fileName) throws IOException
    {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists())
        {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    private Handler messageHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(ImageDetialActivity.this, mSaveMessage,
                    Toast.LENGTH_SHORT).show();
        }
    };
}
