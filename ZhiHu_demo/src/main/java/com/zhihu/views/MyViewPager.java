package com.zhihu.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2016/4/17.
 */
public class MyViewPager extends ViewPager implements Runnable{

    private boolean autoRunFlag = false;//是否自动滚动，true这自动滚动

    private ViewPagerDots mViewPagerDots;//设置点提示
    
    private int autoRunSleepTime = 5000;//自动翻页暂停事件设置
    
    private PagerAdapter mAdapter;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.postDelayed(this, autoRunSleepTime);//启动runnable
    }

    //重新计算高度，解决与scrollview的冲突
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


	//重写监听事件
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        MyOnPageChangeListener mListener = null;
        if(listener != null){
            mListener = new MyOnPageChangeListener(listener);
        }
        super.setOnPageChangeListener(mListener);
    }

    //重写监听事件
    class MyOnPageChangeListener implements OnPageChangeListener {
        private OnPageChangeListener listener;
        
        public MyOnPageChangeListener(OnPageChangeListener listener){
            this.listener = listener;
        }
        
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            listener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
        	//改变点的位置
    		if(mViewPagerDots != null){
    			mViewPagerDots.setDot(getCurrentItem());
    		}
            listener.onPageSelected(position);
        }

        //姝や负鍏抽敭姝ラ锛屽疄鐜板惊鐜紝鎵�浠ュ湪瀹炰緥鍖朇ircleViewPager鏃朵竴瀹氳璋冪敤setOnPageChangeListener鏂规硶鎵嶅彲瀹炵幇
        @Override
        public void onPageScrollStateChanged(int state) {  
        	listener.onPageScrollStateChanged(state);
        }
    }

    //设置默认的点位置提示
    public void setDefaultPagerDots(ViewPagerDots mViewPagerDots){
        this.mViewPagerDots = mViewPagerDots == null ? null:mViewPagerDots;
    }
    
    //设置接口，实现自动更新点
    public interface ViewPagerDots{
    	//设置点的位置
        void setDot(int index);
        //改变点的数量
        void dotsCountChange(int count);
    }
    
    //设置是否自动运行
    public void setAutoRunFlag(boolean flag){
    	autoRunFlag = flag;
    }

    //自动滚动设置
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(autoRunFlag){//判断是否自动翻页
			if(this.getCurrentItem() == this.getAdapter().getCount()-1){
				this.setCurrentItem(0,false);
			}else{
				this.setCurrentItem(getCurrentItem()+1, true);
			}
		}
		this.postDelayed(this, autoRunSleepTime);
	}
}


