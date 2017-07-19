package com.example.x.bonus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Otvet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    EditText edLogin, edPassword;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edLogin = (EditText) findViewById(R.id.editTextLog);
        edPassword = (EditText) findViewById(R.id.editTextPass);
        btn = (Button) findViewById(R.id.buttonSign);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getValidForm()) login();
                else Toast.makeText(LoginActivity.this, "Заполните все необходимые поля!", Toast.LENGTH_SHORT).show();
            }
        });

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

    private boolean getValidForm(){
        if(edLogin.getText().toString().length()>1 && edPassword.getText().toString().length()>1) return true;
        else return  false;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void login(){
        try {
            App.getApi().login(edLogin.getText().toString(),
                    md5(edPassword.getText().toString())
            ).enqueue(new Callback<Otvet>() {
                @Override
                public void onResponse(Call<Otvet> call, retrofit2.Response<Otvet> response) {
                    //List<Otvet> list = response.body();
                    Otvet otvet = response.body();
                    int code = Integer.parseInt(otvet.getCode());
                    if(code==201){Toast.makeText(LoginActivity.this, otvet.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(LoginActivity.this, otvet.getMessage(),Toast.LENGTH_SHORT).show();
                    String s = "";
                }

                @Override
                public void onFailure(Call<Otvet> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Проверьте подключение к интернету...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
