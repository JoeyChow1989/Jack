package com.zzy.autolayout_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity {

    SparseArray<String> sparseArray = new SparseArray<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sparseArray.put(1, "ssss01");
        sparseArray.put(2, "ssss02");

        Intent intent = new Intent();
        System.out.println("11111111111:"+sparseArray.get(1));
        System.out.println("22222222222:"+sparseArray.get(2));

    }
}
