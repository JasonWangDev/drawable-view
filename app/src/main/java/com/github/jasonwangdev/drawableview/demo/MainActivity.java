package com.github.jasonwangdev.drawableview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.github.jasonwangdev.drawableview.DrawableInstantAutoCompleteTextView;
import com.github.jasonwangdev.drawableview.demo.baseadapter_realm.PersonAdapterRealm;
import com.github.jasonwangdev.drawableview.demo.baseadapter_realm.PersonRealm;
import com.github.jasonwangdev.drawableview.demo.baseadpter.Person;
import com.github.jasonwangdev.drawableview.demo.baseadpter.PersonAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        DrawableInstantAutoCompleteTextView tv = (DrawableInstantAutoCompleteTextView) findViewById(R.id.tv);
        tv.setOnItemClickListener(this);
//        tv.setAdapter(getAdapter());
//        tv.setAdapter(getBaseAdapter());
        tv.setAdapter(getBaseAdapterRealm());
    }

    @Override
    protected void onDestroy() {
        realm.close();

        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG", "Parent ID: " + parent.getId());
        Log.d("TAG", "View ID: " + view.getId());
        Log.d("TAG", "Res ID: " + R.id.tv);
    }


    private ArrayAdapter<String> getAdapter() {
        List<String> persons = new ArrayList<>();
        for (int i = 0 ; i < 20000 ; i++)
            persons.add("Name - " + i);

        return new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, persons);
    }

    private PersonAdapter getBaseAdapter() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0 ; i < 20000 ; i++)
            persons.add(new Person(i, "Name - " + i));

        PersonAdapter adapter = new PersonAdapter(persons);

        return adapter;
    }

    private PersonAdapterRealm getBaseAdapterRealm() {
        Realm delete = Realm.getDefaultInstance();
        delete.beginTransaction();
        delete.where(PersonRealm.class).findAll().deleteAllFromRealm();
        delete.commitTransaction();
        delete.close();

        Realm insert = Realm.getDefaultInstance();
        insert.beginTransaction();
        for (int i = 0 ; i < 20000 ; i++)
        {
            PersonRealm person = insert.createObject(PersonRealm.class, i);
            person.setName("Name - " + i);
        }
        insert.commitTransaction();
        insert.close();

        RealmResults results = realm.where(PersonRealm.class).findAll();
        PersonAdapterRealm adapter = new PersonAdapterRealm(results);

        return adapter;
    }

}
