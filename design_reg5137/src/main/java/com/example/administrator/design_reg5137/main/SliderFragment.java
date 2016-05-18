package com.example.administrator.design_reg5137.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.design_reg5137.R;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Slider;

public class SliderFragment extends Fragment {
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    public static SliderFragment newInstance(int position) {
        SliderFragment f = new SliderFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_slider, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        Slider sl_continuous = (Slider)rootView.findViewById(R.id.slider_sl_continuous);
        final TextView tv_continuous = (TextView)rootView.findViewById(R.id.slider_tv_continuous);
        tv_continuous.setText(String.format("pos=%.1f value=%d", sl_continuous.getPosition(), sl_continuous.getValue()));
        sl_continuous.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                tv_continuous.setText(String.format("pos=%.1f value=%d", newPos, newValue));
            }
        });

        Slider sl_discrete = (Slider)rootView.findViewById(R.id.slider_sl_discrete);
        final TextView tv_discrete = (TextView)rootView.findViewById(R.id.slider_tv_discrete);
        tv_discrete.setText(String.format("pos=%.1f value=%d", sl_discrete.getPosition(), sl_discrete.getValue()));
        sl_discrete.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                tv_discrete.setText(String.format("pos=%.1f value=%d", newPos, newValue));
            }
        });

    }
}
