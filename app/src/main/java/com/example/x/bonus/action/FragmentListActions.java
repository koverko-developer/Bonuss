package com.example.x.bonus.action;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobi app on 26.06.2017.
 */

public class FragmentListActions extends Fragment {

    View v;
    BottomActivity activity;
    ListView listView;
    ProgressBar progressBar;
    TextView tvEmpty;
    Animation animToolbar, animFilter;
    Animation animToolbar2, animFilter2;
    RelativeLayout toolbarRel, relFilter;
    Spinner spinner, spinner2;
    String[] arrCityE;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_CITY = "city";
    String ids;
    private SharedPreferences mSettings;
    String city;
    Button btnDone;


    ArrayList<Shop> arrayListShop = new ArrayList<>();

    public FragmentListActions(){}

    public FragmentListActions(BottomActivity activity){
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            v = inflater.inflate(R.layout.activity_list_action, container, false);

            mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

            arrCityE = v.getResources().getStringArray(R.array.city_en);

            toolbarRel = (RelativeLayout) v.findViewById(R.id.toolbarRel);
            relFilter = (RelativeLayout) v.findViewById(R.id.relFilter);
            city = mSettings.getString(APP_PREFERENCES_CITY,"");
            if(city.equals("")) city = "pinsk";

            animToolbar = AnimationUtils.loadAnimation(this.getContext(),
                    R.anim.toolbar_anim_1);
            animFilter = AnimationUtils.loadAnimation(this.getContext(),
                    R.anim.filter_anim_1);

            animToolbar2 = AnimationUtils.loadAnimation(this.getContext(),
                    R.anim.toolbar_anim_2);
            animFilter2 = AnimationUtils.loadAnimation(this.getContext(),
                    R.anim.filter_anim_2);

            animToolbar.setAnimationListener(animToolbarListener);
            animFilter.setAnimationListener(animFilterListener);

            animToolbar2.setAnimationListener(animToolbarListener2);
            animFilter2.setAnimationListener(animFilterListener2);

            listView = (ListView) v.findViewById(R.id.listAllAction);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBarActionAll);
            new MyTaskGetShops(activity,city).execute();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent1 = null;
                    try {
                        intent1 = new Intent(activity, InfoShopsActivity.class);
                        intent1.putExtra("href",arrayListShop.get(i).getHref());
                        intent1.putExtra("name", arrayListShop.get(i).getName());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(intent1);
                }
            });


            ImageView imgExit = (ImageView) v.findViewById(R.id.imageViewExitListActions);
            imgExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //activity.showExit();
                    new_intent();
                }
            });

            btnDone = (Button) v.findViewById(R.id.button2);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_CITY, arrCityE[spinner.getSelectedItemPosition()]);
                    editor.apply();
                    btnDone.setBackground(v.getResources().getDrawable(R.drawable.btn_filter_click_1));
                    btnDone.setTextColor(v.getResources().getColor(R.color.white));
                    new MyTaskGetShops(activity, arrCityE[spinner.getSelectedItemPosition()]).execute();
                    hideFilter();
                }
            });

            Button btnClose = (Button) v.findViewById(R.id.button3);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideFilter();
                }
            });

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;

            if (currentapiVersion >= 22) {
                // Do something for 14 and above versions
                Drawable imgClose = v.getResources().getDrawable( R.drawable.ic_clear_black_24dp_red);
                Drawable imgDone = v.getResources().getDrawable( R.drawable.ic_done_black_24dp);
                btnDone.setCompoundDrawablesWithIntrinsicBounds(imgDone,null,null,null);
                btnClose.setCompoundDrawablesWithIntrinsicBounds(imgClose,null,null,null);

            }
            setSpinner();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  v;
    }

    class MyTaskGetShops extends AsyncTask<Void,Void,Void> {

        String col, name;
        BottomActivity activity;

        public MyTaskGetShops(BottomActivity activity, String name){
            this.activity = activity;
            this.name = name;
        }
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            arrayListShop.clear();

            HttpURLConnection urlConnection;
            BufferedReader reader;
            String resultJson = null;
            String ling = "Пинск";
            Document doc = null;
            try {
                ling = URLEncoder.encode(ling, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String link = "http://gazetki.by/"+name;
            try {
                doc = Jsoup.connect(link).get();
                Elements ulArr = doc.select("ul.dropdown-menu");
                Elements liArr = ulArr.get(1).select("ul.dropdown-menu").select("li");

                for(int i=1; i< liArr.size(); i++){
                    Element li = liArr.get(i).select("li").first();
                    Element a = li.select("a").first();
                    String name = a.select("span.logo").text().toString();
                    Element imgs = a.select("span.holder").first();
                    Element img = imgs.select("img").first();
                    String imgText = img.attr("src");
                    String href = a.attr("href");
                    String ads = "";

                    arrayListShop.add(new Shop(name, imgText, href));
                }

                String s = "";


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if(arrayListShop.size()==0) Toast.makeText(activity, "Отсутствует подключение к интернету",Toast.LENGTH_SHORT).show();
            listView.setAdapter(new ShopsAdapter(activity, arrayListShop));

        }
    }
    private void new_intent(){

        toolbarRel.startAnimation(animToolbar);
        relFilter.startAnimation(animFilter);

    }
    private void hideFilter(){
        relFilter.startAnimation(animFilter2);
        toolbarRel.startAnimation(animToolbar2);
    }
    private void setSpinner(){
        spinner = (Spinner) v.findViewById(R.id.spinner2);
        String[] arrCiry = v.getResources().getStringArray(R.array.city);


        List<String> arrayCity = new ArrayList<>();
        for(int i=0; i< arrCiry.length; i++){
            arrayCity.add(arrCiry[i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (activity,R.layout.item_spinner, arrayCity);
        spinner.setAdapter(dataAdapter);
    }

    Animation.AnimationListener animToolbarListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            toolbarRel.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //relFilter.startAnimation(animFilter);
            toolbarRel.setVisibility(View.GONE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            toolbarRel.setVisibility(View.VISIBLE);
        }
    };
    Animation.AnimationListener animFilterListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            relFilter.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            relFilter.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            relFilter.setVisibility(View.VISIBLE);
        }
    };

    Animation.AnimationListener animToolbarListener2 = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            toolbarRel.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            toolbarRel.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            toolbarRel.setVisibility(View.VISIBLE);
        }
    };
    Animation.AnimationListener animFilterListener2 = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //toolbarRel.setVisibility(View.VISIBLE);
            relFilter.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            relFilter.setVisibility(View.GONE);
            btnDone.setBackground(v.getResources().getDrawable(R.drawable.btn_filter));
            btnDone.setTextColor(v.getResources().getColor(R.color.black));
            //toolbarRel.setAnimation(animToolbar2);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
//            relFilter.setVisibility(View.VISIBLE);
        }
    };
}
