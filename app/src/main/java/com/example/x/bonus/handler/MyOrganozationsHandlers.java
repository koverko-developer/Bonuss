package com.example.x.bonus.handler;

import android.view.View;

import com.example.x.bonus.fragments.FragmentSale;

/**
 * Created by x on 22.06.2017.
 */

public class MyOrganozationsHandlers {

    FragmentSale fragmentSale;
    int position;

    public MyOrganozationsHandlers(FragmentSale fragmentSale, int position) {
        this.fragmentSale = fragmentSale;
        this.position = position;
    }

    public void onClick(View view){
        try {
            fragmentSale.cardClick(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickCall(View view){
        fragmentSale.call(position);
    }
}
