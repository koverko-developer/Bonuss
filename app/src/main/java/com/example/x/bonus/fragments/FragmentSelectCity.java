package com.example.x.bonus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.bonus.R;

/**
 * Created by mobi app on 13.07.2017.
 */

public class FragmentSelectCity extends Fragment{

    private RecyclerView recyclerView;
    public View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_select_city, container, false);



        return v;
    }


}
