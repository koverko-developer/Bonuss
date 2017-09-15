package com.example.x.bonus;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.bonus.firebase.Config;
import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Otvet;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lal.adhish.gifprogressbar.GifView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edLogin, edPassword;
    TextView tv, tvEditPassword;
    Button btn;
    private static final String APP_PREFERENCES = "config";
    private static final String APP_PREFERENCES_TOKEN = "token";
    private static final String APP_PREFERENCES_ID = "id";
    private static final String APP_PREFERENCES_PHONE = "phone";
    private static final String APP_PREFERENCES_IMG = "img";
    private SharedPreferences mSettings;
    Dialog dialogEdit = null;
    ImageView imgDana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

//        Button btnAuth = (Button) findViewById(R.id.buttonAuth);
//        btnAuth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
//
//        Button btnReg = (Button) findViewById(R.id.buttonReg);
//        btnReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
//            }
//        });


    }



    private boolean getValidForm(){
        if(edLogin.getText().toString().length()>1 && edPassword.getText().toString().length()>1) return true;
        else return  false;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void login(){
        try {

            String ss = edPassword.getText().toString();
            //ss = ss.toLowerCase();
            String s = md5(ss);
            s = s.toLowerCase();
            //displayFirebaseRegId();
            App.getApi().login(edLogin.getText().toString(),
                    s
            ).enqueue(new Callback<Otvet>() {
                @Override
                public void onResponse(Call<Otvet> call, retrofit2.Response<Otvet> response) {
                    //List<Otvet> list = response.body();
                    Otvet otvet = response.body();
                    int code = Integer.parseInt(otvet.getCode());
                    if(code==201){
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString(APP_PREFERENCES_TOKEN, otvet.getMessage());
                        editor.putString(APP_PREFERENCES_ID, otvet.getId());
                        editor.putString(APP_PREFERENCES_PHONE, otvet.getPhone());
                        editor.apply();

                        startActivity(new Intent(MainActivity.this, BottomActivity.class));

                    }
                    else Toast.makeText(MainActivity.this, otvet.getMessage(),Toast.LENGTH_SHORT).show();
                    String s = "";
                }

                @Override
                public void onFailure(Call<Otvet> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Проверьте подключение к интернету...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImage(){
        try {
            Picasso.with(imgDana.getContext()).load(R.drawable.bg_login).into(imgDana);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class getBitmap extends AsyncTask<Void,Void, String>{

        URL url;

        public getBitmap(URL url){
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String encodeImage = "";

            try {
                //URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //compress the image to jpg format
                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            } catch (IOException e) {
                // Log exception
                e.printStackTrace();
                return null;
            }

            return encodeImage;
        }

        @Override
        protected void onPostExecute(String str) {

            SharedPreferences.Editor editor1 = mSettings.edit();
            editor1.putString(APP_PREFERENCES_IMG,str);
            editor1.apply();

            startActivity(new Intent(MainActivity.this, BottomActivity.class));

        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true); return true;
        } return super.onKeyDown(keyCode, event);
    }

    public void sendEmail(String email){
        App.getApi().sendEmail(email).enqueue(new Callback<Otvet>() {
            @Override
            public void onResponse(Call<Otvet> call, Response<Otvet> response) {
                switch (Integer.parseInt(response.body().getCode())){
                    case 201:
                        toast(response.body().getMessage());
                        dialogEdit.dismiss();
                        break;
                    case 202:
                        toast(response.body().getMessage());
                        break;
                    case 203:
                        toast(response.body().getMessage());
                        dialogEdit.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Otvet> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialogEditPassword(){

        dialogEdit = new Dialog(this);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.alert_edit_password);


        TextView tvSend = (TextView) dialogEdit.findViewById(R.id.textView44);
        final EditText email = (EditText) dialogEdit.findViewById(R.id.editTextEmailEditP);


        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(email.getText().toString());
                //dialogEdit.dismiss();
            }
        });



        dialogEdit.show();
    }

    private void toast(String s){
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        String s = "";
    }

    private void initialize(){
        try {
            edLogin = (EditText) findViewById(R.id.editTextLog);
            edPassword = (EditText) findViewById(R.id.editTextPass);
            btn = (Button) findViewById(R.id.buttonSign);
            tvEditPassword = (TextView) findViewById(R.id.textView37);
            tvEditPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogEditPassword();
                }
            });

            imgDana = (ImageView) findViewById(R.id.imageViewDana);

            mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

            tv =(TextView) findViewById(R.id.textView5);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getValidForm()) login();
                    else Toast.makeText(MainActivity.this, "Заполните все необходимые поля!", Toast.LENGTH_SHORT).show();
                }
            });

            setImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostResume() {
        try {
            initialize();
            super.onPostResume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
