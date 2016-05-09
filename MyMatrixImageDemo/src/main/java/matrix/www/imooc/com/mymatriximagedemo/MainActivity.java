package matrix.www.imooc.com.mymatriximagedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import matrix.www.imooc.com.mymatriximagedemo.view.ZoomImageView;

public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private int[] mImg = new int[]{R.mipmap.sdd, R.mipmap.index, R.mipmap.media};
    private ImageView[] mImgViews = new ImageView[mImg.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImg.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView zoomImageView = new ZoomImageView(getApplicationContext());

                zoomImageView.setImageResource(mImg[position]);

                container.addView(zoomImageView);
                mImgViews[position] = zoomImageView;
                return zoomImageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImgViews[position]);
            }
        });
    }

}
