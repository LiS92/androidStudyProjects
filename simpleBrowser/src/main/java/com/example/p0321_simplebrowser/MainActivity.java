package com.example.p0321_simplebrowser;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnWeb)).setOnClickListener(new View.OnClickListener() {
        //findViewById возвращает View и для него прописывается обработчик
            @Override
            public void onClick(View v) {
                // Анонимный класс в котором создается интент
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ya.ru")));
            }
        });
    }
}
