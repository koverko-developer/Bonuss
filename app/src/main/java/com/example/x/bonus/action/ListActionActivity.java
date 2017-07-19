package com.example.x.bonus.action;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class ListActionActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;

    ArrayList<Shop> arrayListShop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Акции");
        toolbar.setSubtitle("Пинск");

        listView = (ListView) findViewById(R.id.listAllAction);
        progressBar = (ProgressBar) findViewById(R.id.progressBarActionAll);
        new MyTaskGetShops(this,"pinsk").execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(ListActionActivity.this, InfoShopsActivity.class);
                intent1.putExtra("href",arrayListShop.get(i).getHref());
                intent1.putExtra("name", arrayListShop.get(i).getName());
                startActivity(intent1);
            }
        });

    }
    class MyTaskGetShops extends AsyncTask<Void,Void,Void> {

        String col, name;
        ListActionActivity activity;
        boolean isNet = false;

        public MyTaskGetShops(ListActionActivity activity, String name){
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
                isNet = true;
                String s = "";


            } catch (Exception e) {
                e.printStackTrace();
                isNet = false;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if(!isNet) Toast.makeText(ListActionActivity.this,"Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            listView.setAdapter(new ShopsAdapter(ListActionActivity.this, arrayListShop));
        }
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



}
