package com.example.x.bonus.handler;

import android.util.Log;
import android.view.View;

import com.example.x.bonus.fragments.FragmentCompany;
import com.example.x.bonus.fragments.classes.OrganizationObject;

/**
 * Created by x on 15.06.2017.
 */

public class OrganizationsHandlers {

    FragmentCompany fragmentCompany;
    int position;

    public OrganizationsHandlers(FragmentCompany fragmentCompany, int position){

        this.fragmentCompany = fragmentCompany;
        this.position = position;
    }

    public void onClickCard(View view){

    }

    public void onClickButton(View view){
        fragmentCompany.clickButton(position);
    }

    public void onClick(View view){
        try {
            fragmentCompany.cardClick(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onClickCall(View view){
        fragmentCompany.call(position);
    }

}
