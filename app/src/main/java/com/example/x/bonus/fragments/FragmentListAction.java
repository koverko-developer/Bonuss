package com.example.x.bonus.fragments;

import android.annotation.SuppressLint;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.InfoOrganizationsActivity;
import com.example.x.bonus.R;
import com.example.x.bonus.adapter.ActionAdapter;
import com.example.x.bonus.fragments.classes.ActionObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by x on 15.06.2017.
 */

@SuppressLint("ValidFragment")
public class FragmentListAction extends Fragment {

    View v;
    ActionAdapter actionAdapter;
    List<ActionObject> actionObjects = new ArrayList<>();
    InfoOrganizationsActivity activity;


    public FragmentListAction(InfoOrganizationsActivity activity, List<ActionObject> list){
        this.activity = activity;
        this.actionObjects = list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_action, container, false);



        setRecycler();

        return v;
    }

    private void setRecycler(){

        try {

            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvListAction);
            final LinearLayoutManager llm = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            if(actionObjects!=null)actionAdapter = new ActionAdapter(activity,actionObjects,this);
            if(actionObjects.size()==0){
                recyclerView.setVisibility(View.GONE);


            }
            recyclerView.setAdapter(actionAdapter);
            //recyclerView.getAdapter().notifyDataSetChanged();
            String  s= "";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
