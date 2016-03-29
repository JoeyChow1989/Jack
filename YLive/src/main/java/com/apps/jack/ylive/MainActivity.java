package com.apps.jack.ylive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String  DEFAULTPATH = "http://ipadlive.cntv.soooner.com/cctv_p2p_hdcctv6.m3u8";
    EditText Live_Url;
    Button PlayBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Live_Url = (EditText)findViewById(R.id.live_url);
        Live_Url.setText(DEFAULTPATH);
        PlayBtn = (Button)findViewById(R.id.play);
        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, InitActivity.class);
                String path = Live_Url.getText().toString();
                if (path == null) {
                    path = DEFAULTPATH;
                }
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }
}
