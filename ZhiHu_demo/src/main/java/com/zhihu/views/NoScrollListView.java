package com.zhihu.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class NoScrollListView extends ListView{

    public NoScrollListView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
