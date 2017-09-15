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
import android.widget.Toast;

import com.example.x.bonus.R;
import com.example.x.bonus.retrofit.App;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            //toolbar.setSubtitle("Пинск");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getIngoShop(href);
        //new MyTaskGetShops(InfoShopsActivity.this, href).execute();
    }

    private void getIngoShop(String url){
        progressBar.setVisibility(View.VISIBLE);
        try {
            App.getApi().getInfoAction(url).enqueue(new Callback<List<ExampeInfoShop>>() {
                @Override
                public void onResponse(Call<List<ExampeInfoShop>> call, Response<List<ExampeInfoShop>> response) {
                    List<ExampeInfoShop> list = response.body();
                    if(list.get(0).getInfo()!=null){
                        List<Info> infos = list.get(0).getInfo();
                        arrayList.clear();

                        for(Info info : infos){
                            arrayList.add(new InfoActionShop(info.getTitle(), info.getPrice(), info.getSale(),
                                    info.getTime(), info.getImage()));
                        }
                    }
                    if(arrayList.size()!=0)listView.setAdapter(new AdapterActionShops(InfoShopsActivity.this, arrayList));
                    else tvEmpty.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<ExampeInfoShop>> call, Throwable t) {
                    Toast.makeText(InfoShopsActivity.this, "Проверьте подключениие к интернету", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
