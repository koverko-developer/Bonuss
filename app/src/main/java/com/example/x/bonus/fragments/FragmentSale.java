package com.example.x.bonus.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.NotificationActivity;
import com.example.x.bonus.R;
import com.example.x.bonus.adapter.MyOrganizationsAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.x.bonus.adapter.NotificationAdapter;
import com.example.x.bonus.fragments.classes.NotificationObject;
import com.example.x.bonus.fragments.classes.OrganizationObject;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Company;
import com.example.x.bonus.retrofit.Notification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x on 15.06.2017.
 */

@SuppressLint("ValidFragment")
public class FragmentSale extends Fragment {

    Dialog dialog = null;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    NotificationAdapter notificationAdapter;
    private SharedPreferences mSettings;
    TextView tvCol;

    View v;
    BottomActivity activity;
    RecyclerView recyclerView;
    MyOrganizationsAdapter adapter;
    List<OrganizationObject> organizationList = new ArrayList<>();
    List<NotificationObject> notificationObjects = new ArrayList<>();
    List<Company> list = new ArrayList<>();
    ProgressBar progressBar;
    ImageView imgNotification;
    boolean isNot = false;
    boolean isNet = false;
    String ids;


    public FragmentSale(BottomActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sale, container, false);

        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ids = mSettings.getString(APP_PREFERENCES_ID,"");




        progressBar = (ProgressBar) v.findViewById(R.id.progressBarFragmentSale);

        ImageView imgExit = (ImageView) v.findViewById(R.id.imageViewExitSsale);
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showExit();
            }
        });

        tvCol = (TextView) v.findViewById(R.id.textView39);




        imgNotification = (ImageView) v.findViewById(R.id.imageView21);
        imgNotification.setEnabled(false);
        //imgNotification.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
        tvCol.setVisibility(View.GONE);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialogNotification();
                tvCol.setVisibility(View.GONE);
                startActivity(new Intent(activity, NotificationActivity.class));
            }
        });

        getMyCompany(ids);
        //setRecyclerView();

        return  v;
    }

    private void getMyCompany(String ids){

        App.getApi().getMyCompany(ids).enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                List<Company> list = response.body();
                isNet = true;
                setRecyclerView(list);
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                isNet = false;
                getDB();
            }
        });

    }

    private void getDB(){
        List<OrganizationObject> list = activity.readDB();
        recyclerView = (RecyclerView) v.findViewById(R.id.rvSale);
        final LinearLayoutManager llm = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        adapter = new MyOrganizationsAdapter(activity,list,this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    private void setRecyclerView(List<Company> lists){
        list = lists;
        recyclerView = (RecyclerView) v.findViewById(R.id.rvSale);
        final LinearLayoutManager llm = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        adapter = new MyOrganizationsAdapter(activity,organizationList,this);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i< list.size(); i++){

            OrganizationObject object = new OrganizationObject();

            object.setId(new ObservableField<String>(list.get(i).getId()));
            object.setName(new ObservableField<String>(list.get(i).getName()));
            object.setPhone(new ObservableField<String>(list.get(i).getTelephone()));
            object.setIsMy(new ObservableBoolean(false  ));
            object.setNameCategory(new ObservableField<String>(list.get(i).getCategoryId().getCompanyCategory()));
            if(Integer.parseInt(list.get(i).getLoyaltyId())==1) object.setSale(new ObservableField<String>(list.get(i).getSale()));
            else if (Integer.parseInt(list.get(i).getLoyaltyId())==2){
                object.setSale(new ObservableField<String>(list.get(i).getLoyaltyStep().getStep1()));
            }else object.setSale(new ObservableField<String>(list.get(i).getLoyaltyStep().getStep1()));
            object.setTime(new ObservableField<String>(list.get(i).getTime()));
            object.setImg(new ObservableField<String>(list.get(i).getImage()));
            object.setImgCategory(new ObservableField<String>(list.get(i).getCategoryId().getIcon()));
            object.setCity(new ObservableField<String>(list.get(i).getCity().getCity()));
            if(list.get(i).getInfo().length()>1)object.setInfo(new ObservableField<String>(list.get(i).getInfo()));
            else object.setInfo(new ObservableField<String>(null));
            organizationList.add(object);

        }
        TextView tv = (TextView) v.findViewById(R.id.textView42);
        if(organizationList.size()==0) tv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    public void cardClick(int position){

        try {
            if(isNet) activity.startActivityNew(organizationList.get(position).getId().get(),
                    list.get(position).getLoyaltyId());
            else Toast.makeText(activity, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCountNotifycation(){
        try {
            App.getApi().getNotification(ids).enqueue(new Callback<List<Notification>>() {
                @Override
                public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                    Notification notification = response.body().get(0);
                    int col = notification.getNoticeAll().size();
                    if(col>0) {
                        tvCol.setText("+"+String.valueOf(col));
                        tvCol.setVisibility(View.VISIBLE);
                        imgNotification.setEnabled(true);
                    }
                    String s = "";
                }

                @Override
                public void onFailure(Call<List<Notification>> call, Throwable t) {
                   // activity.readDB();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void call(int position){
        activity.call(organizationList.get(position).getPhone().get());
    }



    @Override
    public void onResume() {
        try {
            super.onResume();
            getCountNotifycation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
