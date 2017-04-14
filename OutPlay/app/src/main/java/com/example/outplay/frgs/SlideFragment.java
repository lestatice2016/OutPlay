package com.example.outplay.frgs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SlideFragment extends Fragment {

    private static final String ARG_LAYOUT_ID="LayoutId";

    private int mParam;


    public static SlideFragment newInstance(int param) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, param);
        fragment.setArguments(args);
        return fragment;
    }

    public SlideFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_LAYOUT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(mParam, container, false);
    }


}
