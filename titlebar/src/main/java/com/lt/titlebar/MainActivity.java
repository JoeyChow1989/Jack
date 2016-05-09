package com.lt.titlebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_titlebar);
        Titlebar titlebar = (Titlebar) findViewById(R.id.titlebar);
        titlebar.setOnButtonClickListener(new Titlebar.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                Toast.makeText(MainActivity.this,"左侧按钮被点击了",0).show();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(MainActivity.this,"右侧按钮被点击了",0).show();
            }
        });
    }
}
