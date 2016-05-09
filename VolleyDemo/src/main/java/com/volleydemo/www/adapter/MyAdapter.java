package com.volleydemo.www.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.volleydemo.www.R;
import com.volleydemo.www.activity.ImageActivity;
import com.volleydemo.www.appliaction.MyApplication;
import com.volleydemo.www.util.BitmapCache;

import java.util.List;
import java.util.Map;

/**
 * Created by 周正尧 on 2015/11/26.
 */
public class MyAdapter extends BaseAdapter {

    private List<ImageActivity.Item> list;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private Map<Integer, Boolean> isCheckedMap;

    public MyAdapter(Context context, List<ImageActivity.Item> list,
                     Map<Integer, Boolean> isCheckedMap) {
        this.isCheckedMap = isCheckedMap;
        this.list = list;
        inflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        ImageActivity.Item item = list.get(position);
        //Item的位置
        final int listPosition = position;
        //这个记录item的id用于操作isCheckedMap来更新CheckBox的状态
        final int id = item.id;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_image, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.networkimg);
            holder.textView = (TextView) convertView.findViewById(R.id.textview);
            holder.cBox = (CheckBox) convertView.findViewById(R.id.ischecked);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cBox.setChecked(isCheckedMap.get(id));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                //删除list中的数据
                list.remove(listPosition);
                //删除Map中对应选中状态数据
                isCheckedMap.remove(id);
                //通知列表数据修改
                notifyDataSetChanged();
            }
        });

        holder.cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCheckedMap.put(id, true);
                } else {
                    isCheckedMap.put(id, false);
                }
            }
        });

        /**
         * 加载图片
         */
        ImageLoader.ImageListener mImageListener = ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);
        mImageLoader.get(list.get(position).url, mImageListener);
        holder.textView.setText(list.get(position).name);




        return convertView;
    }

    public final class ViewHolder {
        private ImageView imageView;
        public TextView textView;
        public ImageButton deleteButton;
        public CheckBox cBox;
    }
}
