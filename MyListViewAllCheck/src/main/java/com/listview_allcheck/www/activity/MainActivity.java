package com.listview_allcheck.www.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {

    private List<Item> itemList;
    private DraftDailyAdapter adapter;
    private Map<Integer, Boolean> isCheckedMap;
    private CheckBox allCheckBox;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allCheckBox = (CheckBox) findViewById(R.id.all_check_btn);
        listView = (ListView) findViewById(R.id.listview);
        itemList = new ArrayList<Item>();
        isCheckedMap = new HashMap<Integer, Boolean>();
        //初始化数据
        for (int i = 0; i < 8; i++) {
            Item item = new Item();
            item.id = i;
            item.name = "第" + i + "篇日报";
            itemList.add(item);
            isCheckedMap.put(i, false);
        }

        adapter = new DraftDailyAdapter(this, itemList);
        listView.setAdapter(adapter);
        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Set<Integer> set = isCheckedMap.keySet();
                Iterator<Integer> iterator = set.iterator();
                if (isChecked) {
                    while (iterator.hasNext()) {
                        Integer keyId = iterator.next();
                        isCheckedMap.put(keyId, true);
                    }
                } else {
                    while (iterator.hasNext()) {
                        Integer keyId = iterator.next();
                        isCheckedMap.put(keyId, false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    class DraftDailyAdapter extends BaseAdapter {

        public List<Item> list;
        private Context context;
        LayoutInflater inflater;

        public DraftDailyAdapter(Context context, List<Item> list) {
            super();
            this.list = list;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int location) {
            return list.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Item item = list.get(position);
            //Item的位置
            final int listPosition = position;
            //这个记录item的id用于操作isCheckedMap来更新CheckBox的状态
            final int id = item.id;

            System.out.println("listPosition:" + listPosition);
            System.out.println("id:" + id);

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.item_textview);
                holder.deleteButton = (ImageButton) convertView.findViewById(R.id.item_img);
                holder.cBox = (CheckBox) convertView.findViewById(R.id.ischecked);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cBox.setChecked(isCheckedMap.get(id));
            holder.tvName.setText(item.name);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View paramView) {
                    //Log.d(TAG, "deletePosition="+listPosition+"");
                    //删除list中的数据
                    list.remove(listPosition);
                    //删除Map中对应选中状态数据
                    isCheckedMap.remove(id);
                    //通知列表数据修改
                    adapter.notifyDataSetChanged();
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
            return convertView;
        }

        public final class ViewHolder {
            public TextView tvName;
            public ImageButton deleteButton;
            public CheckBox cBox;
        }
    }

    class Item {
        private Integer id;
        private String name;
    }
}
