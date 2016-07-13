package com.example.p0311_simpleintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnWeb;
    Button btnMap;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWeb = (Button) findViewById(R.id.btnWeb);
        btnMap = (Button) findViewById(R.id.btnMap);
        btnCall = (Button) findViewById(R.id.btnCall);

        btnWeb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnWeb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
                startActivity(intent);
                break;
            case R.id.btnMap:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:55.754283,37.62002"));
                startActivity(intent);
                break;
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:12345"));
                startActivity(intent);
                break;
            /*В случае btnWeb я использовал конструктор Intent (String action, Uri uri).
              Он создает Intent и на вход сразу принимает action и data. Мы используем
              стандартный системный action – ACTION_VIEW. Это константа в классе Intent –
              означает, что мы хотим просмотреть что-либо. В качестве data мы подаем объект
              Uri, созданный из веб-ссылки: http://developer.android.com. И если попытаться
              описать словами наш код, то получится так: этот Intent означает, что мы хотим
              посмотреть содержимое этой ссылки и ищем Activity, которая могла бы нам помочь.

              В случае btnMap использовался конструктор Intent(). Он просто создает Intent.
              А в следующих строках мы уже присваиваем ему атрибуты action и data.
              action – снова ACTION_VIEW, а в качестве data мы создаем Uri из пары координат
              - 55.754283,37.62002. Этот Intent означает, что мы хотим посмотреть на карте
              указанные координаты.

              В случае btnCall используем конструктор Intent (String action). На вход ему
              сразу подается action, а data указывается позже. action в данном случае –
              ACTION_DIAL – открывает звонилку и набирает номер, указанный в data, но не
              начинает звонок. В data – помещаем Uri, созданный из номера телефона 12345.*/
        }
    }
}
