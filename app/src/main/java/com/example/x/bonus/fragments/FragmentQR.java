package com.example.x.bonus.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.fragments.classes.EAN13CodeBuilder;
import com.example.x.bonus.fragments.classes.QRObject;
import com.example.x.bonus.*;
import com.example.x.bonus.handler.QRHandler;

import java.util.Random;

/**
 * Created by x on 14.06.2017.
 */

@SuppressLint("ValidFragment")
public class FragmentQR extends Fragment {


    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";

    private SharedPreferences mSettings;


    BottomActivity activity;
    QRObject object = new QRObject();
    View v;
    String ids;

    public static FragmentQR newInstance(BottomActivity activity) {

        return new FragmentQR(activity);
    }

    public FragmentQR(BottomActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr, container, false);

        try {
            mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            String img = mSettings.getString(APP_PREFERENCES_IMG,"");
            String token = mSettings.getString(APP_PREFERENCES_TOKEN,"");
            ids = mSettings.getString(APP_PREFERENCES_ID,"");
            String phone = mSettings.getString(APP_PREFERENCES_PHONE,"");

//
            img = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+token;


            object.setPhone(new ObservableField<String>(phone));
            object.setInvait(new ObservableField<String>("VTEME2017"+ids));

            object.setQr(new ObservableField<String>("VTEME2017"+token+ids));

            EAN13CodeBuilder bb = new EAN13CodeBuilder(randomShtrih());
            object.setShtrih(new ObservableField<String>(bb.getCode()));
            binding.setVariable(BR.infoQR,object);

            QRHandler handler = new QRHandler(this);
            binding.setVariable(BR.handlerQR, handler);

            v = binding.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    private String randomShtrih(){

        String code = "";

        int col_randInt =  12 - ids.length();

        for(int i = 0; i < col_randInt-1;  i++){
            Random rand = new Random();
            int  n = rand.nextInt(9);
            code += String.valueOf(n);
        }

        String n = ids + code + String.valueOf(ids.length());
        int i = 1;
        return n;

    }

    public void exit(){
       activity.showExit();
    }

    public void clickBusiness(){
        activity.show();
    }
}
