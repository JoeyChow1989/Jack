package com.zhihu.views;

/*
婊戝姩浜嬩欢鐨勭洃鍚紝鑾峰彇S浠巖ollView鐨勪綅缃俊鎭�
 */

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private OnScrollChangedListener onScrollChangedListener;

    private int lastScrollY;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener){
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY){
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if(onScrollChangedListener != null)
            onScrollChangedListener.onScrollChanged(this,scrollX,scrollY,oldScrollX, oldScrollY);
    }

    public interface OnScrollChangedListener{
        void onScrollChanged(ScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

}

