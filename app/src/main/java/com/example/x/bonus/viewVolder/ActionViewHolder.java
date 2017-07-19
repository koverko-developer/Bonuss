package com.example.x.bonus.viewVolder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by x on 15.06.2017.
 */

public class ActionViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding bindings;
    public ActionViewHolder(View itemView) {
        super(itemView);
        bindings = DataBindingUtil.bind(itemView);
    }
    public ViewDataBinding getBindings(){
        return bindings;
    }

}
