package com.example.x.bonus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.fragments.FragmentListAction;
import com.example.x.bonus.fragments.FragmentPromo;
import com.example.x.bonus.fragments.classes.ActionObject;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Info;
import com.example.x.bonus.retrofit.Mycompany;
import com.example.x.bonus.retrofit.News;
import com.example.x.bonus.retrofit.Userinfo;
import com.example.x.bonus.retrofit.fixwed.InfoF;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoOrganizationsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    private SharedPreferences mSettings;
    CardView cardView;
    RelativeLayout progressBar;
    List<ActionObject> listObject = new ArrayList<>();
    Userinfo usetInfo = new Userinfo();
    String maps = "";
    String name = "";
    ImageView imgLogo,imgCategory, imgIsFixsed;
    TextView textCategory, nameCompany, textTime, textPhone, textAddress, textTypeLoyalty, textSaleCompany, textPhoneCompany;
    RelativeLayout relAddress;
    String id_c = "2";
    String loyalt;
    TabLayout tabLayout;
    boolean isNakopit = false;
    String price = "";
    double step1, step2, step3, step4;
    double step1Col, step2Col, step3Col, step4Col;
    Mycompany mycompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_info_organizations);


            cardView = (CardView) findViewById(R.id.cardSkidka);
            cardView.setOnClickListener(this);
            relAddress = (RelativeLayout) findViewById(R.id.relAddressInfoOrganizations);
            relAddress.setOnClickListener(this);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("АКЦИИ"));
            tabLayout.addTab(tabLayout.newTab().setText("КАБИНЕТ"));
            mSettings = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            String ids = mSettings.getString(APP_PREFERENCES_ID,"");
            progressBar = (RelativeLayout) findViewById(R.id.progressBarInfo);
            Intent intent = getIntent();
            id_c = intent.getStringExtra("id");
            //loyalt = intent.getStringExtra("l");

            getInfoCompany(ids, id_c);


            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            ImageView img = (ImageView) findViewById(R.id.imageViewInfoHome);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            textPhoneCompany = (TextView) findViewById(R.id.textViewPhoneCompany);
            textPhoneCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    call(textPhoneCompany.getText().toString());
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relAddressInfoOrganizations:
                Intent intent = new Intent(InfoOrganizationsActivity.this, MapsActivity.class);
                intent.putExtra("map",maps);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.cardSkidka:
                if(isNakopit){
                    Intent intent1 = new Intent(InfoOrganizationsActivity.this, NacopitActivity.class);
                    intent1.putExtra("id",id_c);
                    startActivity(intent1);
                }
                break;
        }
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        List<ActionObject> list;

        public PagerAdapter(FragmentManager fm, int NumOfTabs, List<ActionObject> list) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentListAction tab1 = new FragmentListAction(InfoOrganizationsActivity.this,list);
                    return tab1;
                case 1:
                    FragmentPromo tab2 = new FragmentPromo(usetInfo, InfoOrganizationsActivity.this,price,name,id_c,mycompany);
                    return tab2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    public void setInfo(String url, String urlCategory, String textNameCategory,
                        String nCompany, String timeCompany, String phoneCompany,
                        String address, int typeLoyalty, String sale){

        //LOGO campany
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


        textTime = (TextView) findViewById(R.id.textViewTimeCompany);
        textTime.setText(timeCompany);

        textPhone = (TextView) findViewById(R.id.textViewPhoneCompany);
        textPhone.setText(phoneCompany);

        textAddress = (TextView) findViewById(R.id.textViewTextAdddress);
        textAddress.setText(address);

        textTypeLoyalty = (TextView) findViewById(R.id.textViewTypeLoyalty);
        imgIsFixsed = (ImageView) findViewById(R.id.imageViewISFIXED);

        if(typeLoyalty==1){
            textTypeLoyalty.setText("Фиксированная");
            imgIsFixsed.setVisibility(View.GONE);
        }else if(typeLoyalty==3)textTypeLoyalty.setText("Накопительная");
        else textTypeLoyalty.setText("От суммы покупки");

        textSaleCompany= (TextView) findViewById(R.id.textViewSaleCompany);
        textSaleCompany.setText(sale);



        progressBar.setVisibility(View.GONE);

        initViewPager();

    }

    private void getInfoCompany(String ids, String id_c){
        progressBar.setVisibility(View.VISIBLE);
        try {
            App.getApi().getInfoUser(ids, id_c).enqueue(new Callback<List<Info>>() {
                @Override
                public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                    List<Info> list = response.body();
                    Info info = list.get(0);
                    mycompany = info.getMycompany();
                    String address = info.getMycompany().getCity().getIndex()+ ", Республика Беларусь, \nг. " +info.getMycompany().getCity().getCity()
                            +", ул."+info.getMycompany().getCity().getAddress()+", "+ info.getMycompany().getCity().getBuild();
                    if(info.getMycompany().getCity().getOffice().length()>0) address = address + ", офис "+ info.getMycompany().getCity().getOffice();

                    int loyalty_id = Integer.parseInt(info.getMycompany().getLoyaltyId());

                    maps = info.getMycompany().getMap();
                    usetInfo = info.getUserinfo();
                    name = info.getMycompany().getName();
                    price = info.getMycompany().getInvitePrice();

                    String sale = "";
                    List<News> news = new ArrayList<News>();

                    if(info.getMycompany().getNews().size()!=0) news = info.getMycompany().getNews();

                    for(int i = 0; i< news.size(); i++){

                        ActionObject object = new ActionObject();
                        object.setName(new ObservableField<String>(news.get(i).getTitle()));
                        object.setTime(new ObservableField<String>(news.get(i).getTime()));
                        object.setImg(new ObservableField<String>(news.get(i).getPhoto()));
                        object.setText(new ObservableField<String>(news.get(i).getText()));

                        listObject.add(object);
                    }



//                    if(loyalty_id==1){
//                        sale = "Скидка "+ info.getMycompany().getSale() + " %";
//                    }else sale = "Скидка "+ info.getMycompany().getLoyaltyStep().getStep1() + " %";

                    if(loyalty_id==2){
                        isNakopit = true;
                        double sum_buy = Double.parseDouble(info.getUserinfo().getSum_buy());

                        step1 = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep1());
                        step2 = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep2());
                        step3 = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep3());
                        step4 = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep4());

                        step1Col = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep1Col());
                        step2Col = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep2Col());
                        step3Col = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep3Col());
                        step4Col = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep4Col());

                        double sales;
                        if(sum_buy>=0 && sum_buy< step2Col) {
                            sales = step1;
                        }else if(sum_buy>step2Col && sum_buy < step3Col) sales = step2;
                        else if(sum_buy>step3Col && sum_buy< step4Col) sales = step3;
                        else sales = step4;

                        sale = "Скидка "+ String.valueOf(info.getMycompany().getLoyaltyStep().getStep1()) + " %";

                    }else if(loyalty_id==3){
                        isNakopit = true;
                        int col_buy = Integer.parseInt(info.getUserinfo().getColBuy());
                        double sales;
                        if(col_buy>=0 && col_buy<Integer.parseInt(info.getMycompany().getLoyaltyStep().getStep2Col())) {

                            sales = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep1());
                        }
                        else if(col_buy>Integer.parseInt(info.getMycompany().getLoyaltyStep().getStep2Col()) && col_buy<Integer.parseInt(info.getMycompany().getLoyaltyStep().getStep3Col())){

                            sales = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep2());
                        }else if(col_buy>Integer.parseInt(info.getMycompany().getLoyaltyStep().getStep3Col()) && col_buy<Integer.parseInt(info.getMycompany().getLoyaltyStep().getStep4Col())){

                            sales = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep3());
                        }else{

                            sales = Double.parseDouble(info.getMycompany().getLoyaltyStep().getStep4());
                        }
                        sale = "Скидка "+ String.valueOf(sales) + " %";
                    }else sale = "Скидка "+ info.getMycompany().getSale() + " %";


                    setInfo(info.getMycompany().getImage(), info.getMycompany().getCategoryId().getIcon(),
                            info.getMycompany().getCategoryId().getCompanyCategory(),info.getMycompany().getName(),
                            info.getMycompany().getTime(),info.getMycompany().getTelephone(),
                            address,Integer.parseInt(info.getMycompany().getLoyaltyId()), sale);
                }

                @Override
                public void onFailure(Call<List<Info>> call, Throwable t) {

                    Toast.makeText(InfoOrganizationsActivity.this,"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getInfoCompanyFixed(String ids, String id_c){
        progressBar.setVisibility(View.VISIBLE);
        App.getApi().getInfoUserFixed(ids,id_c).enqueue(new Callback<List<InfoF>>() {
            @Override
            public void onResponse(Call<List<InfoF>> call, Response<List<InfoF>> response) {
                List<InfoF> list = response.body();
                InfoF info = list.get(0);

                String address = info.getMycompany().getCity().getIndex()+ " , " +info.getMycompany().getCity().getCity()
                        +" , ул."+info.getMycompany().getCity().getAddress()+" "+ info.getMycompany().getCity().getBuild();
                if(info.getMycompany().getCity().getOffice()!= null) address = address + ",оффис "+ info.getMycompany().getCity().getOffice();
                String s = "";

                name = info.getMycompany().getName();
                usetInfo = info.getUserinfo();
                maps = info.getMycompany().getMap();
                price = info.getMycompany().getInvitePrice();
                String sale = "";
                List<News> news = new ArrayList<News>();

                if(info.getMycompany().getNews().size()!=0) news = info.getMycompany().getNews();

                for(int i = 0; i< news.size(); i++){

                    ActionObject object = new ActionObject();
                    object.setName(new ObservableField<String>(news.get(i).getText()));
                    object.setTime(new ObservableField<String>(news.get(i).getTime()));
                    object.setImg(new ObservableField<String>(news.get(i).getPhoto()));

                    listObject.add(object);
                }

                sale = "Скидка "+ info.getMycompany().getSale() + " %";

                setInfo(info.getMycompany().getImage(), info.getMycompany().getCategoryId().getIcon(),
                        info.getMycompany().getCategoryId().getCompanyCategory(),info.getMycompany().getName(),
                        info.getMycompany().getTime(),info.getMycompany().getTelephone(),
                        address,Integer.parseInt(info.getMycompany().getLoyaltyId()), sale);
            }

            @Override
            public void onFailure(Call<List<InfoF>> call, Throwable t) {
                Toast.makeText(InfoOrganizationsActivity.this,"Проверьте подключение к интернету",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViewPager(){
        try {
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new PagerAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), listObject);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void share(String invait){
        final String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String text = this.getResources().getString(R.string.share_text);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text+"\n https://play.google.com/store/apps/details?id="+appPackageName+"\nДарю тебе инвайт-код:"+invait);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ phone));
        startActivity(Intent.createChooser(intent, "Call"));
    }
}
