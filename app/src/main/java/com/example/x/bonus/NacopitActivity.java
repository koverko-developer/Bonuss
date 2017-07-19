package com.example.x.bonus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.fragments.classes.ActionObject;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Info;
import com.example.x.bonus.retrofit.News;
import com.example.x.bonus.retrofit.Otvet;
import com.example.x.bonus.retrofit.Userinfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NacopitActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    private SharedPreferences mSettings;
    ImageView imgLogo,imgCategory, imgIsFixsed;
    TextView textCategory, nameCompany, colUserBuy, colUserProcent, colUserBalls;
    Button btnActivate;
    Userinfo usetInfo = null;
    int isActivate;
    String name, sale, id_c;
    String ids;
    int col_buy = 0;
    int loyalty_id;


    private TextView tvBuy1,tvBuy2,tvBuy3,tvBuy4;
    private TextView tvBuy1T,tvBuy2T,tvBuy3T,tvBuy4T;

    //Circle text steps procent
    private TextView tvStep1,tvStep2,tvStep3,tvStep4;

    //Progress steps
    private ProgressBar progress1,progress2,progress3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nacopit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSettings = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ids = mSettings.getString(APP_PREFERENCES_ID,"");

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        id_c = intent.getStringExtra("id");

        getInfoCompany(ids, id_c);

    }

    private void getInfoCompany(String ids, String id_c){

        try {
            App.getApi().getInfoUser(ids, id_c).enqueue(new Callback<List<Info>>() {
                @Override
                public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                    List<Info> list = response.body();
                    Info info = list.get(0);
                    String address = info.getMycompany().getCity().getIndex()+ " , " +info.getMycompany().getCity().getCity()
                            +" , ул."+info.getMycompany().getCity().getAddress()+" "+ info.getMycompany().getCity().getBuild();
                    if(info.getMycompany().getCity().getOffice()!= null) address = address + ",оффис "+ info.getMycompany().getCity().getOffice();

                    loyalty_id = Integer.parseInt(info.getMycompany().getLoyaltyId());


                    usetInfo = info.getUserinfo();
                    name = info.getMycompany().getName();
                    getSupportActionBar().setTitle(name);

                    if(loyalty_id==1){
                        sale = "Скидка "+ info.getMycompany().getSale() + " %";
                    }else sale = "Скидка "+ info.getMycompany().getLoyaltyStep().getStep1() + " %";

                    isActivate = Integer.parseInt(info.getUserinfo().getStatusCompanyUser());
                    col_buy = (Integer.parseInt(info.getUserinfo().getColBuy()));
                    String sumsBuy = info.getUserinfo().getSum_buy();
                    //int col_f = Integer.parseInt(info.getUserinfo().getCol_friend());
                    setInfo(info.getMycompany().getImage(), info.getMycompany().getCategoryId().getIcon(),
                            info.getMycompany().getCategoryId().getCompanyCategory(),info.getMycompany().getName(),
                            info.getMycompany().getLoyaltyStep().getStep1Col(),info.getMycompany().getLoyaltyStep().getStep2Col(),
                            info.getMycompany().getLoyaltyStep().getStep3Col(),info.getMycompany().getLoyaltyStep().getStep4Col(),
                            info.getMycompany().getLoyaltyStep().getStep1(),info.getMycompany().getLoyaltyStep().getStep2(),
                            info.getMycompany().getLoyaltyStep().getStep3(),info.getMycompany().getLoyaltyStep().getStep4(),
                            info.getUserinfo().getColBuy(),info.getUserinfo().getColBal(),
                            Integer.parseInt(info.getUserinfo().getStatusCompanyUser()), info.getUserinfo().getCol_friend(),
                            sumsBuy
                            );
                }

                @Override
                public void onFailure(Call<List<Info>> call, Throwable t) {

                    Toast.makeText(NacopitActivity.this,"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setInfo(String url, String urlCategory, String textNameCategory,
                         String nCompany, String buyStep1,String buyStep2,String buyStep3,String buyStep4,
                         String procentStep1,String procentStep2,String procentStep3,String procentStep4,
                         String userBuy, String userProcent, int isActivate, String s, String sumsBuy){
        try {
            imgLogo = (ImageView) findViewById(R.id.imageViewLogoInfo);
            Picasso.with(imgLogo.getContext())
                    .load(url)
                    .into(imgLogo);
            imgCategory = (ImageView) findViewById(R.id.imageViewLogoCategory);
            Picasso.with(imgCategory.getContext())
                    .load(urlCategory)
                    .into(imgCategory);
            textCategory = (TextView) findViewById(R.id.textViewNameCategory);
            textCategory.setText(textNameCategory);

            nameCompany = (TextView) findViewById(R.id.textViewNameCompany);
            nameCompany.setText(nCompany);

            tvBuy1 = (TextView) findViewById(R.id.textStep1);
            tvBuy2 = (TextView) findViewById(R.id.textStep2);
            tvBuy3 = (TextView) findViewById(R.id.textStep3);
            tvBuy4 = (TextView) findViewById(R.id.textStep4);

            tvBuy1T = (TextView) findViewById(R.id.textStep1T);
            tvBuy2T = (TextView) findViewById(R.id.textStep2T);
            tvBuy3T = (TextView) findViewById(R.id.textStep3T);
            tvBuy4T = (TextView) findViewById(R.id.textStep4T);

            tvBuy1T.setText(loyalty_id==2 ? "бел. руб" : "покупок");
            tvBuy2T.setText(loyalty_id==2 ? "бел. руб" : "покупок");
            tvBuy3T.setText(loyalty_id==2 ? "бел. руб" : "покупок");
            tvBuy4T.setText(loyalty_id==2 ? "бел. руб" : "покупок");

            tvBuy1.setText(buyStep1);
            tvBuy2.setText(buyStep2);
            tvBuy3.setText(buyStep3);
            tvBuy4.setText(buyStep4);

            //Circle text steps procent
            tvStep1 = (TextView) findViewById(R.id.textViewStep1);
            tvStep2 = (TextView) findViewById(R.id.textViewStep2);
            tvStep3 = (TextView) findViewById(R.id.textViewStep3);
            tvStep4 = (TextView) findViewById(R.id.textViewStep4);


            tvStep1.setText(procentStep1+"%");
            tvStep2.setText(procentStep2+"%");
            tvStep3.setText(procentStep3+"%");
            tvStep4.setText(procentStep4+"%");

            //Progress steps
            progress1 = (ProgressBar) findViewById(R.id.progressStep1);
            progress2 = (ProgressBar) findViewById(R.id.progressStep2);
            progress3 = (ProgressBar) findViewById(R.id.progressStep3);

            colUserBuy = (TextView) findViewById(R.id.textViewColUserBuy);
            if(col_buy==1){
                colUserBuy.setText(userBuy);
            }else {
                colUserBuy.setText(userBuy);
            }

            colUserProcent = (TextView) findViewById(R.id.textViewColUserProcent);


            colUserBalls = (TextView) findViewById(R.id.textViewColUserBalls);
            colUserBalls.setText(userProcent);

            TextView colUserFriends = (TextView) findViewById(R.id.textViewColUserFriends);
            colUserFriends.setText(String.valueOf(s));

            TextView colSumBuy = (TextView) findViewById(R.id.textViewColSumBuy);
            colSumBuy.setText(sumsBuy);


            btnActivate = (Button) findViewById(R.id.buttonActivateBonus);
            if(isActivate==0){
                btnActivate.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.btn_auth_no_active));
                btnActivate.setText("АКТИВИРОВАТЬ БОНУС");
            }
            int step1 = Integer.parseInt(buyStep1);
            int step2 = Integer.parseInt(buyStep2);
            int step3 = Integer.parseInt(buyStep3);
            int step4 = Integer.parseInt(buyStep4);

            if(loyalty_id==3){
                if(col_buy>=step1 && col_buy<step2){
                    tvStep1.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep1);

                    progress1.setMax(step2 - step1 - 1);
                    int x1 = col_buy-step1;

                    //int x = ;
                    progress1.setProgress(x1);

                }else if(col_buy>=step2 && col_buy<step3){
                    tvStep2.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep2);
                    int roz = step2-step1;

                    progress1.setProgress(100);
                    progress2.setMax(100);
                    //progress2.setProgress(x);
                }else if(col_buy>=step3 && col_buy<step4){
                    tvStep3.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep3);
                    progress1.setProgress(100);
                    progress2.setProgress(100);
                    int roz = step2-step1;

                    progress3.setMax(100);
                    //progress3.setProgress(x);
                }else if(col_buy>=step4){
                    tvStep4.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep4);
                    progress1.setProgress(100);
                    progress2.setProgress(100);
                    progress3.setProgress(100);
                }
            }else {

                int sum = Integer.parseInt(sumsBuy);

                if(sum>=step1 && sum<step2){
                    //tvStep1.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep1);

                    //progress1.setMax(step2 - step1 - 1);
                    //int x1 = sum-step1;

                    //int x = ;
                    //progress1.setProgress(x1);

                }else if(sum>=step2 && sum<step3){
                    //tvStep2.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep2);
    //                int roz = step2-step1;
    //
    //                progress1.setProgress(100);
    //                progress2.setMax(100);
                    //progress2.setProgress(x);
                }else if(sum>=step3 && sum<step4){
                    //tvStep3.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep3);
    //                progress1.setProgress(100);
    //                progress2.setProgress(100);
    //                int roz = step2-step1;
    //
    //                progress3.setMax(100);
                    //progress3.setProgress(x);
                }else if(sum>=step4){
                    //tvStep4.setBackground(this.getResources().getDrawable(R.drawable.circle_tv_active));
                    colUserProcent.setText(procentStep4);
    //                progress1.setProgress(100);
    //                progress2.setProgress(100);
    //                progress3.setProgress(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnActivate.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                // do what you want to be done on home button click event
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonActivateBonus:
                activateBonus();
                break;
        }
    }

    public void activateBonus(){

        App.getApi().activateBonus(ids,id_c).enqueue(new Callback<Otvet>() {
            @Override
            public void onResponse(Call<Otvet> call, Response<Otvet> response) {
                if(isActivate==0){
                    btnActivate.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_auth));
                    btnActivate.setText("ДЕАКТИВИРОВАТЬ БОНУС");
                    isActivate = 1;
                }else {

                    btnActivate.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_auth_no_active));
                    btnActivate.setText("АКТИВИРОВАТЬ БОНУС");
                    isActivate = 0;
                }
            }

            @Override
            public void onFailure(Call<Otvet> call, Throwable t) {
                Toast.makeText(NacopitActivity.this,"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
