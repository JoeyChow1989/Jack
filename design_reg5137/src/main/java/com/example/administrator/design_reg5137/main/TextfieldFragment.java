package com.example.administrator.design_reg5137.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.administrator.design_reg5137.R;
import com.rey.material.widget.EditText;
import com.rey.material.widget.RadioButton;

public class TextfieldFragment extends Fragment {
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    private EditText et_helper;
    private android.widget.EditText et_helper_input;

    public static TextfieldFragment newInstance(int position) {
        TextfieldFragment f = new TextfieldFragment();
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
        rootView = inflater.inflate(R.layout.fragment_textfield, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        et_helper = (EditText) rootView.findViewById(R.id.textfield_et_helper);
        et_helper_input =  (android.widget.EditText) rootView.findViewById(R.id.textfield_et_helper_input);
        et_helper.setOnKeyListener(keyListener);
        et_helper.setOnFocusChangeListener(focusChangeListener);

        EditText et_helper_error = (EditText) rootView.findViewById(R.id.textfield_et_helper_error);
        android.widget.EditText et_helper_input_error =  (android.widget.EditText) rootView.findViewById(R.id.textfield_et_label_error_input);
        et_helper_error.setText("123456");
        et_helper_error.setError("Password is incorrect.");
        et_helper_input_error.setError("Password is incorrect.");
    }

    private View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
                et_helper.setError("Password is incorrect.");
                et_helper_input.setError("Password is incorrect.");
            }
            return false;
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                et_helper.setError(null);
                et_helper_input.setError(null);
            }
        }
    };
}
