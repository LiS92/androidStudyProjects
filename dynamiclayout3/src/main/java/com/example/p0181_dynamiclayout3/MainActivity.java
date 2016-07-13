package com.example.p0181_dynamiclayout3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar sbWeight;
    Button btn1;
    Button btn2;

    LinearLayout.LayoutParams lParams1;
    LinearLayout.LayoutParams lParams2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sbWeight = (SeekBar) findViewById(R.id.sbWeight);
        sbWeight.setOnSeekBarChangeListener(this);
//        Назначаем обработчик
        
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        lParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
        lParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();
        /*Мы используем метод getLayoutParams для получения LayoutParams компонента.
         Но этот метод возвращает базовый ViewGroup.LayoutParams, а нам нужен LinearLayout.
         LayoutParams, поэтому делаем преобразование. В результате - lParams1 и lParams2
         теперь являются LayoutParams для компонентов btn1 и btn2.*/
    }

    @Override
//    - onProgressChanged срабатывает все время, пока значение меняется
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        int leftValue = progress;
        int rightValue = seekBar.getMax() - progress;
        // настраиваем вес
        lParams1.weight = leftValue;
        lParams2.weight = rightValue;
        // в текст кнопок пишем значения переменных
        btn1.setText(String.valueOf(leftValue));
        btn2.setText(String.valueOf(rightValue));
        /*  переменная leftValue – текущее значение SeekBar, т.е. то что слева от ползунка
            переменная rightValue – то, что справа от ползунка, т.е. из макс вычесть текущее знач.*/
    }

    @Override
//    - onStartTrackingTouch срабатывает, когда начинаем тащить ползунок
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
//    - onStopTrackingTouch срабатывает, когда отпускаем ползунок
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
