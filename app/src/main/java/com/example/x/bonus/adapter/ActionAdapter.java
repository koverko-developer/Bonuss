package com.example.x.bonus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.x.bonus.*;
import com.example.x.bonus.fragments.FragmentCompany;
import com.example.x.bonus.fragments.FragmentListAction;
import com.example.x.bonus.fragments.classes.ActionObject;
import com.example.x.bonus.fragments.classes.OrganizationObject;
import com.example.x.bonus.handler.OrganizationsHandlers;
import com.example.x.bonus.viewVolder.ActionViewHolder;
import com.example.x.bonus.viewVolder.OrganizationViewHolder;

import java.util.List;

/**
 * Created by x on 15.06.2017.
 */

public class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder>{
    private Context context;
    private List<ActionObject> categoryObjectList;
    FragmentListAction activity;

    public ActionAdapter(Context context, List<ActionObject> categoryObjectList, FragmentListAction activity) {
        this.context = context;
        this.categoryObjectList = categoryObjectList;
        this.activity = activity;
    }
    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_action, parent, false);
        return new ActionViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {

        final ActionObject object = categoryObjectList.get(position);
        holder.getBindings().setVariable(BR.infoAction,object);
//
        holder.getBindings().executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return categoryObjectList.size();
    }

}
