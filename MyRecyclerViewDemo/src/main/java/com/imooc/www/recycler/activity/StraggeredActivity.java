package com.imooc.www.recycler.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.imooc.www.recycler.R;
import com.imooc.www.recycler.adapter.MyAdapter;
import com.imooc.www.recycler.adapter.StrageredAdapter;

import java.util.ArrayList;
import java.util.List;

public class StraggeredActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private StrageredAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        mAdapter = new StrageredAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        //设置RecyclerView的布局管理
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

                mAdapter.deleteData(position);

            }
        });

        //设置RecyclerView的Item间分割线
//        DividerItemDecoration decor = new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST);
//        mRecyclerView.addItemDecoration(decor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initData() {

        mList = new ArrayList<String>();
        for (int i = 'A'; i <= 'z'; i++) {
            mList.add("" + (char) i);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
        }

        return super.onOptionsItemSelected(item);
    }
}
