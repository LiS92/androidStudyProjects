package com.example.p0301_activityresult;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvText;
    Button btnColor;
    Button btnAlign;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;
//  Эти константы далее будем использовать в качестве requestCode.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView) findViewById(R.id.tvText);

        btnColor = (Button) findViewById(R.id.btnColor);
        btnAlign = (Button) findViewById(R.id.btnAlign);

        btnColor.setOnClickListener(this);
        btnAlign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.btnAlign:
                intent = new Intent(this, AlignActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGN);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.WHITE);
                    tvText.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGN:
                    int align = data.getIntExtra("alignment", Gravity.LEFT);
                    tvText.setGravity(align);
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }

        /*Если вызов прошел успешно (resultCode = RESULT_OK), то мы смотрим значение
         requestCode. Если оно равно константе REQUEST_CODE_COLOR, то вспоминаем,
         что мы использовали эту константу в методе startActivityForResult, когда
         отправляли запрос на выбор цвета. Значит, нам пришел результат этого выбора.
         Мы берем Intent (data) и извлекаем из него значение объекта с именем color и
         присваиваем это значение цвету текста в TextView. Константа Color.WHITE в методе
         getIntExtra означает значение по умолчанию. Т.е. если в Intent не найдется
         объекта с именем color, то метод вернет белый (white) цвет.

         Аналогично для REQUEST_CODE_ALIGN. Эту константу мы использовали для запроса
         выбора выравнивания. И если в методе onActivityResult параметр
         requestCode = этой константе, значит пришел ответ на запрос выравнивания.
         И мы считываем это значение из Intent и присваиваем его атрибуту Gravity для
         TextView.

         Если resultCode не равен RESULT_OK, значит что-то пошло не так. Выводим на экран
         соответствующее сообщение. Этот случай может наступить, например, если на экране
         выбора не делать выбор, а нажать кнопку Назад.*/
    }
}
