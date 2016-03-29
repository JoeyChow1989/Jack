package com.imooc.www.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.www.recycler.R;
import com.imooc.www.recycler.bean.ViewHolder;

import java.util.List;

/**
 * Created by zzy on 2015/11/16.
 */
public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    protected List<String> mList;

    //Onclik接口
    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public MyAdapter(Context context, List<String> mList) {

        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_single_textview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(mList.get(position));
        setUpItemEvent(holder);
    }

    protected void setUpItemEvent(final ViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int layoutpostion = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutpostion);
                }
            });

            //longclick

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int layoutpostion = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, layoutpostion);

                    return false;
                }
            });
        }
    }


    public void addData(int postion) {
        mList.add(postion, "Insert one");
        notifyItemInserted(postion);
    }

    public void deleteData(int postion) {
        mList.remove("Delete one");
        notifyItemRemoved(postion);
    }
}

