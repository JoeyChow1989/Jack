package com.zhihu.views;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhihu.R;
import com.zhihu.views.MyViewPager;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyViewPagerDots extends LinearLayout implements MyViewPager.ViewPagerDots{

    private int dotsIndex = 0;//选中点位置
    
    private int dotsMargin = 5;//点的间距
    
    private int dosSize = 8;//点的大小

    private int dots_selectedColor = android.R.color.white;//选中时点颜色
    private int dots_normalColor = android.R.color.darker_gray;//正常点颜色
    
    private int dotsCount;
    
    private Context context;
    
    public MyViewPagerDots(Context context) {
        super(context);
        this.context = context;
    }
    public MyViewPagerDots(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
    }

    //初始话点设置
    public void initDotsView(int count) {

        //初始化点，清除所有的点
        if(this.getChildCount()>0) {
            this.removeAllViews();
        }
        
        setCount(count);
        
        //初始化点位置
        dotsIndex = 0;//鍒濆鍖栭�変腑鐐�
        
        for(int i=0;i<count;i++) {
            ImageView dotsView = new ImageView(this.context);
            if(i!=0) {
                dotsView.setBackgroundDrawable(getResources().getDrawable(dots_normalColor));
            }else{
                dotsView.setBackgroundDrawable(getResources().getDrawable(dots_selectedColor));
            }
            LayoutParams lp = new LayoutParams(dosSize,dosSize);
            lp.setMargins(dotsMargin, 0, dotsMargin, 0);
            dotsView.setLayoutParams(lp);
            this.addView(dotsView);
        }
    }

    public int getCount() {
        return dotsCount;
    }

    public void setCount(int count) {
        this.dotsCount = count;
    }

    //设置接口项，设置点的位置
    @Override
    public void setDot(int index) {
        this.getChildAt(dotsIndex).setBackgroundDrawable(getResources().getDrawable(dots_normalColor));
        this.getChildAt(index).setBackgroundDrawable(getResources().getDrawable(dots_selectedColor));
        dotsIndex = index;
    }
    
    //改变点的数量
	@Override
	public void dotsCountChange(int count) {
		initDotsView(count);
	}
}
