package com.arcmenu.www;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arcmenu.www.view.ArcMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周正尧 on 2015/11/27.
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mDatas;
    private ArcMenu mArcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mDatas));

        initEvent();
    }

    private void initEvent() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (mArcMenu.isOpen())
                    mArcMenu.toggleMenu(600);
            }
        });

        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onclick(View view, int position) {
                Toast.makeText(MainActivity.this, position + ":" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.id_listview);
        mArcMenu = (ArcMenu) findViewById(R.id.id_menu);
    }

    private void initData() {
        mDatas = new ArrayList<String>();

        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }
}
