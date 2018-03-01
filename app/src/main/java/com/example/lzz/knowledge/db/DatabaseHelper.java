package com.example.lzz.knowledge.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2018/1/26.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Zhihu ("
            + "id integer primary key autoincrement,"
            + "zhihu_id integer not null,"
            + "zhihu_news text,"
            + "zhihu_time real)");

        db.execSQL("alter table Zhihu add column bookmark integer default 0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
