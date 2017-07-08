package com.github.jasonwangdev.drawableview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.github.jasonwangdev.drawableview.DrawableInstantAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> datas = new ArrayList<>();
        for (int i = 0 ; i < 20000 ; i++)
            datas.add("Item " + i);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                                          android.R.layout.simple_dropdown_item_1line,
                                                          datas);

        DrawableInstantAutoCompleteTextView tv = (DrawableInstantAutoCompleteTextView) findViewById(R.id.tv);
        tv.setCanInput(false);
        tv.setAdapter(adapter);
    }

}
