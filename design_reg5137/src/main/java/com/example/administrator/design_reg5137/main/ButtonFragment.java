package com.example.administrator.design_reg5137.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.administrator.design_reg5137.R;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.RadioButton;

public class ButtonFragment extends Fragment {
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    public static ButtonFragment newInstance(int position) {
        ButtonFragment f = new ButtonFragment();
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
        rootView = inflater.inflate(R.layout.fragment_button, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        FloatingActionButton bt_float = (FloatingActionButton)rootView.findViewById(R.id.button_bt_float);
        FloatingActionButton bt_float_color = (FloatingActionButton)rootView.findViewById(R.id.button_bt_float_color);
        FloatingActionButton bt_float_wave = (FloatingActionButton)rootView.findViewById(R.id.button_bt_float_wave);
        FloatingActionButton bt_float_wave_color = (FloatingActionButton)rootView.findViewById(R.id.button_bt_float_wave_color);

        bt_float.setOnClickListener(listener);
        bt_float_wave.setOnClickListener(listener);
        bt_float_color.setOnClickListener(listener);
        bt_float_wave_color.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v instanceof  FloatingActionButton){
                FloatingActionButton bt = (FloatingActionButton)v;
                bt.setLineMorphingState((bt.getLineMorphingState() + 1) % 2, true);
            }
//          System.out.println(v + " " + ((RippleDrawable)v.getBackground()).getDelayClickType());
        }
    };

}
