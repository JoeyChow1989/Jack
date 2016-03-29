package com.jack.tsdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {

    TextSwitcher switcher;
    Handler handler;
    String[] resources = {
            "身是菩提树，",
            "心如明镜台，",
            "时时勤拂拭，",
            "勿使惹尘埃。"
    };
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    id = next(); //更新Id值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        }
    };
    int id = 0; //resources 数组的Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 3000);//每3秒更新
    }

    private void init() {
        switcher = (TextSwitcher) findViewById(R.id.id_textswitcher);
        switcher.setFactory(this);
        switcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    }

    private int next() {
        int flag = id + 1;
        if (flag > resources.length - 1) {
            flag = flag - resources.length;
        }
        return flag;
    }

    private void updateText() {
        switcher.setText(resources[id]);
    }

    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView tv = new TextView(this);
        tv.setText(resources[id]);
        return tv;
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }
}
