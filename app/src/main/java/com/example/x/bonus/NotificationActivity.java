package com.example.x.bonus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.x.bonus.adapter.NotificationAdapter;
import com.example.x.bonus.fragments.classes.NotificationObject;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.NoticeAll;
import com.example.x.bonus.retrofit.Notification;
import com.example.x.bonus.retrofit.Otvet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {


    List<NotificationObject> notificationObjects = new ArrayList<>();
    NotificationAdapter notificationAdapter;
    RecyclerView recyclerView;
    ProgressBar progress;
    ImageView imgDeleteAll;
    String id;

    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        id = mSettings.getString(APP_PREFERENCES_ID,"");

        progress = (ProgressBar) findViewById(R.id.progressBar2);

        imgDeleteAll = (ImageView) findViewById(R.id.imageView22);
        imgDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerNotification);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        getNotifiacation();
    }

    private void getNotifiacation() {
        progress.setVisibility(View.VISIBLE);

        App.getApi().getNotification(id).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                Notification notification = response.body().get(0);
                int col = notification.getNoticeAll().size();

                List<Notification> list = response.body();
                notificationObjects.clear();

                for(int i = 0 ; i< col; i++ ){

                    NotificationObject object = new NotificationObject();
                    object.setNameOrganization(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(0).getName()));
                    object.setImgOrganization(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(0).getImage()));
                    object.setCity(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(0).getCity()));
                    object.setTitleAction(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getTitle()));
                    object.setTimeAction(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getTime()));
                    object.setImgAction(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getPhoto()));
                    object.setTextAction(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getText()));
                    object.setId_news(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getNewsId()));
                    object.setId_company(new ObservableField<String>(list.get(0).getNoticeAll().get(i).get(0).get(1).getCompanyId()));
                    notificationObjects.add(object);
                }
                setRecycler();
                String s = "";
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            case R.id.action_create:
                //deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setRecycler(){

//        for (int i = 0; i< 10; i++){
//            NotificationObject object = new NotificationObject();
//            object.setNameOrganization(new ObservableField<String>("Creative"+i));
//            object.setCity(new ObservableField<String>("Пинск"));
//            object.setImgOrganization(new ObservableField<String>("https://pp.userapi.com/c638129/v638129133/43c34/Dn4WI3bUf-o.jpg"));
//            object.setTitleAction(new ObservableField<String>("Название"+i));
//            object.setImgAction(new ObservableField<String>("https://pp.userapi.com/c638129/v638129133/43d2c/L7Htrea9S0k.jpg"));
//            object.setTimeAction(new ObservableField<String>("20.03.2017"));
//            object.setTextAction(new ObservableField<String>("Информационная страница программы лояльности \"ВТЕМЕ\" расскажет вам о плюсах и минусах мобильных приложений для бизнеса, о мобильных программах лояльности"));
//            //notificationAdapter = new NotificationAdapter(activity,notificationObjects);
//            notificationObjects.add(object);
//        }
        notificationAdapter = new NotificationAdapter(this,notificationObjects);
        if(notificationObjects.size()!=0) recyclerView.setAdapter(notificationAdapter);
        progress.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        getNotifiacation();

    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            delete(viewHolder.getLayoutPosition());
            notificationObjects.remove(viewHolder.getLayoutPosition());
            notificationAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
        }
    };

    private void delete(int position) {

        App.getApi().deleteNotification(id,notificationObjects.get(position).getId_news().get()).enqueue(new Callback<Otvet>() {
            @Override
            public void onResponse(Call<Otvet> call, Response<Otvet> response) {

            }

            @Override
            public void onFailure(Call<Otvet> call, Throwable t) {

            }
        });

    }

    private void deleteAll(){
        notificationObjects.remove(0);
        notificationAdapter.notifyItemRemoved(0);
        notificationObjects.clear();
        notificationAdapter.notifyDataSetChanged();
    }

    public void startInfo(int position){
        delete(position);
        Intent intent = new Intent(NotificationActivity.this, InfoOrganizationsActivity.class);
        intent.putExtra("id", notificationObjects.get(position).getId_company().get());
        startActivity(intent);
    }

}
