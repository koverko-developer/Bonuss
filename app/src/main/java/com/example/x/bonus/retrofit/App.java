package com.example.x.bonus.retrofit;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.MainActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x on 13.06.2017.
 */

public class App extends Application {

    private static Api umoriliApi;
    private Retrofit retrofit;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private SharedPreferences mSettings;

    @Override
    public void onCreate() {
        super.onCreate();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        String token = mSettings.getString(APP_PREFERENCES_TOKEN, "");
//
//        if(token.length()>2){
//            Intent intent = new Intent(App.this, BottomActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }else {
//            Intent intent = new Intent(App.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }

        retrofit = new Retrofit.Builder()
                .baseUrl("http://anika-cs.by/server/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        umoriliApi = retrofit.create(Api.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static Api getApi() {
        return umoriliApi;
    }
}
