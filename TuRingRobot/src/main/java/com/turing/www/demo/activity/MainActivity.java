package com.turing.www.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.turing.www.demo.R;
import com.turing.www.demo.appliaction.MyApplication;
import com.turing.www.demo.bean.ChatMessage;
import com.turing.www.demo.bean.Result;
import com.turing.www.demo.util.HttpUtil;
import com.turing.www.demo.util.VolleyInterface;
import com.turing.www.demo.util.VolleyRequest;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    String url = "";
    private long exitTime = 0;
    private Toolbar mToolBar;
    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mList;

    private EditText mInputMsg;
    private Button mSendButton;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener()
    {
        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.id_person:

                    Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                    startActivity(intent);
                    break;
            }

            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
        initListener();
    }

    private void initListener()
    {
        mSendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msgSend = mInputMsg.getText().toString();

                if (TextUtils.isEmpty(msgSend))
                {
                    Toast.makeText(MainActivity.this, "消息为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage chatMessage = new ChatMessage(msgSend, ChatMessage.Type.OUTCOMING, new Date());
                mList.add(chatMessage);
                mInputMsg.setText("");
                mAdapter.notifyDataSetChanged();

                setParams(msgSend);
                volly_Get(url);

            }
        });
    }

    private void initDatas()
    {
        mList = new ArrayList<ChatMessage>();
        mList.add(new ChatMessage("你好,我可以喜欢你吗？", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mList);
        mMsgs.setAdapter(mAdapter);
    }

    private void initViews()
    {
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolBar.setTitle("与小苗苗Happy中...");
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolBar);
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_inputMsg);
        mSendButton = (Button) findViewById(R.id.id_sendmsg);
    }

    /**
     * 二次封装的回调方法Get
     */
    public void volly_Get(String url)
    {

        final ChatMessage chatMessage = new ChatMessage();

        VolleyRequest.RequestGet(this, url, "abcGet", new VolleyInterface(this, VolleyInterface.mSuccessListener,
                VolleyInterface.mErrorListener)

        {
            @Override
            public void onMySuccess(String result)
            {

                Gson gson = new Gson();
                Result r = gson.fromJson(result, Result.class);
                System.out.println(result);

                if (r.getCode() == 200000)
                {
                    chatMessage.setMsg(r.getUrl());
                    chatMessage.setDate(new Date());
                    chatMessage.setType(ChatMessage.Type.INCOMING);
                } else
                {
                    chatMessage.setMsg(r.getText());
                    chatMessage.setDate(new Date());
                    chatMessage.setType(ChatMessage.Type.INCOMING);
                }
                mList.add(chatMessage);
                mAdapter.notifyDataSetChanged();
                //Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMyError(VolleyError error)
            {
                chatMessage.setMsg("服务器繁忙,请稍候再试!");
            }
        });
    }


    private String setParams(String msg)
    {
        url = HttpUtil.URL + "?key=" + HttpUtil.API_KEY + "&info=" + msg;
        return url;
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        MyApplication.getHttpQueues().cancelAll("abcGet");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else
            {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
