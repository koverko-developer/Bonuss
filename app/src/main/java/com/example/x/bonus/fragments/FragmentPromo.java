package com.example.x.bonus.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.InfoOrganizationsActivity;
import com.example.x.bonus.NacopitActivity;
import com.example.x.bonus.R;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Mycompany;
import com.example.x.bonus.retrofit.Otvet;
import com.example.x.bonus.retrofit.Userinfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x on 15.06.2017.
 */

@SuppressLint("ValidFragment")
public class FragmentPromo extends Fragment {

    Userinfo userinfo;
    InfoOrganizationsActivity activity;

    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    String price;
    String name;
    String id;
    String id_c;
    boolean isActive = false;
    int isActivate;
    Mycompany mycompany;

    private SharedPreferences mSettings;

    public FragmentPromo(Userinfo userinfo, InfoOrganizationsActivity activity, String price, String name, String id_c, Mycompany mycompany){
        this.userinfo = userinfo;
        this.activity = activity;
        this.price = price;
        this.name = name;
        this.id_c = id_c;
        this.mycompany = mycompany;
    }

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_promo, container, false);
        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        id = mSettings.getString(APP_PREFERENCES_ID,"");
        final String text = id;

        ImageView imgShare = (ImageView) v.findViewById(R.id.imageViewSharePromo);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(price)!=0)activity.share("VTEME2017"+text);
                else Toast.makeText(activity, "Данная функция продавцом не активирована...", Toast.LENGTH_SHORT).show();
            }
        });

        isActivate = Integer.parseInt(userinfo.getStatusCompanyUser());

        setInfo(id);

        return v;
    }

    private void setInfo(final String id){
        TextView tvBalls = (TextView) v.findViewById(R.id.textViewYourBalls);
        TextView tvBuy = (TextView) v.findViewById(R.id.textViewYourBuy);
        TextView tvinvait = (TextView) v.findViewById(R.id.textViewYourInvait);
        TextView tvGT = (TextView) v.findViewById(R.id.textViewGT);
        TextView tvFN = (TextView) v.findViewById(R.id.textViewFN);
        TextView tvColF = (TextView) v.findViewById(R.id.textViewColFriend);
        TextView tvLastBuy = (TextView) v.findViewById(R.id.textViewLastBuy);
        TextView tvPriceLast = (TextView) v.findViewById(R.id.textViewPriceBuyLast);
        Button btnActivate = (Button) v.findViewById(R.id.buttonActivateBonus);
        RelativeLayout relShare = (RelativeLayout) v.findViewById(R.id.relShare);
        relShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(Double.parseDouble(price)!=0 && Integer.parseInt(userinfo.getColBuy())!=0) activity.share("VTEME2017"+id);
                    else if(Double.parseDouble(price)==0) Toast.makeText(activity, "Данная функция продавцом не активирована...", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(activity, "Вам нужно совершить хотя бы одну покупку...", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        if(Integer.parseInt(userinfo.getColBal())>0 && Integer.parseInt(userinfo.getColBuy())>0){
            btnActivate.setBackground(getResources().getDrawable(R.drawable.btn_auth));
        }else{

        }

        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(userinfo.getColBal())>0 && Integer.parseInt(userinfo.getColBuy())>0)activateBonus();
                else if(Integer.parseInt(userinfo.getColBal())==0)  Toast.makeText(activity, "У Вас в наличии нет бонусов",Toast.LENGTH_SHORT).show();
                else Toast.makeText(activity, "Вам нужно совершить хотя бы одну покупку...",Toast.LENGTH_SHORT).show();
//
//                if(Integer.parseInt(userinfo.getColBal())==0 && Integer.parseInt(userinfo.getColBuy())>0){
//                    Toast.makeText(activity, "У Вас в наличии нет бонусов",Toast.LENGTH_SHORT).show();
//                }else {
//                    activateBonus();
//                }


            }
        });

        tvinvait.setText("VTEME2017"+id);
        tvBalls.setText(userinfo.getColBal());
        tvBuy.setText(userinfo.getColBuy());
        String gt = String.format("Дарите своим друзьям скидку %s BYN и получайте \nза это %s BYN бонусов!",price, price);
        String fn = String.format("После своей 1-ой покупки вы отправляете другу, " +
                "знакомому или коллеге свой персональный промо-код " +
                "любым удобным способом. Сделав покупку ваш друг " +
                "может использовать этот промо-код и получить " +
                "скидку %s BYN на свою ПЕРВУЮ покупку в " +
                "\" %s \". После этого вы получите также " +
                "бонусы, за которые сможете получить скидку. " +
                "\n\n\n" +
                "Скидка предоставляется 1 РАЗ и только для новых " +
                "пользователей, которых вы пригласите в сервис.\n\n " +
                "А вот количество друзей, которых вы пригласите - " +
                "не ограничено.",price,name);
        tvFN.setText(fn);
        tvGT.setText(gt);
        tvColF.setText(userinfo.getCol_friend());
        tvLastBuy.setText(userinfo.getLastBuying());
        tvPriceLast.setText(userinfo.getPrice_last_buying());
    }

    public void activateBonus(){
        final Button btnActivate = (Button) v.findViewById(R.id.buttonActivateBonus);
        App.getApi().activateBonus(id,id_c).enqueue(new Callback<Otvet>() {
            @Override
            public void onResponse(Call<Otvet> call, Response<Otvet> response) {
               startActivity(new Intent(activity, BottomActivity.class));
            }

            @Override
            public void onFailure(Call<Otvet> call, Throwable t) {
                Toast.makeText(activity,"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
