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
import com.example.x.bonus.fragments.classes.QRObject;
import com.example.x.bonus.*;
import com.example.x.bonus.handler.QRHandler;

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

        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String img = mSettings.getString(APP_PREFERENCES_IMG,"");
        String token = mSettings.getString(APP_PREFERENCES_TOKEN,"");
        String ids = mSettings.getString(APP_PREFERENCES_ID,"");
        String phone = mSettings.getString(APP_PREFERENCES_PHONE,"");

//
        img = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+token;



        object.setPhone(new ObservableField<String>(phone));
        object.setInvait(new ObservableField<String>("VTEME2017"+ids));

        object.setQr(new ObservableField<String>("VTEME2017"+token+ids));
        binding.setVariable(BR.infoQR,object);

        QRHandler handler = new QRHandler(this);
        binding.setVariable(BR.handlerQR, handler);

        v = binding.getRoot();

        return v;
    }

    public void exit(){
       activity.showExit();
    }

    public void clickBusiness(){
        activity.show();
    }
}
