package com.example.x.bonus.adapter;

import android.app.Notification;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.bonus.*;

import com.example.x.bonus.fragments.FragmentListAction;
import com.example.x.bonus.fragments.classes.ActionObject;
import com.example.x.bonus.fragments.classes.NotificationObject;
import com.example.x.bonus.handler.NotificationHandler;
import com.example.x.bonus.viewVolder.ActionViewHolder;
import com.example.x.bonus.viewVolder.NotificationViewHolder;

import java.util.List;

/**
 * Created by mobi app on 06.07.2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    NotificationActivity context;
    private List<NotificationObject> categoryObjectList;

    public NotificationAdapter(NotificationActivity context, List<NotificationObject> categoryObjectList) {
        this.context = context;
        this.categoryObjectList = categoryObjectList;

    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_notification, parent, false);
        return new NotificationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

        final NotificationObject object = categoryObjectList.get(position);
        holder.getBindings().setVariable(BR.notObject,object);
        NotificationHandler handler = new NotificationHandler(context,position);
        holder.getBindings().setVariable(BR.notHandler,handler);
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
