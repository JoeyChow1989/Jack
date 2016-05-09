package com.imooc.festivalsms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.imooc.festivalsms.ChooseMsgActivity;
import com.imooc.festivalsms.R;
import com.imooc.festivalsms.bean.Festival;
import com.imooc.festivalsms.bean.FestivalLab;

public class FestivalCategoryFragment extends Fragment {

    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;
    public static final String ID_FESTIVAL = "festivalId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_festival_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.id_gridview_festival_category);

        mAdapter = new ArrayAdapter<Festival>(getActivity(), -1, FestivalLab.getmInstance().getFestivals());


        mGridView.setAdapter(new ArrayAdapter<Festival>(getActivity(), -1, FestivalLab.getmInstance().getFestivals()) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_festival, parent, false);
                }

                TextView textView = (TextView) convertView.findViewById(R.id.id_tv_festival_name);


                textView.setText(getItem(position).getName());

                return convertView;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2015/11/30
                Intent intent = new Intent(getActivity(), ChooseMsgActivity.class);
                intent.putExtra(ID_FESTIVAL, mAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
    }
}
