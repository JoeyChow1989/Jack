package com.example.administrator.design_reg5137.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.example.administrator.design_reg5137.R;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

public class DialogsFragment extends Fragment implements View.OnClickListener {
    private View rootView = null;//缓存Fragment view
    private int mPosition;

    private BottomSheetDialog mBottomSheetDialog;

    public static DialogsFragment newInstance(int position) {
        DialogsFragment f = new DialogsFragment();
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
        rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
        initBase();
        return rootView;
    }

    private void initBase() {
        rootView.findViewById(R.id.dialog_bt_bottomsheet).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showBottomSheet();
    }

    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.MyBottomSheetDialogStyle);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_bottomsheet, null);

        Button bt_match = (Button) v.findViewById(R.id.sheet_bt_match);
        bt_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_content, null, false);
                mBottomSheetDialog.addContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });

        mBottomSheetDialog.contentView(v).show();
    }
}
