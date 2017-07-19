package com.example.x.bonus.action;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

/**
 * Created by mobi app on 26.06.2017.
 */

public class FragmentListActions extends Fragment {

    View v;
    BottomActivity activity;
    ListView listView;
    ProgressBar progressBar;
    TextView tvEmpty;


    ArrayList<Shop> arrayListShop = new ArrayList<>();

    public FragmentListActions(){}

    public FragmentListActions(BottomActivity activity){
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_list_action, container, false);

        listView = (ListView) v.findViewById(R.id.listAllAction);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBarActionAll);
        new MyTaskGetShops(activity,"pinsk").execute();
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
                activity.showExit();
            }
        });



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
}
