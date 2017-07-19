package com.example.x.bonus.handler;

import android.view.View;

import com.example.x.bonus.NotificationActivity;

/**
 * Created by mobi app on 06.07.2017.
 */

public class NotificationHandler {

    int position;
    NotificationActivity activity;

    public NotificationHandler(NotificationActivity activity, int position){
        this.activity = activity;
        this.position = position;
    }

    public void onClick(View view){
        activity.startInfo(position);
    }

}
