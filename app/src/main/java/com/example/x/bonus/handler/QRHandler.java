package com.example.x.bonus.handler;

import android.view.View;

import com.example.x.bonus.fragments.FragmentQR;

/**
 * Created by x on 16.06.2017.
 */

public class QRHandler {

    FragmentQR fragmentQR;

    public QRHandler(FragmentQR fragmentQR){
        this.fragmentQR = fragmentQR;
    }

    public void onClickExit(View v){
        fragmentQR.exit();
    }


    public void onClickBusines(View v){
        fragmentQR.clickBusiness();
    }

}
