package com.imooc.www.recycler.bean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.imooc.www.recycler.R;

/**
 * Created by Administrator on 2015/11/16.
 */
public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView tv;

    public ViewHolder(View itemView) {
        super(itemView);

        tv = (TextView) itemView.findViewById(R.id.id_tv);

    }
}
