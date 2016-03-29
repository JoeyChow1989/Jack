package com.test.zxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_create,btn_scanner;
    private ImageView imageView;
    private EditText et;
    private String time;
    private File file = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_scanner = (Button) findViewById(R.id.btn_scanner);
        imageView = (ImageView) findViewById(R.id.image);
        et = (EditText) findViewById(R.id.editText);
        btn_create.setOnClickListener(this);
        btn_scanner.setOnClickListener(this);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                saveCurrentImage();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                String msg = et.getText().toString();
                if(TextUtils.isEmpty(msg)){
                    Toast.makeText(MainActivity.this, "请输入信息", Toast.LENGTH_LONG).show();
                    return;
                }
                //生成二维码图片，第一个参数是二维码的内容，第二个参数是正方形图片的边长，单位是像素
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtil.createQRCode(msg, 400);
                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);
                break;

            case R.id.btn_scanner:
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    //这种方法状态栏是空白，显示不了状态栏的信息
    private void saveCurrentImage()
    {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        //找到当前页面的根布局
        View view =  getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片,创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        temBitmap = view.getDrawingCache();
        SimpleDateFormat df = new SimpleDateFormat("yyyymmddhhmmss");
        time = df.format(new Date());
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen",time + ".png");
            if(!file.exists()){
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen/" + time + ".png";
                    final Result result = parseQRcodeBitmap(path);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).start();
            //禁用DrawingCahce否则会影响性能 ,而且不禁止会导致每次截图到保存的是缓存的位图
            view.setDrawingCacheEnabled(false);
        }
    }

    //解析二维码图片,返回结果封装在Result对象中
    private com.google.zxing.Result  parseQRcodeBitmap(String bitmapPath){
        //解析转换类型UTF-8
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath,options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = options.outHeight / 400;
        if(options.inSampleSize <= 0){
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }
}
