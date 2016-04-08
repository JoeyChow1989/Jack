package com.turing.www.demo.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2015/11/26.
 */
public class BitmapCache implements ImageLoader.ImageCache
{

    public LruCache<String, Bitmap> cache;
    public int max = 10 * 1024 * 1024;

    public BitmapCache()
    {
        cache = new LruCache<String, Bitmap>(max)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String s)
    {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap)
    {
        cache.put(s, bitmap);
    }
}
