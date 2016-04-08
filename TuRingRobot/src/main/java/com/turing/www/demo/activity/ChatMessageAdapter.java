package com.turing.www.demo.activity;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.turing.www.demo.R;
import com.turing.www.demo.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ChatMessageAdapter extends BaseAdapter
{

    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING)
        {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            if (getItemViewType(position) == 0)
            {
                convertView = mInflater.inflate(R.layout.item_from_msg, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.from_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_from_msg);
            }else
            {
                convertView = mInflater.inflate(R.layout.item_to_msg, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.to_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_to_msg);
            }

            convertView.setTag(viewHolder);

        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mMsg.setAutoLinkMask(Linkify.ALL);
        viewHolder.mMsg.setMovementMethod(LinkMovementMethod.getInstance());

        //设置数据
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());

        return convertView;
    }

    final class ViewHolder
    {
        TextView mDate;
        TextView mMsg;
    }
}
