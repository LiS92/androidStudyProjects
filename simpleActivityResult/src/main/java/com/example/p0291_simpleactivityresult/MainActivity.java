package com.example.p0291_simpleactivityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvName;
    Button btnName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvName = (TextView) findViewById(R.id.tvName);
        btnName = (Button) findViewById(R.id.btnName);
        btnName.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        /*В методе обработчика onClick создаем Intent, указываем класс второго Acivity
          Для отправки используем startActivityForResult. Отличие от обычного
          startActivity в том, что MainActivity становится «родителем» для NameActivity.
          И когда NameActivity закрывается, вызывается метод onActivityResult в
          MainActivity, тем самым давая нам знать, что закрылось Activity, которое
          мы вызывали методом startActivityForResult.*/

        Intent intent = new Intent(this, NameActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("name");
        tvName.setText("Your name is " + name);
    }
}

