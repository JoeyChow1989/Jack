package com.example.jdadvernotice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jdadvernotice.entity.AdverNotice;
import com.example.jdadvernotice.view.JDAdverView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private  List<AdverNotice> datas = new ArrayList<AdverNotice>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        final JDViewAdapter adapter = new JDViewAdapter(datas);
        final JDAdverView tbView = (JDAdverView) findViewById(R.id.jdadver);
        tbView.setAdapter(adapter);
        //开启线程滚东
        tbView.start();
    }

    private void initData() {
        datas.add(new AdverNotice("瑞士维氏军刀 新品满200-50","最新"));
        datas.add(new AdverNotice("家居家装焕新季，讲199减100！","最火爆"));
        datas.add(new AdverNotice("带上相机去春游，尼康低至477","HOT"));
        datas.add(new AdverNotice("价格惊呆！电信千兆光纤上市","new"));
    }
}
