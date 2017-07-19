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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class InfoShopsActivity extends AppCompatActivity {

    ArrayList<InfoActionShop> arrayList = new ArrayList<>();
    ListView listView;
    ProgressBar progressBar;
    TextView tvEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String href = null;
        try {
            setContentView(R.layout.activity_info_shops);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            progressBar = (ProgressBar) findViewById(R.id.progressBarInfoShops);
            listView = (ListView) findViewById(R.id.listViewInfoShops);
            tvEmpty = (TextView) findViewById(R.id.textView36);

            Intent intent = getIntent();
            href = intent.getStringExtra("href");
            String title = intent.getStringExtra("name");
            getSupportActionBar().setTitle("Акции, "+ title);
            toolbar.setSubtitle("Пинск");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MyTaskGetShops(InfoShopsActivity.this, href).execute();
    }

    class MyTaskGetShops extends AsyncTask<Void,Void,Void> {

        String col, name;
        InfoShopsActivity activity;

        public MyTaskGetShops(InfoShopsActivity activity, String name){
            this.activity = activity;
            this.name = name;
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
            String link = "http://gazetki.by"+name;
            try {
                doc = Jsoup.connect(link).get();
                Elements ulArr = doc.select("ul.promotions");
                Elements arrLi = ulArr.select("li.promotion");

                for(int i = 0; i< arrLi.size(); i++){

                    String img = null;//картинка
                    String titleA = null;// название
                    String term = null;//время
                    String price = null;//цена
                    try {
                        Element divImage = arrLi.get(i).select("div.image").first();
                        Element divData = arrLi.get(i).select("div.data").first();
                        Element a = divImage.select("img").first();
                        img = "http://gazetki.by"+ a.attr("src");
                        Elements skidka = arrLi.get(i).select("div.image");
                        String skidkaS = skidka.text().toString(); // скидка
                        titleA = divData.select("a").first().text().toString();
                        Elements divTerms = divData.select("div.terms").first().select("span");
                        term = divTerms.get(1).text().toString();
                        price = divData.select("div.prices").select("strong").first().text().toString()+"\n" + " руб";
                        String s = "";
                        arrayList.add(new InfoActionShop(titleA,price,"",term, img));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            String link2 = "http://gazetki.by"+name+"/2";
            try {
                doc = Jsoup.connect(link2).get();
                Elements ulArr = doc.select("ul.promotions");
                Elements arrLi = ulArr.select("li.promotion");

                for(int i = 0; i< arrLi.size(); i++){

                    String img = null;//картинка
                    String titleA = null;// название
                    String term = null;//время
                    String price = null;//цена
                    try {
                        Element divImage = arrLi.get(i).select("div.image").first();
                        Element divData = arrLi.get(i).select("div.data").first();
                        Element a = divImage.select("img").first();
                        img = "http://gazetki.by"+ a.attr("src");
                        Elements skidka = arrLi.get(i).select("div.image");
                        String skidkaS = skidka.text().toString(); // скидка
                        titleA = divData.select("a").first().text().toString();
                        Elements divTerms = divData.select("div.terms").first().select("span");
                        term = divTerms.get(1).text().toString();
                        price = divData.select("div.prices").select("strong").first().text().toString()+ "\n"+ " руб";
                        String s = "";
                        arrayList.add(new InfoActionShop(titleA,price,"",term, img));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }String link3 = "http://gazetki.by"+name+"/3";
            try {
                doc = Jsoup.connect(link3).get();
                Elements ulArr = doc.select("ul.promotions");
                Elements arrLi = ulArr.select("li.promotion");

                for(int i = 0; i< arrLi.size(); i++){

                    String img = null;//картинка
                    String titleA = null;// название
                    String term = null;//время
                    String price = null;//цена
                    try {
                        Element divImage = arrLi.get(i).select("div.image").first();
                        Element divData = arrLi.get(i).select("div.data").first();
                        Element a = divImage.select("img").first();
                        img = "http://gazetki.by"+ a.attr("src");
                        Elements skidka = arrLi.get(i).select("div.image");
                        String skidkaS = skidka.text().toString(); // скидка
                        titleA = divData.select("a").first().text().toString();
                        Elements divTerms = divData.select("div.terms").first().select("span");
                        term = divTerms.get(1).text().toString();
                        price = divData.select("div.prices").select("strong").first().text().toString() + " руб";
                        String s = "";
                        arrayList.add(new InfoActionShop(titleA,price,"",term, img));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(arrayList.size()!=0)listView.setAdapter(new AdapterActionShops(InfoShopsActivity.this, arrayList));
            else tvEmpty.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
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
