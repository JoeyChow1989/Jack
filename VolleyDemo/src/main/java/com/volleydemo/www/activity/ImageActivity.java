package com.volleydemo.www.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.volleydemo.www.R;
import com.volleydemo.www.adapter.MyAdapter;
import com.volleydemo.www.appliaction.MyApplication;
import com.volleydemo.www.util.BitmapCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/25.
 */
public class ImageActivity extends Activity
{

    private ImageView mImageView;
    private String[] urls = {"http://d.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a558dd9025043fbf2b3118bc8.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/11385343fbf2b2110bc27944c98065380dd78ec8.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/908fa0ec08fa513d429e93d43e6d55fbb2fbd980.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/72f082025aafa40fdf42a050a964034f79f019b2.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/09fa513d269759eeebd40e46b0fb43166c22dfb3.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/b812c8fcc3cec3fdcb7f008cd488d43f86942781.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3e4f755bf472cf3bc79e3d5681.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0fe3b8748885e6034a85fdf7298.jpg",
            "http://img0.bdstatic.com/img/image/4fc1a53df0161690436b2ae8161fb6871409738074.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/6609c93d70cf3bc73032f6ffd200baa1cd112af6.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/5ab5c9ea15ce36d3c8428f2639f33a87e950b165.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/4afbfbedab64034f07d28307adc379310a551d97.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/caef76094b36acaf405ea5197ed98d1000e99c9c.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/54fbb2fb43166d227f2a2671442309f79152d2f1.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/b3119313b07eca8051ec3dec932397dda04483cb.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/4b90f603738da977ed478220b251f8198618e355.jpg"};

    private String url = "http://b.hiphotos.baidu.com/image/pic/item/32fa828ba61ea8d3ac911e0a950a304e251f5808.jpg";

    //networkImageview
    private NetworkImageView networkImageView;


    private ListView mListView;
    private CheckBox mCheckBox;
    private List<Item> list;
    private MyAdapter adapter;
    private Map<Integer, Boolean> isCheckedMap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        initview();
        initData();
        //GetImageRequest();
        //GetImageLoader();
        //GetNetWorkImage();
        adapter = new MyAdapter(this, list, isCheckedMap);
        mListView.setAdapter(adapter);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Set<Integer> set = isCheckedMap.keySet();
                Iterator<Integer> iterator = set.iterator();
                if (isChecked)
                {
                    while (iterator.hasNext())
                    {
                        Integer keyId = iterator.next();
                        isCheckedMap.put(keyId, true);
                    }
                } else
                {
                    while (iterator.hasNext())
                    {
                        Integer keyId = iterator.next();
                        isCheckedMap.put(keyId, false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void GetNetWorkImage()
    {

        ImageLoader mImageLoader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl(url, mImageLoader);

    }

    private void GetImageLoader()
    {
        ImageLoader mImageLoader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());

        ImageLoader.ImageListener mImageListener = ImageLoader.getImageListener(mImageView, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);
        mImageLoader.get(url, mImageListener);
    }


    private void GetImageRequest()
    {
        ImageRequest mImageRequest = new ImageRequest(url, new Response.Listener<Bitmap>()
        {
            @Override
            public void onResponse(Bitmap bitmap)
            {
                mImageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                mImageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        MyApplication.getHttpQueues().add(mImageRequest);
    }

    private void initview()
    {
        mListView = (ListView) findViewById(R.id.listview);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        list = new ArrayList<>();
        isCheckedMap = new HashMap<Integer, Boolean>();
    }

    private void initData()
    {
        for (int i = 0; i < urls.length; i++)
        {
            Item item = new Item();
            item.id = i;
            item.name = "第" + i + "篇日报";
            item.url = urls[i];
            list.add(item);
            isCheckedMap.put(i, false);
        }
    }

    public class Item
    {
        public Integer id;
        public String name;
        public String url;
    }
}
