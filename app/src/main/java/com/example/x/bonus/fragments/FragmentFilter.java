package com.example.x.bonus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.x.bonus.FilterActivity;
import com.example.x.bonus.R;

/**
 * Created by mobi app on 13.07.2017.
 */

public class FragmentFilter extends Fragment {

    View v;
    FilterActivity activity;

    public FragmentFilter(FilterActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_select_filter, container,false);

        TextView tv = (TextView) v.findViewById(R.id.textView46);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setFragmentSelectCity();
            }
        });

        return v;
    }
}
