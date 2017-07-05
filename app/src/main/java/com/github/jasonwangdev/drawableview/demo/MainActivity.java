package com.github.jasonwangdev.drawableview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.github.jasonwangdev.drawableview.DrawableInstantAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private String[] datas = {"SEGSEG", "23234", "測試設", "sveb,,e4,"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                                          android.R.layout.simple_dropdown_item_1line,
                                                          datas);

        DrawableInstantAutoCompleteTextView tv = (DrawableInstantAutoCompleteTextView) findViewById(R.id.tv);
        tv.setAdapter(adapter);
    }

}
