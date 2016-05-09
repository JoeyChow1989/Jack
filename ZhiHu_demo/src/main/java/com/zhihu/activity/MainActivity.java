package com.zhihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.R;
import com.zhihu.beans.BeforeNewsInfo;
import com.zhihu.beans.LatstNewsInfo;
import com.zhihu.beans.NewsInfo;
import com.zhihu.utils.DimenUtils;
import com.zhihu.utils.HttpUtils;
import com.zhihu.utils.JsonUtils;
import com.zhihu.views.MyScrollView;
import com.zhihu.views.MyViewPager;
import com.zhihu.views.MyViewPagerDots;
import com.zhihu.views.NoScrollListView;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    //最新新闻
    public static final String LATST_NEWS_PATH = "http://news-at.zhihu.com/api/4/news/latest";
    //过往新闻，后面加日期 20160502
    public static final String BEFORE_NEWS_PATH = "http://news.at.zhihu.com/api/4/news/before/";
    private String dateId = "20160101";
    private int dateIdCount = 0;

    // handler的放回码设置
    public static final int UPDATE_NEWS = 0;
    public static final int UPDATE_IMAGEVIEW = 1;

    //是否在加载列表数据中
    private boolean isLoading = false;

    //主页显示组件部分
    private SwipeRefreshLayout mySwipe;

    private MyScrollView newsScrollView;
    private LinearLayout newsScrollViewLL;

    private MyViewPager newsViewPager;
    private MyViewPagerDots newsViewPagerDots;
    private MyViewPagerAdapter newsViewPagerAdapter;

    private NoScrollListView newsListView;
    private MyListAdapter newsListViewAdapter;

    //数据存储部分
    private LatstNewsInfo latstNewsInfo;
    private List<NewsInfo> currentTop_stories;
    private List<NewsInfo> currentStories;

    //标题栏按钮点击事件
    private LinearLayout personalInfo;
    private LinearLayout noticeBtn;
    private LinearLayout optionBtn;

    //侧滑菜单部分
    private LinearLayout infoAll;
    private LinearLayout infoView;
    private LinearLayout infoBack;
    private LinearLayout.LayoutParams infoViewParams;

    private int mouseDownPosX;
    private int mouseDownMarginX;

    private NoScrollListView typeNewsList;

    private String[] typeNews = new String[]{
            "电影日报", "日常心理学", "用户推荐日报", "不许无聊",
            "设计日报", "大公司日报", "财经日报", "互联网安全",
            "开始游戏", "音乐日报", "动漫日报", "体育日报"
    };

    //设置浮动菜单部分
    private Button dayOrNightMode;
    private Button otherOptionBtn;
    private boolean isNightMode = true;
    private PopupWindow popupWindow;

    // 主线程更新新闻内容
    private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == UPDATE_NEWS)
            {

            } else if (msg.what == UPDATE_IMAGEVIEW)
            {
                Object[] objs = (Object[]) msg.obj;
                Bitmap bitmap = (Bitmap) objs[1];
                NewsInfo newsInfo = (NewsInfo) objs[0];
                newsInfo.setImageBitmap(new SoftReference<Bitmap>(bitmap));
                newsViewPagerAdapter.notifyDataSetChanged();
                newsListViewAdapter.notifyDataSetChanged();
            }
        }
    };

    //翻页监听
    private ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0)
        {
            // TODO Auto-generated method stub

        }

    };

    //侧滑菜单控制监听
    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            int pointX;
            float alpha;
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mouseDownPosX = (int) event.getX();
                    mouseDownMarginX = infoViewParams.leftMargin;
                    return true;
                case MotionEvent.ACTION_UP:
                    System.out.println("adfasdadsfasdfasfsd" + "");
                    if (Math.abs(infoViewParams.leftMargin) >= infoViewParams.width / 2)
                    {
                        infoViewParams.leftMargin = -infoViewParams.width;
                        infoView.setLayoutParams(infoViewParams);
                        infoAll.setOnTouchListener(null);
                    } else
                    {
                        infoViewParams.leftMargin = 0;
                        infoView.setLayoutParams(infoViewParams);
                    }
                    alpha = 0.5f * (infoViewParams.width + infoViewParams.leftMargin) / infoViewParams.width;
                    infoBack.setAlpha(alpha);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    pointX = (int) event.getX();
                    int marginX = mouseDownMarginX + pointX - mouseDownPosX;
                    System.out.println("adfasdfsd" + pointX);
                    if (marginX > 0)
                        marginX = 0;
                    if (marginX < -infoViewParams.width)
                        marginX = -infoViewParams.width;
                    infoViewParams.leftMargin = marginX;

                    alpha = 0.5f * (infoViewParams.width + infoViewParams.leftMargin) / infoViewParams.width;
                    infoBack.setAlpha(alpha);
                    infoView.setLayoutParams(infoViewParams);
                    return false;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        //设置阅读模式主题
        setDayOrNightMode();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面数据
        initView();

        // 开启子线程获取新闻数据
        getLatstNewsInfo();

    }

    //初始化界面
    private void initView()
    {

        //标题栏 按钮事件设置
        personalInfo = (LinearLayout) findViewById(R.id.personalInfo);
        noticeBtn = (LinearLayout) findViewById(R.id.noticeBtn);
        optionBtn = (LinearLayout) findViewById(R.id.optionBtn);
        personalInfo.setOnClickListener(this);
        noticeBtn.setOnClickListener(this);
        optionBtn.setOnClickListener(this);

        //主界面滑动组件设置
        mySwipe = (SwipeRefreshLayout) findViewById(R.id.mySwipe);
        mySwipe.setProgressBackgroundColorSchemeResource(android.R.color.transparent);
        mySwipe.setColorSchemeResources(android.R.color.black, android.R.color.white);
        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (isLoading)
                {
                    mySwipe.setRefreshing(false);
                } else
                {
                    dateIdCount = 0;// 初始化日期
                    getLatstNewsInfo();// 更新消息
                }
            }

        });

        newsScrollView = (MyScrollView) findViewById(R.id.newsScrollView);
        newsScrollViewLL = (LinearLayout) findViewById(R.id.newsScrollViewLL);
        newsScrollView.setOnScrollChangedListener(new MyScrollView.OnScrollChangedListener()
        {
            @Override
            public void onScrollChanged(
                    ScrollView scrollView, int scrollX, int scrollY, int oldScrollX,
                    int oldScrollY)
            {
                if (scrollY > newsScrollViewLL.getHeight() - WelcomeActivity.windowHeight && !isLoading)
                {
                    isLoading = true;//加载开始
                    getBeforeNewsInfo();
                }
            }

        });

        newsViewPager = (MyViewPager) findViewById(R.id.newsViewPager);
        newsViewPagerDots = (MyViewPagerDots) findViewById(R.id.newsViewPagerDots);
        newsListView = (NoScrollListView) findViewById(R.id.newsViewList);
        newsViewPager.setOnPageChangeListener(myOnPageChangeListener);//必须执行此步骤，用于监听翻页时自动滚动点位置
        newsViewPager.setAutoRunFlag(true);
        newsViewPager.setDefaultPagerDots(newsViewPagerDots);

        if (currentTop_stories == null)
            currentTop_stories = new ArrayList<NewsInfo>();
        newsViewPagerAdapter = new MyViewPagerAdapter(currentTop_stories);
        newsViewPager.setAdapter(newsViewPagerAdapter);

        if (currentStories == null)
            currentStories = new ArrayList<NewsInfo>();
        newsListViewAdapter = new MyListAdapter(currentStories);
        newsListView.setAdapter(newsListViewAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int newsId = currentStories.get(position).getId();
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("id", newsId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });

        //侧滑菜单部分
        infoAll = (LinearLayout) findViewById(R.id.infoLayout);
        infoView = (LinearLayout) infoAll.findViewById(R.id.infoView);
        infoBack = (LinearLayout) infoAll.findViewById(R.id.infoBack);

        infoViewParams = (LinearLayout.LayoutParams) infoView.getLayoutParams();
        infoViewParams.leftMargin = 0 - infoViewParams.width;
        infoView.setLayoutParams(infoViewParams);
        infoBack.setAlpha(0);

        //侧滑菜单内部组件部分
        typeNewsList = (NoScrollListView) findViewById(R.id.typeNewsList);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.list_news_type_item,
                new String[]{"typeNew", "typeNewImage"},
                new int[]{R.id.typeNew, R.id.typeNewImage}
        );
        typeNewsList.setAdapter(adapter);

    }

    //侧滑菜单listview 数据设置
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < typeNews.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("typeNew", typeNews[i]);
            if (i == 0)
            {
                map.put("typeNewImage", R.drawable.menu_arrow);
            } else
            {
                map.put("typeNewImage", R.drawable.menu_follow);
            }
            list.add(map);
        }
        return list;
    }

    //获取过去数据，完成后返回主线程更新新闻
    private void getBeforeNewsInfo()
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    dateIdCount++;
                    calendar.add(Calendar.DAY_OF_MONTH, -dateIdCount);//加载的日期设定
                    dateId = format.format(calendar.getTime());

                    String beforeNewsJsonData = HttpUtils.getJsonData(BEFORE_NEWS_PATH + dateId);
                    final BeforeNewsInfo beforeNewsInfo = JsonUtils.getBeforeNewsInfo(beforeNewsJsonData);

                    //更新文本数据部分
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            if (currentStories != null)
                            {
                                NewsInfo date = new NewsInfo();//添加一个时间item
                                date.setType(10);
                                date.setTitle(dateId);
                                currentStories.add(date);
                                currentStories.addAll(beforeNewsInfo.getStories());
                            }
                            newsListViewAdapter.notifyDataSetChanged();
                        }
                    });

                    //开启另外的线程去获取图片数据
                    getImageBitmap(beforeNewsInfo.getStories());
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e(e.getMessage(), "获取过去信息失败");
                }
            }
        }.start();
    }

    // 获取最新数据，完成后返回主线程更新新闻
    private void getLatstNewsInfo()
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    if (latstNewsInfo == null)
                        latstNewsInfo = new LatstNewsInfo();
                    String latstNewsJsonData = HttpUtils.getJsonData(LATST_NEWS_PATH);
                    latstNewsInfo = JsonUtils.getLatstNewsInfo(latstNewsJsonData);
                    currentTop_stories.removeAll(currentTop_stories);
                    currentStories.removeAll(currentStories);
                    currentTop_stories.addAll(latstNewsInfo.getTop_stories());
                    NewsInfo date = new NewsInfo();//添加一个时间item
                    date.setType(10);
                    date.setTitle("今日新闻");
                    currentStories.add(date);
                    currentStories.addAll(latstNewsInfo.getStories());

                    //更新文本数据部分
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            newsViewPagerAdapter.notifyDataSetChanged();
                            newsListViewAdapter.notifyDataSetChanged();
                            //解决scorllview与listview冲突，使scrollview不在顶部的问题
                            newsScrollView.smoothScrollTo(0, 0);
                            if (mySwipe != null && mySwipe.isRefreshing())
                            {
                                mySwipe.setRefreshing(false);
                            }
                        }
                    });

                    //开启另外的线程去获取图片数据
                    getImageBitmap(currentTop_stories);
                    getImageBitmap(currentStories);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e(e.getMessage(), "获取最新信息失败");
                }
            }
        }.start();
    }

    // 加载图片较慢放入子线程后返回主线程
    private void getImageBitmap(final List<NewsInfo> stories)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    for (int i = 0; i < stories.size(); i++)
                    {
                        if (stories.get(i).getType() != 10)
                        {//排除时间项
                            NewsInfo newsInfo = stories.get(i);
                            String imagePath = newsInfo.getImage();
                            //System.out.println(imagePath);
                            Bitmap bitmap = HttpUtils.getBitmap(imagePath);
                            //newsInfo.setImageBitmap(bitmap);
                            //获取图片数据完成，返回数据到主线程通知数据更新
                            Message msg = new Message();
                            msg.obj = new Object[]{newsInfo, bitmap};
                            msg.what = UPDATE_IMAGEVIEW;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                //加载结束
                if (isLoading == true)
                {
                    isLoading = false;
                }
            }
        }.start();
    }

    //顶部viewpager适配器设置
    public class MyViewPagerAdapter extends PagerAdapter
    {

        private List<NewsInfo> stories;

        private List<View> views = new ArrayList<View>();

        public MyViewPagerAdapter(List<NewsInfo> stories)
        {
            this.stories = stories;
            initViews();
        }

        @Override
        public void notifyDataSetChanged()
        {
            initViews();
            //数据变化时，更新点的数量
            newsViewPagerDots.dotsCountChange(this.getCount());
            super.notifyDataSetChanged();
        }


        private void initViews()
        {
            if (stories.size() == 0)
            {
                View view = View.inflate(MainActivity.this, R.layout.pager_news_item, null);
                views.add(view);
            } else if (stories.size() < views.size())
            {
                for (int i = stories.size(); i < views.size(); i++)
                {
                    views.remove(views.get(i));
                }
            }

            for (int i = 0; i < stories.size(); i++)
            {
                View view = null;
                final int j = i;
                if (i < views.size())
                {
                    view = views.get(i);
                    TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
                    ImageView newsImage = (ImageView) view.findViewById(R.id.newsImage);
                    String title = stories.get(i).getTitle();
                    SoftReference<Bitmap> imageBitmap = stories.get(i).getImageBitmap();
                    if (title != null)
                        newsTitle.setText(title);
                    if (imageBitmap != null)
                        newsImage.setImageBitmap(imageBitmap.get());
                } else
                {
                    view = View.inflate(MainActivity.this, R.layout.pager_news_item, null);
                    TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
                    ImageView newsImage = (ImageView) view.findViewById(R.id.newsImage);
                    String title = stories.get(i).getTitle();
                    SoftReference<Bitmap> imageBitmap = stories.get(i).getImageBitmap();
                    if (title != null)
                        newsTitle.setText(title);
                    if (newsImage != null && imageBitmap != null)
                        newsImage.setImageBitmap(imageBitmap.get());
                    views.add(view);
                }

                //设置点击事件,viewpager本身没有点击事件，只能在子view里进行点击事件设置
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int newsId = stories.get(j).getId();
                        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        intent.putExtra("id", newsId);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

            }


        }

        @Override
        public int getCount()
        {
            if (views != null)
                return views.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = views.get(position);
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ((ViewPager) container).removeView(views.get(position));
        }

    }

    //普通新闻listView适配器设置
    public class MyListAdapter extends BaseAdapter
    {

        private List<NewsInfo> stories;

        private Object objTag = new Object();

        public MyListAdapter(List<NewsInfo> stories)
        {
            this.stories = stories;
        }

        @Override
        public int getCount()
        {
            if (stories != null)
                return stories.size();
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = null;
            Log.e("数量提示", "" + parent.getChildCount());
            //判断是否是时间
            if (stories.get(position).getType() != 10)
            {//不是时间的情况
                if (convertView != null && convertView.getTag() != null)
                {
                    view = convertView;
                } else
                {
                    view = View.inflate(MainActivity.this, R.layout.list_news_item, null);
                    view.setTag(objTag);
                }
                TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);
                ImageView newsImage = (ImageView) view.findViewById(R.id.newsImage);
                String title = stories.get(position).getTitle();
                SoftReference<Bitmap> imageBitmap = stories.get(position).getImageBitmap();
                if (title != null)
                    newsTitle.setText(title);
                if (imageBitmap != null)
                    newsImage.setImageBitmap(imageBitmap.get());
            } else
            {//是时间的情况
                if (convertView != null && convertView.getTag() == null)
                {
                    view = convertView;
                } else
                {
                    view = View.inflate(MainActivity.this, R.layout.list_news_item_date, null);
                }
                TextView newsDate = (TextView) view.findViewById(R.id.newsDate);
                newsDate.setText(stories.get(position).getTitle());
            }

            return view;
        }
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            //侧滑菜单部分设置，弹出侧滑菜单栏
            case R.id.personalInfo:
                infoAll.setOnTouchListener(touchListener);//开启侧滑菜单监听，控制滑动事件
                infoViewParams.leftMargin = 0;//侧滑菜单间距设置为0显示出侧滑菜单
                infoBack.setAlpha(0.5f);//侧滑菜单半背景
                infoView.setLayoutParams(infoViewParams);
                break;
            //通知提醒
            case R.id.noticeBtn:
                break;
            //下拉菜单按钮点击事件，弹出下拉对话框
            case R.id.optionBtn:
                View view = View.inflate(this, R.layout.popupmenu, null);
                dayOrNightMode = (Button) view.findViewById(R.id.dayOrNightMode);
                otherOptionBtn = (Button) view.findViewById(R.id.otherOptionBtn);
                if (getIsNightMode())
                {
                    dayOrNightMode.setText("日间模式");
                } else
                {
                    dayOrNightMode.setText("夜间模式");
                }

                dayOrNightMode.setOnClickListener(this);
                otherOptionBtn.setOnClickListener(this);

                //弹出下拉菜单设置
                popupWindow = new PopupWindow(view, 400, 200, true);
                //设置监听，点击任意地方退出下拉菜单
                view.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        if (popupWindow != null && popupWindow.isShowing())
                        {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                        return false;
                    }
                });
                popupWindow.setOutsideTouchable(true);
                //设置下拉菜单的显示位置
                popupWindow.showAsDropDown(optionBtn, 0, -DimenUtils.dip2px(this, 40));
                break;
            //侧滑菜单中的按钮事件设置部分
//		case R.id.button1:
//			Toast.makeText(this, "button1", Toast.LENGTH_SHORT).show();
//			break;
            //下拉菜单中阅读模式切换
            case R.id.dayOrNightMode:
                if (getIsNightMode())
                {
                    Toast.makeText(this, "日间模式", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(this, "夜间模式", Toast.LENGTH_SHORT).show();
                }
                //关闭下拉菜单，保存阅读模式设置，并重启界面显示新主题
                popupWindow.dismiss();
                popupWindow = null;
                setIsNightMode(!getIsNightMode());
                recreate();
                break;
            //下拉菜单中的其他设置
            case R.id.otherOptionBtn:
                Toast.makeText(this, "button3", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                popupWindow = null;
                break;
            default:
        }
    }

    //保存阅读模式
    private void setIsNightMode(boolean bool)
    {
        SharedPreferences sp = getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isNightMode", bool);
        editor.commit();
    }

    //获取阅读模式
    private boolean getIsNightMode()
    {
        SharedPreferences sp = getSharedPreferences("userInfo", 0);
        return sp.getBoolean("isNightMode", false);
    }

    //设置阅读主题
    private void setDayOrNightMode()
    {
        if (getIsNightMode())
        {
            setTheme(R.style.AppTheme_Night);
        } else
        {
            setTheme(R.style.AppTheme_Day);
        }
    }
}
