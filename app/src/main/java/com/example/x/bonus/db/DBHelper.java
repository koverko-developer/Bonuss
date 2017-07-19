package com.example.x.bonus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mobi app on 07.07.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mycompany";
    public static final String TABLE_COMPANY = "company";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String KEY_ID = "_id";
    public static final String KEY_NAMEO = "_nameo";
    public static final String KEY_IMGO = "_imgo";
    public static final String KEY_NAMEC = "_namec";
    public static final String KEY_IMGC = "_imgc";
    public static final String KEY_CITY = "_city";
    public static final String KEY_TIME = "_time";
    public static final String KEY_PHONE = "_phone";
    public static final String KEY_SALE = "_sale";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_COMPANY + "(" + KEY_ID
                + " integer primary key," + KEY_NAMEO + " text,"+ KEY_IMGO + " blob,"+
                KEY_NAMEC + " text,"+ KEY_IMGC + " blob,"+
                KEY_CITY + " text,"+ KEY_TIME + " text,"+
                KEY_PHONE + " text,"+ KEY_SALE + " text"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COMPANY);
        onCreate(db);
    }


}
