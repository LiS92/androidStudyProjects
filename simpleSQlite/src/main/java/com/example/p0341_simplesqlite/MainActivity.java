package com.example.p0341_simplesqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnAdd.setOnClickListener(this);
        btnUpd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            /*btnAdd – добавление записи в таблицу mytable.  Мы заполняем объект cv парами: имя поля
             и значение. И (при вставке записи в таблицу) в указанные поля будут вставлены
             соответствующие значения. Мы заполняем поля name и email. id у нас заполнится
             автоматически (primary key autoincrement). Вызываем метод insert – передаем ему имя
             таблицы и объект cv с вставляемыми значениями. Второй аргумент метода используется, при
             вставке в таблицу пустой строки. Нам это сейчас не нужно, поэтому передаем null. Метод
             insert возвращает ID вставленной строки, мы его сохраняем в rowID и выводим в лог.*/
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение
                cv.put("name", name);
                cv.put("email", email);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            /*btnRead – чтение всех записей из таблицы mytable. Для чтения используется метод query.
            На вход ему подается имя таблицы, список запрашиваемых полей, условия выборки,
            группировка, сортировка. Т.к. нам нужны все данные во всех полях без сортировок и
            группировок - мы используем везде null. Только имя таблицы указываем. Метод возвращает
            нам объект класса Cursor. Его можно рассматривать как таблицу с данными. Метод
            moveToFirst – делает первую запись в Cursor активной и заодно проверяет, есть ли вообще
            записи в нем (т.е. выбралось ли что-либо в методе query). Далее мы получаем порядковые
            номера столбцов в Cursor по их именам с помощью метода getColumnIndex. Эти номера потом
            используем для чтения данных в методах getInt и getString и выводим данные в лог.
            С помощью метода moveToNext мы перебираем все строки в Cursor пока не добираемся до
            последней. Если же записей не было, то выводим в лог соответствующее сообщение – 0 rows.
            В конце закрываем курсор (освобождаем занимаемые им ресурсы) методом close, т.к. далее
            мы его нигде не используем.*/
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            /*btnClear – очистка таблицы. Метод delete удаляет записи. На вход передаем имя таблицы
            и null в качестве условий для удаления, а значит удалится все. Метод возвращает кол-во
            удаленных записей.*/
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            /*btnUpd – обновление записи в mytable. Проверяем, что значение id не пустое, заполняем
            cv данными для апдейта и обновляем запись. Для этого используется метод update. На вход
            ему подается имя таблицы, заполненный ContentValues с значениями для обновления, строка
            условия (Where) и массив аргументов для строки условия. В строке условия я использовал
            знак ?. При запросе к БД вместо этого знака будет подставлено значение из массива
            аргументов, в нашем случае это – значение переменной id. Если знаков ? в строке условия
            несколько, то им будут сопоставлены значения из массива по порядку. Метод update
            возвращает нам кол-во обновленных записей, которое мы выводим в лог.*/
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                    // Если поле пусто выходим
                }
                Log.d(LOG_TAG, "--- Update mytabe: ---");
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("email", email);
                // обновляем по id
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { id });
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                break;
            /*btnDel – удаление записи из mytable. Проверяем, что id не пустое и вызываем метод
            delete. На вход передаем имя таблицы, строку условия и массив аргументов для условия.
            Метод delete возвращает кол-во удаленных строк, которое мы выводим в лог.*/
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                    // Если поле пусто выходим
                }
                Log.d(LOG_TAG, "--- Delete from mytabe: ---");
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }
}
