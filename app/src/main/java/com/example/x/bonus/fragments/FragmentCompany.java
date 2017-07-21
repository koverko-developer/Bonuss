package com.example.x.bonus.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.BottomActivity;
import com.example.x.bonus.*;
import com.example.x.bonus.adapter.OrganizationAdapter;
import com.example.x.bonus.fragments.classes.OrganizationObject;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Company;
import com.example.x.bonus.retrofit.Otvet;
import com.example.x.bonus.retrofit.filter.CategoryFilter;
import com.example.x.bonus.retrofit.filter.CityFilter;
import com.example.x.bonus.retrofit.filter.Filter;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

/**
 * Created by x on 14.06.2017.
 */

@SuppressLint("ValidFragment")
public class FragmentCompany extends Fragment {
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    String ids;
    private SharedPreferences mSettings;
    boolean isAnim = false;
    List<Company> lists = new ArrayList<>();
    ProgressBar progressBar;

    View v;
    BottomActivity activity;
    RecyclerView recyclerView;
    OrganizationAdapter adapter;
    List<OrganizationObject> organizationList = new ArrayList<>();
    List<Company> list = new ArrayList<>();

    Animation animToolbar, animFilter;
    Animation animToolbar2, animFilter2;
    Animation animRecycler1, animRecycler2;
    RelativeLayout toolbarRel, relFilter;

    Spinner spinner, spinner2;


    public FragmentCompany(BottomActivity activity){

        this.activity = activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            v = inflater.inflate(R.layout.fragment_catalog, container, false);

        mSettings = this.getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ids = mSettings.getString(APP_PREFERENCES_ID,"");

        animToolbar = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.toolbar_anim_1);
        animFilter = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.filter_anim_1);

        animToolbar2 = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.toolbar_anim_2);
        animFilter2 = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.filter_anim_2);

        animRecycler1 = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.recycler_anim_1);
        animRecycler2 = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.recycler_anim_2);

        animToolbar.setAnimationListener(animToolbarListener);
        animFilter.setAnimationListener(animFilterListener);

        animToolbar2.setAnimationListener(animToolbarListener2);
        animFilter2.setAnimationListener(animFilterListener2);

        animRecycler1.setAnimationListener(animRecyclerListener1);
        animRecycler2.setAnimationListener(animRecyclerListener2);


        toolbarRel = (RelativeLayout) v.findViewById(R.id.toolbarRel);
        relFilter = (RelativeLayout) v.findViewById(R.id.relFilter);


        progressBar = (ProgressBar) v.findViewById(R.id.progressBarFragmentCatalog);
        ImageView imgExit = (ImageView) v.findViewById(R.id.imageViewExits);
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.showExit();
                new_intent();
            }
        });

        Button btnDone = (Button) v.findViewById(R.id.button2);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort(spinner.getSelectedItem().toString(),"");
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

        Button btnApply = (Button) v.findViewById(R.id.buttonApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAllCompany(ids);


        //setRecyclerView();

        return  v;
    }

    private void getAllCompany(String id){

        App.getApi().getCompany(id).enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {

                List<Company> list = response.body();
                lists = list;
                setRecyclerView(list,1);
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {

            }
        });

    }

    private void setRecyclerView(List<Company> lists, int type){
        organizationList.clear();
        list = lists;
        recyclerView = (RecyclerView) v.findViewById(R.id.relCompany);
        final LinearLayoutManager llm = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        adapter = new OrganizationAdapter(activity,organizationList,this);
        recyclerView.setAdapter(adapter);

        for(int i = 0; i< list.size(); i++){

            OrganizationObject object = new OrganizationObject();

            object.setId(new ObservableField<String>(list.get(i).getId()));
            object.setName(new ObservableField<String>(list.get(i).getName()));
            object.setPhone(new ObservableField<String>(list.get(i).getTelephone()));
            object.setIsMy(new ObservableBoolean(true));
            object.setNameCategory(new ObservableField<String>(list.get(i).getCategoryId().getCompanyCategory()));

            if(list.get(i).getLoyaltyId().contains("1")) object.setSale(new ObservableField<String>(list.get(i).getSale()));
            else if(Integer.parseInt(list.get(i).getLoyaltyId())==2) object.setSale(new ObservableField<String>(list.get(i).getLoyaltyStep().getStep1()));
            else object.setSale(new ObservableField<String>(list.get(i).getLoyaltyStep().getStep1()));

            object.setTime(new ObservableField<String>(list.get(i).getTime()));
            object.setImg(new ObservableField<String>(list.get(i).getImage()));
            object.setImgCategory(new ObservableField<String>(list.get(i).getCategoryId().getIcon()));
            object.setCity(new ObservableField<String>(list.get(i).getCity().getCity()));
            organizationList.add(object);
        }
        TextView tv = (TextView) v.findViewById(R.id.textView41);
        if(organizationList.size()==0) {
            tv.setVisibility(View.VISIBLE);
            if(type==0) tv.setText("По вашему фильтру ничего не найдено...");
        }
        progressBar.setVisibility(View.GONE);

        getFilter();

    }

    private void setSpinner(List<String> arrayCity, List<String> arrayCategory){

        spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (activity,R.layout.item_spinner, arrayCity);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String> (activity,R.layout.item_spinner, arrayCategory);

        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

    }

    private void sort(String city, String category){
        List<Company> newList = new ArrayList<>();
        for(int i =0 ; i< lists.size(); i++){
            if(lists.get(i).getCity().getCity().contains(category) &&
                    lists.get(i).getCategoryId().getCompanyCategory().contains(city)) newList.add(lists.get(i));
        }
        setRecyclerView(newList,0);
    }

    public void cardClick(int position){

        try {
            activity.startActivityNew(organizationList.get(position).getId().get(),
                    list.get(position).getLoyaltyId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickButton(final int position){

        String company_id = organizationList.get(position).getId().get();

        App.getApi().getBonus(ids,company_id).enqueue(new Callback<Otvet>() {
            @Override
            public void onResponse(Call<Otvet> call, Response<Otvet> response) {
                try {
                    organizationList.get(position).setIsMy(new ObservableBoolean(false));

                    new myTaskToDb(activity,organizationList.get(position).getImg().get(),
                            organizationList.get(position).getImgCategory().get(),organizationList.get(position)).execute();



                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Otvet> call, Throwable t) {

            }
        });

    }

    public void exit(){
        activity.showExit();
    }

    public void call(int position){
        activity.call(organizationList.get(position).getPhone().get());
    }

    protected Bitmap Imagehandler(String url) {
        try {
            url=url.replaceAll(" ", "%20");
            InputStream is = (InputStream)this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            String base64 = new String(Base64.encodeBase64(bitmapdata));
            String s = "";
            return bitmap;
        } catch (MalformedURLException e)
        {
            System.out.println(url);
            System.out.println("error at URI"+e);
            return null;
        }
        catch (IOException e)
        {
            System.out.println("io exception: "+e);
            System.out.println("Image NOT FOUND");
            return null;
        }
    }

    protected Object fetch(String address) throws MalformedURLException,IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                URL url = new URL(src);
                is = url.openStream ();
                byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
                int n;

                while ( (n = is.read(byteChunk)) > 0 ) {
                    baos.write(byteChunk, 0, n);
                }

                //String base64Encoded = Base64.getEncoder().encodeToString(byteChunk);
                String base64 = new String(Base64.encodeBase64(byteChunk));
                String sd = "";
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteChunk, 0, byteChunk.length);
                return bitmap;
            }
            catch (IOException e) {

                e.printStackTrace ();
                // Perform any other exception handling that's appropriate.
            }
            finally {
                if (is != null) { is.close(); }
                return null;
            }

        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    class myTaskToDb extends AsyncTask<Void, Void, Void>{

        BottomActivity activity;
        String img, imgC;
        OrganizationObject object;

        public myTaskToDb(BottomActivity activity, String img, String imgC, OrganizationObject object){
            this.activity = activity;
            this.img = img;
            this.imgC = imgC;
            this.object = object;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Bitmap bitmap = Imagehandler(img);
                Bitmap category = Imagehandler(imgC);
                activity.insertInToDB(object, bitmap, category);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
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

    private void getFilter(){
        App.getApi().getFilter().enqueue(new Callback<List<Filter>>() {
            @Override
            public void onResponse(Call<List<Filter>> call, Response<List<Filter>> response) {
                Filter filter = response.body().get(0);
                List<String> list = new ArrayList<String>();
                List<String> list1 = new ArrayList<>();
                List<CategoryFilter> category = filter.getCategory();

                List<CategoryFilter> city = filter.getCategory();
                for(int i = 0; i< category.size(); i++){
                   list1.add(category.get(i).getCompanyCategory());
                }
                List<CityFilter> listCity = filter.getCity();
                for(int i =0 ;i< listCity.size(); i++){
                    list.add(listCity.get(i).getCity());
                }

                setSpinner(list1,list);
            }

            @Override
            public void onFailure(Call<List<Filter>> call, Throwable t) {
                Toast.makeText(activity, "Ошибка подключения к серверу...", Toast.LENGTH_SHORT).show();
            }
        });
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
            //toolbarRel.setAnimation(animToolbar2);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
//            relFilter.setVisibility(View.VISIBLE);
        }
    };


    Animation.AnimationListener animRecyclerListener1 = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {


        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            ViewGroup.MarginLayoutParams marginLayoutParams =
//                    (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
//            marginLayoutParams.setMargins(8, 300, 8, 0);
//
//            recyclerView.setLayoutParams(marginLayoutParams);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    Animation.AnimationListener animRecyclerListener2 = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            recyclerView.setAlpha(Float.parseFloat("1.0"));
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            recyclerView.setAlpha(Float.parseFloat("0.2"));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


}
