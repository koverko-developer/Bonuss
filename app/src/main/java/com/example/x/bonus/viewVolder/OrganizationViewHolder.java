package com.example.x.bonus.viewVolder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by x on 14.06.2017.
 */

public class OrganizationViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding bindings;
    public OrganizationViewHolder(View itemView) {
        super(itemView);
        bindings = DataBindingUtil.bind(itemView);
    }
    public ViewDataBinding getBindings(){
        return bindings;
    }


}