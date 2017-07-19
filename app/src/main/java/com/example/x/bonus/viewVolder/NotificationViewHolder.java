package com.example.x.bonus.viewVolder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mobi app on 06.07.2017.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding bindings;
    public NotificationViewHolder(View itemView) {
        super(itemView);
        bindings = DataBindingUtil.bind(itemView);
    }
    public ViewDataBinding getBindings(){
        return bindings;
    }

}
