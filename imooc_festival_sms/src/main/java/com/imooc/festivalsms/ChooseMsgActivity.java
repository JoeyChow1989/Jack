package com.imooc.festivalsms;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.imooc.festivalsms.bean.FestivalLab;
import com.imooc.festivalsms.bean.Msg;
import com.imooc.festivalsms.fragment.FestivalCategoryFragment;

public class ChooseMsgActivity extends AppCompatActivity
{

    private ListView mListView;
    private FloatingActionButton mFabToSend;

    private ArrayAdapter<Msg> mAdapter;
    private int mFestivalId;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);

        System.out.println("mFestivalId======::" + mFestivalId);


        mInflater = LayoutInflater.from(this);
        setTitle(FestivalLab.getmInstance().getFestivalById(mFestivalId).getName());
        initViews();

        initEvent();

    }

    private void initEvent()
    {

        mFabToSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SendMessageActivity.toActivity(ChooseMsgActivity.this, mFestivalId, -1);
            }
        });

    }

    private void initViews()
    {
        mListView = (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_fab_tosend);

        mListView.setAdapter(new ArrayAdapter<Msg>(this, -1, FestivalLab.getmInstance().getMsgByFestivalId(mFestivalId))
        {

            @Override
            public View getView(final int position, View convertView, ViewGroup parent)
            {

                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.item_msg, parent, false);
                }

                TextView textView = (TextView) convertView.findViewById(R.id.id_tv_content);
                Button button = (Button) convertView.findViewById(R.id.id_button_toSend);

                textView.setText("   " + getItem(position).getContent());

                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        SendMessageActivity.toActivity(ChooseMsgActivity.this, mFestivalId,
                                getItem(position).getId());
                    }
                });
                return convertView;
            }
        });
    }
}
