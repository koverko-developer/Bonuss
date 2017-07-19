package com.example.x.bonus;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.action.FragmentListActions;
import com.example.x.bonus.db.DBHelper;
import com.example.x.bonus.db.Utilities;
import com.example.x.bonus.firebase.Config;
import com.example.x.bonus.firebase.NotificationUtils;
import com.example.x.bonus.fragments.FragmentCompany;
import com.example.x.bonus.fragments.FragmentListAction;
import com.example.x.bonus.fragments.FragmentQR;
import com.example.x.bonus.fragments.FragmentSale;
import com.example.x.bonus.fragments.classes.OrganizationObject;
import com.example.x.bonus.retrofit.App;
import com.google.firebase.messaging.FirebaseMessaging;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BottomActivity extends AppCompatActivity implements OnBottomNavigationItemClickListener {

    private static final String LOG_TAG = "";
    private static Fragment fragment;
    private FragmentManager fragmentManager;

    private FragmentTransaction transaction;
    private final int IDD_THREE_BUTTONS = 0;
    private final int IDD_EXIT = 1;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    private SharedPreferences mSettings;
    int col =0;

    DBHelper dbHelper;
    SQLiteDatabase database;

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String token = mSettings.getString(APP_PREFERENCES_TOKEN, "");

        if(token.length()>2){

        }else {
            Intent intent = new Intent(BottomActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        try {
            setContentView(R.layout.activity_bottom);



            dbHelper = new DBHelper(this);
            database = dbHelper.getWritableDatabase();

            fragmentManager = getSupportFragmentManager();
//
            fragment = new FragmentQR(BottomActivity.this);

            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

            int[] image = {R.drawable.ic_qrcode_scan ,R.drawable.ic_favorite_border_black_24dp,
                    R.drawable.ic_shopping_cart_black_24dp, R.drawable.ic_sale_menu};
            int[] color = {ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.red),
                    ContextCompat.getColor(this, R.color.blue), ContextCompat.getColor(this, R.color.fiolet)};

            if (bottomNavigationView != null) {
                bottomNavigationView.isWithText(false);
                // bottomNavigationView.activateTabletMode();
                bottomNavigationView.isColoredBackground(true);
                bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
                bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
                bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.white));

            }

            BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                    ("QR", color[0], image[0]);
            BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                    ("Скидки", color[1], image[1]);
            BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                    ("Каталог", color[2], image[2]);
            BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                    ("Акции", color[3], image[3]);


            bottomNavigationView.addTab(bottomNavigationItem);
            bottomNavigationView.addTab(bottomNavigationItem3);
            bottomNavigationView.addTab(bottomNavigationItem1);
            bottomNavigationView.addTab(bottomNavigationItem2);
            bottomNavigationView.setOnBottomNavigationItemClickListener(this);

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }


    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true); return true;
        } return super.onKeyDown(keyCode, event);
    }

    public void startActivityNew(String id, String loualty){
        try {
            Intent intent = new Intent(this, InfoOrganizationsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("l", loualty);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onNavigationItemClick(int index) {
        switch (index) {
                        case 0:
                            fragment = new FragmentQR(BottomActivity.this);
                            break;
                        case 2:
                            fragment = new FragmentSale(BottomActivity.this);
                            break;
                        case 3:
                            fragment = new FragmentCompany(BottomActivity.this);
                            break;
                        case 1:
                            fragment = new FragmentListActions(BottomActivity.this);
                            break;
                    }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public Dialog onCreateDialog(int id) {

        switch (id) {
            case IDD_THREE_BUTTONS:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Скачать \"ВТЕМЕ для бизнеса\"?")
                        .setCancelable(false)
                        .setPositiveButton("Скачать",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        playMarket();
                                    }
                                })
                        .setNegativeButton("Позже",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder.create();
            case IDD_EXIT:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Выйти из аккаунта?")
                        .setCancelable(false)
                        .setPositiveButton("ДА",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        exit();
                                    }
                                })
                        .setNegativeButton("ОТМЕНА",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });

                return builder1.create();
            default:
                return null;
        }


    }

    public void create(){
        try {

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        showDialog(IDD_THREE_BUTTONS);
    }
    public void showExit(){
        showDialog(IDD_EXIT);
    }

    public void playMarket(){
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "by.anikamobiappstudio.vtemebusiness")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "by.anikamobiappstudio.vtemebusiness")));
        }
    }

    public void exit(){
        SharedPreferences.Editor editor1 = mSettings.edit();
        editor1.putString(APP_PREFERENCES_TOKEN,"");
        editor1.apply();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ phone));
        startActivity(Intent.createChooser(intent, "Call"));
    }


    public void insertInToDB(OrganizationObject object, Bitmap imgLogo, Bitmap imgCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAMEO, object.getName().get());
        contentValues.put(DBHelper.KEY_NAMEC, object.getNameCategory().get());
        contentValues.put(DBHelper.KEY_CITY, object.getCity().get());
        contentValues.put(DBHelper.KEY_PHONE, object.getPhone().get());
        contentValues.put(DBHelper.KEY_TIME, object.getTime().get());
        contentValues.put(DBHelper.KEY_SALE, object.getSale().get());
        contentValues.put(DBHelper.KEY_IMGO, Utilities.getBytes(imgLogo));
        contentValues.put(DBHelper.KEY_IMGC, Utilities.getBytes(imgCategory));

        database.insert(DBHelper.TABLE_COMPANY, null, contentValues);
    }

//    public List<OrganizationObject> readDB(){
//
//        List<OrganizationObject> organizationList = new ArrayList<>();
//
//        try {
//            Cursor c = database.query(DBHelper.TABLE_COMPANY, null, null, null, null, null, null);
//
//            if (c.moveToFirst()) {
//                int _id = c.getColumnIndex("_id");
//                int _nameo = c.getColumnIndex("_nameo");
//                int _imgo = c.getColumnIndex("_imgo");
//                int _namec = c.getColumnIndex("_namec");
//                int _imgc = c.getColumnIndex("_imgc");
//                int _city = c.getColumnIndex("_city");
//                int _time = c.getColumnIndex("_time");
//                int _phone = c.getColumnIndex("_phone");
//                int _sale = c.getColumnIndex("_sale");
//
//                String sale = c.getString(_sale);
//                sale = sale.substring(0,sale.length()-1);
//                byte[] byteImgLogo = c.getBlob(_imgo);
//                byte[] byteImgCategory = c.getBlob(_imgc);
//                String base64 = new String(Base64.encodeBase64(byteImgLogo));
//                String base64Category = new String(Base64.encodeBase64(byteImgCategory));
//                String strLogo = byteImgLogo.toString();
//
//                OrganizationObject object = new OrganizationObject();
//                object.setName(new ObservableField<String>(c.getString(_nameo)));
//                object.setNameCategory(new ObservableField<String>(c.getString(_namec)));
//                object.setCity(new ObservableField<String>(c.getString(_city)));
//                object.setTime(new ObservableField<String>(c.getString(_time)));
//                object.setPhone(new ObservableField<String>(c.getString(_phone)));
//                object.setSale(new ObservableField<String>(sale));
//                object.setImg(new ObservableField<String>(base64));
//                object.setImgCategory(new ObservableField<String>(base64Category));
//
//                organizationList.add(object);
//
//
//
//                String nameo = c.getString(_nameo);
//                String s = "";
//            }
//
////            // ставим позицию курсора на первую строку выборки
////            // если в выборке нет строк, вернется false
////            if (c.moveToFirst()) {
////
////                // определяем номера столбцов по имени в выборке
////
////
////                do {
////
////                    // получаем значения по номерам столбцов и пишем все в лог
////                    Log.d(LOG_TAG,
////                            "ID = " + c.getInt(_id) +
////                                    ", name = " + c.getString(_nameo) +
////                                    ", email = " + c.getString(_imgo));
////
////                    // переход на следующую строку
////                    // а если следующей нет (текущая - последняя), то false - выходим из цикла
////                } while (c.moveToNext());
////            } else
////                Log.d(LOG_TAG, "0 rows");
//            c.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //dbHelper.close();
//
//        return organizationList;
//    }

    public List<OrganizationObject> readDB(){
        boolean isDownload = false;

        List<OrganizationObject> organizationList = new ArrayList<>();

        try {
            Cursor c = database.query(DBHelper.TABLE_COMPANY, null, null,null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {

                int _id = c.getColumnIndex("_id");
                int _nameo = c.getColumnIndex("_nameo");
                int _imgo = c.getColumnIndex("_imgo");
                int _namec = c.getColumnIndex("_namec");
                int _imgc = c.getColumnIndex("_imgc");
                int _city = c.getColumnIndex("_city");
                int _time = c.getColumnIndex("_time");
                int _phone = c.getColumnIndex("_phone");
                int _sale = c.getColumnIndex("_sale");

                String sale = c.getString(_sale);
                sale = sale.substring(0,sale.length()-1);
                byte[] byteImgLogo = c.getBlob(_imgo);
                byte[] byteImgCategory = c.getBlob(_imgc);
                String base64 = new String(Base64.encodeBase64(byteImgLogo));
                String base64Category = new String(Base64.encodeBase64(byteImgCategory));
                String strLogo = byteImgLogo.toString();

                OrganizationObject object = new OrganizationObject();
                object.setName(new ObservableField<String>(c.getString(_nameo)));
                object.setNameCategory(new ObservableField<String>(c.getString(_namec)));
                object.setCity(new ObservableField<String>(c.getString(_city)));
                object.setTime(new ObservableField<String>(c.getString(_time)));
                object.setPhone(new ObservableField<String>(c.getString(_phone)));
                object.setSale(new ObservableField<String>(sale));
                object.setImg(new ObservableField<String>(base64));
                object.setImgCategory(new ObservableField<String>(base64Category));

                organizationList.add(object);
                c.moveToNext();
                //cursor.close();
            }
            // make sure to close the cursor

//            if (cursor.moveToFirst()) {
//                do {
//                    //Toast.makeText(ctx,cursor.getString(nameIndex),Toast.LENGTH_SHORT).show();
//                } while (cursor.moveToNext());
//
//
//                return true;
//            }



            c.close();
            //dbHelper.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return organizationList;
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
        //if (!TextUtils.isEmpty(regId))
            //txtRegId.setText("Firebase Reg Id: " + regId);
       // else
            //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

}
