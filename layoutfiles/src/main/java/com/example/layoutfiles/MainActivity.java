package com.example.layoutfiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Заменяем автоматически созданную активность на созданную нами
        // после создания активности в файле R.java создается константа
        // Которая указывает на активность
        setContentView(R.layout.myscreen);
        myText = (TextView) findViewById(R.id.myText);
        myText.setText("Хуй");


    }
}
