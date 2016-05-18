package com.example.administrator.design_reg5137.app;

import android.app.Application;

import com.rey.material.app.ThemeManager;

/**
 * Created by Administrator on 2015/11/26.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        ThemeManager.init(this, 2, 0, null);
    }
}
