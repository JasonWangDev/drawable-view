package com.github.jasonwangdev.drawableview.demo.baseadapter_realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Jason on 2017/7/8.
 */

public class PersonRealm extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
