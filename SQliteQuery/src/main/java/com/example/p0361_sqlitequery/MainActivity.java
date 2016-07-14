package com.example.p0361_sqlitequery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String LOG_TAG = "myLogs";

    String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада"};
    // Список стран
    int people[] = {1400, 311, 195, 142, 128, 82, 80, 60, 66, 35};
    // Население по странам
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка"};
    // Региональный признак

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    EditText etFunc, etPeople, etRegionPeople;
    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = (Button) findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etFunc = (EditText) findViewById(R.id.etFunc);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);
        // подключаемся к базе
        db = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            // заполним таблицу
            for (int i = 0; i < 10; i++) {
                cv.put("name", name[i]);
                cv.put("people", people[i]);
                cv.put("region", region[i]);
                Log.d(LOG_TAG, "id = " + db.insert("mytable", null, cv));
            }
        }
        c.close();
        dbHelper.close();

        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);
    }

    @Override
    public void onClick(View view) {
        // подключаемся к базе
        db = dbHelper.getWritableDatabase();

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // курсор
        Cursor c = null;

        // определяем нажатую кнопку
        switch (view.getId()) {
            // Все записи
            case R.id.btnAll:
                /*btnAll – вывод всех записей. Вызываем метод query с именем таблицы и null для
                остальных параметров.*/
                Log.d(LOG_TAG, "--- Все записи ---");
                c = db.query("mytable", null, null, null, null, null, null);
                break;

            // Функция
            case R.id.btnFunc:
                /*btnFunc – вывод значения агрегатной функции (или любого поля). Использую параметр
                columns, в который надо записать поля, которые я хотел бы получить из таблицы, т.е.
                то, что обычно перечисляется после слова SELECT в SQL-запросе. columns имеет тип
                String[] – массив строк. Создаем массив из одного значения, которое считано с поля
                etFunc на экране. Запускаем query.*/
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                columns = new String[] { sFunc };
                c = db.query("mytable", columns, null, null, null, null, null);
                break;

            // Население больше, чем
            case R.id.btnPeople:
                /*btnPeople – вывод стран с населением больше введенного на экране количества.
                Используем selection для формирования условия. При этом используем один аргумент - ?
                Значение аргумента задаем в selectionArgs – это sPeople – содержимое поля etPeople.
                Запускаем query.*/
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] { sPeople };
                c = db.query("mytable", null, selection, selectionArgs, null, null,
                        null);
                break;

            // Население по региону
            case R.id.btnGroup:
                /*btnGroup – группировка стран по регионам и вывод общее количество населения.
                Используем columns для указания столбцов, которые хотели бы получить – регион и
                сумма населения. В groupBy указываем, что группировка будет по региону. Запускаем
                query.*/
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                c = db.query("mytable", columns, null, null, groupBy, null, null);
                break;

            // Население по региону больше чем
            case R.id.btnHaving:
                /*btnHaving – вывод регионов с населением больше указанного числа. Полностью
                аналогично случаю с группировкой, но добавляется условие в параметре having – сумма
                населения региона должна быть меньше sRegionPeople (значение etRegionPeople с
                экрана).*/
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople
                        + " ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                c = db.query("mytable", columns, null, null, groupBy, having, null);
                break;

            // Сортировка
            case R.id.btnSort:
                /*btnSort – сортировка стран. Определяем какой RadioButton включен и соответственно
                указываем в orderBy поле для сортировки данных. Запускаем query*/
                switch (rgSort.getCheckedRadioButtonId()) {
                    // Находим выбранную радио кноп1
                    // наименование
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    // население
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    // регион
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        // Обработчик курсора
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);

                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        dbHelper.close();
    }
}
