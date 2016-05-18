package com.example.administrator.design_reg5137.main;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.design_reg5137.R;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Spinner;

public class SpinnerFragment extends Fragment {
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    public static SpinnerFragment newInstance(int position) {
        SpinnerFragment f = new SpinnerFragment();
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_spinner, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        Spinner spn_label = (Spinner)rootView.findViewById(R.id.spinner_label);
        Spinner spn_no_arrow = (Spinner)rootView.findViewById(R.id.spinner_no_arrow);
        String[] items = new String[20];
        for(int i = 0; i < items.length; i++)
            items[i] = "Item " + String.valueOf(i + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spn_label.setAdapter(adapter);
        spn_no_arrow.setAdapter(adapter);
    }
}
