package com.example.p0381_sqlitetransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";

    DBHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "--- onCreate Activity ---");
        dbh = new DBHelper(this);
        myActions();
    }

    private void myActions() {
        db = dbh.getWritableDatabase();
        delete(db, "mytable");
        db.beginTransaction();
        insert(db, "mytable", "val1");
        db.setTransactionSuccessful();
        insert(db, "mytable", "val2");
        db.endTransaction();
        insert(db, "mytable", "val3");
        read(db, "mytable");
        dbh.close();
        /* Мы подключаемся к базе, чистим таблицу, открываем транзакцию методом beginTransaction,
        вставляем val1, закрываем транзакцию методом endTransaction, вставляем val2, подтверждаем
        успешность транзакции методом setTransactionSuccessful выводим содержимое в лог и
        отключаемся. Все сохраняем, запускаем и смотрим лог:*/
    }
    // insert – набор операций для вставки записи
    private void insert(SQLiteDatabase db, String table, String value) {
        Log.d(LOG_TAG, "Insert in table " + table + " value = " + value);
        ContentValues cv = new ContentValues();
        cv.put("val", value);
        db.insert(table, null, cv);
    }
    // read – чтение всех записей
    private void read(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Read table " + table);
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c != null) {
            Log.d(LOG_TAG, "Records count = " + c.getCount());
            if (c.moveToFirst()) {
                do {
                    Log.d(LOG_TAG, c.getString(c.getColumnIndex("val")));
                } while (c.moveToNext());
            }
            c.close();
        }
    }
    // delete – удаление всех записей
    private void delete(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Delete all from table " + table);
        db.delete(table, null, null);
    }

}
