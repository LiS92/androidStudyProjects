package com.example.p0361_sqlitequery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LiS92 on 14.07.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.LOG_TAG, "--- onCreate database ---");

        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement," + "name text,"
                + "people integer," + "region text" + ");");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
