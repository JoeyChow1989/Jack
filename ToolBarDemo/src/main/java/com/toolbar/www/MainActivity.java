package com.toolbar.www;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.toolbar.www.activity.SreachActivity;

public class MainActivity extends AppCompatActivity
{

    private Toolbar mToolBar;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener()
    {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem)
        {
            String msg = "";
            switch (menuItem.getItemId())
            {
                case R.id.action_edit:
                    msg += "Click edit";
                    Intent intent = new Intent(MainActivity.this,SreachActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings1:
                    msg += "Click setting1";
                    break;
                case R.id.action_settings2:
                    msg += "Click setting2";
                    break;
                case R.id.action_settings3:
                    msg += "Click setting3";
                    break;
                case R.id.action_settings4:
                    msg += "Click setting4";
                    break;
                case R.id.action_settings5:
                    msg += "Click setting5";
                    break;
            }

            if (!msg.equals(""))
            {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolBar.setTitle("My Title");
        // Sub Title
        mToolBar.setSubtitle("Sub title");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("你确定要点击吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this,"点击了确定",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
