package com.example.p0331_sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etText;
    Button btnSave, btnLoad;

    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.etText);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                saveText();
                // Сохранить текст в spreference
                break;
            case R.id.btnLoad:
                loadText();
                // Загрузить текст из spreference
                break;
            default:
                break;
        }
    }

    private void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        /* Получаем обьект класса SharedPreference, он позволяет читать|писать данные
        MODE_PRIVATE - константа, указывает на то что после записи данные будут видны
        только данному приложению.*/
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, etText.getText().toString());
        ed.commit();
        /*Для редактирования данных необходим обьект Editor получаем его из sPref
        putString указывает какие данные положить в обьект sPref (ключ|значение)
        commit завершает процедуру */
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    private void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        /*Достает из обьекта строку по ключу, второе значение по умолчанию
        если данный ключ не будет найден*/
        etText.setText(savedText);
        Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
}