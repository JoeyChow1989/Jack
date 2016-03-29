package com.jack.zzy.eventbus_demo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RightFragment extends Fragment {

    private final String TAG = "rightFragment";
    private LinearLayout mTextViewLinear;

    public RightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        mTextViewLinear = (LinearLayout) view.findViewById(R.id.right_fragment_linear);
        return view;
    }


    @Subscribe
    public void onMessage(MessageEvent event) {
        TextView textView = new TextView(getActivity());
        textView.setText(event.message);
        mTextViewLinear.addView(textView);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * EventBus基本用法
     * @param event
     */

/*
    @Subscribe
    public void onMessage(MessageEvent event) {
        TextView textView = new TextView(getActivity());
        textView.setText(event.message);
        mTextViewLinear.addView(textView);
    }
*/

    /**
     * 测试EventBus中的线程模式
     *
     * @param event
     */


}
