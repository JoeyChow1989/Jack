package com.jack.zzy.eventbus_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;


public class LeftFragment extends Fragment {
    private LinearLayout mButtonLinear;
    private final String TAG = "leftFragment";
    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        mButtonLinear = (LinearLayout)view.findViewById(R.id.left_fragment_linear);
        sendEvent();
        testThreadMode();
        return view;
    }

    private void sendEvent(){
        Button button = new Button(getActivity());
        button.setText("SEND");
        mButtonLinear.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
            }
        });
    }

    private void testThreadMode(){
        Button button = new Button(getActivity());
        button.setText("TEST THREAD MODE");
        mButtonLinear.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "订阅者线程ID:" + Thread.currentThread().getId());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                    }
                }).start();
            }
        });
    }
}
