package com.imooc.www.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.www.recycler.R;
import com.imooc.www.recycler.bean.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzy on 2015/11/16.
 */
public class StrageredAdapter extends MyAdapter {

    private List<Integer> mHeights;


    public StrageredAdapter(Context context, List<String> mList) {
        super(context, mList);
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mList.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);
        holder.tv.setText(mList.get(position));
        setUpItemEvent(holder);
    }
}

