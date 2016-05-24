package com.jack.www.gsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
{

    String s = "{name:张三 ,age:26 ,phone:[131,132],score:[{id:语文,fenshu:100},{id:数学,fenshu:60},{id:化学,fenshu:120}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new Gson();
        Student student = gson.fromJson(s,Student.class);
        System.out.println(student.age);
    }
}
