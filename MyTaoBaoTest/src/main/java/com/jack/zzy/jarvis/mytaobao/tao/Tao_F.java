package com.jack.zzy.jarvis.mytaobao.tao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jack.zzy.jarvis.mytaobao.home.BabyActivity;
import com.jack.zzy.javis.Adapter.Adapter_ListView_tao;
import com.jack.zzy.mytaobaotest.R;

/**
 * 微淘主界面
 * @author http://yecaoly.taobao.com
 *
 */
public class Tao_F extends Fragment {
	
	private ListView listView_tao;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	
    	View view=LayoutInflater.from(getActivity()).inflate(R.layout.tao_f, null);
    	initView(view);
		return view;
	}




	private void initView(View view){
    	listView_tao=(ListView) view.findViewById(R.id.listView_tao);
    	listView_tao.setAdapter(new Adapter_ListView_tao(getActivity()));
    	listView_tao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent=new Intent(getActivity(),BabyActivity.class);
				startActivity(intent);
			}
		});
    	
    }

    
}
