package com.example.administrator.design_reg5137.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.administrator.design_reg5137.R;
import com.rey.material.widget.RadioButton;

public class SwitchesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    public static SwitchesFragment newInstance(int position) {
        SwitchesFragment f = new SwitchesFragment();
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
        rootView = inflater.inflate(R.layout.fragment_switches, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        rb1 = (RadioButton)rootView.findViewById(R.id.switches_rb1);
        rb2 = (RadioButton)rootView.findViewById(R.id.switches_rb2);
        rb3 = (RadioButton)rootView.findViewById(R.id.switches_rb3);
        rb1.setOnCheckedChangeListener(this);
        rb2.setOnCheckedChangeListener(this);
        rb3.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            rb1.setChecked(rb1 == compoundButton);
            rb2.setChecked(rb2 == compoundButton);
            rb3.setChecked(rb3 == compoundButton);
        }
    }
}
