package com.example.x.bonus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.*;
import com.example.x.bonus.fragments.FragmentCompany;
import com.example.x.bonus.fragments.classes.OrganizationObject;
import com.example.x.bonus.handler.OrganizationsHandlers;
import com.example.x.bonus.viewVolder.OrganizationViewHolder;

import java.util.List;

/**
 * Created by x on 14.06.2017.
 */


public class   OrganizationAdapter extends RecyclerView.Adapter<OrganizationViewHolder>{
    private Context context;
    private List<OrganizationObject> categoryObjectList;
    FragmentCompany activity;

    public OrganizationAdapter(Context context, List<OrganizationObject> categoryObjectList, FragmentCompany activity) {
        this.context = context;
        this.categoryObjectList = categoryObjectList;
        this.activity = activity;
    }
    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_organizations, parent, false);
        return new OrganizationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(OrganizationViewHolder holder, int position) {

        try {
            final OrganizationObject categoryObject = categoryObjectList.get(position);
            holder.getBindings().setVariable(BR.infoOrganizations, categoryObject);
            OrganizationsHandlers handlers = new OrganizationsHandlers(activity,position);
            holder.getBindings().setVariable(BR.organizationsHandler,handlers);
//        MyHandlers handlers = new MyHandlers(activity, position);
//        holder.getBindings().setVariable(BR.handlers,handlers);
            holder.getBindings().executePendingBindings();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
