package com.example.x.bonus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.bonus.retrofit.App;
import com.example.x.bonus.retrofit.Otvet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements TextWatcher {

    EditText edFIO, edLOGIN, edPassword, edADDRESS, edPHONE, edPassword2, edInvait;
    String fio, login, password, address, phone, password2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        setContentView(R.layout.activity_registration);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            edFIO = (EditText) findViewById(R.id.editTextFIO);

            edLOGIN = (EditText) findViewById(R.id.editTextEMAIL);
            edLOGIN.addTextChangedListener(this);
            edPassword = (EditText) findViewById(R.id.editTextPASSWORD);
            edPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(edPassword.getText().toString().length()<4) edPassword.setError("менее 4-х символов");
                }
            });
            edPassword2 = (EditText) findViewById(R.id.editTextPASSWORD1);
            //edADDRESS = (EditText) findViewById(R.id.editTextAAddress);
            edPHONE = (EditText) findViewById(R.id.editTextPHONE);
            btn = (Button) findViewById(R.id.buttonREGISTRATION);
            edInvait = (EditText) findViewById(R.id.editTextInvait);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        if(getValid()){
                            if(getValidPassword()) reg();
                            else Toast.makeText(RegistrationActivity.this, "Пароли должны совпадать",Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(RegistrationActivity.this, "Заполните все необходимые поля",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//            edPassword.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    final int DRAWABLE_LEFT = 0;
//                    final int DRAWABLE_TOP = 1;
//                    final int DRAWABLE_RIGHT = 2;
//                    final int DRAWABLE_BOTTOM = 3;
//
//                    if(event.getAction() == MotionEvent.ACTION_UP) {
//                        if(event.getRawX() >= (edPassword.getRight() - edPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                            // your action here
//                            edPassword.setInputType(InputType.TYPE_CLASS_TEXT);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
//
//            edPassword2.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    final int DRAWABLE_LEFT = 0;
//                    final int DRAWABLE_TOP = 1;
//                    final int DRAWABLE_RIGHT = 2;
//                    final int DRAWABLE_BOTTOM = 3;
//
//                    if(event.getAction() == MotionEvent.ACTION_UP) {
//                        if(event.getRawX() >= (edPassword2.getRight() - edPassword2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                            // your action here
//                            edPassword2.setInputType(InputType.TYPE_CLASS_TEXT);
//                            return true;
//                        }
//                    }
//                    return false;
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
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

    private void reg(){
        try {
            App.getApi().regisnrations(login,
                                        password,
                                        fio,
                                        phone,
                                        edInvait.getText().toString()
                    ).enqueue(new Callback<Otvet>() {
                @Override
                public void onResponse(Call<Otvet> call, retrofit2.Response<Otvet> response) {
                    //List<Otvet> list = response.body();
                    Otvet otvet = response.body();
                    Toast.makeText(RegistrationActivity.this, otvet.getMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    String s = "";
                }

                @Override
                public void onFailure(Call<Otvet> call, Throwable t) {
                    Toast.makeText(RegistrationActivity.this, "Проверьте подключение к интернету...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean getValid(){

        fio = edFIO.getText().toString();
        password = md5(edPassword.getText().toString());
        phone = edPHONE.getText().toString();
        address = "null";
        login = edLOGIN.getText().toString();
        password2 = edPassword2.getText().toString();
        String pass = edPassword.getText().toString();


        if(fio.length()>1 && phone.length()>1
                &&pass.length()>1 && address.length()>1
                && login.length()>1 && password2.length()>1)  return true;
        else return false;
    }

    private  boolean getValidPassword(){
        if(edPassword.getText().toString().contains(edPassword2.getText().toString())) return true;
        else  return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if(edLOGIN.getText().toString().contains("@") && edLOGIN.getText().toString().length()>5 && edLOGIN.getText().toString().contains(".") ) {}
        else edLOGIN.setError("Некорректный email");

    }
}
