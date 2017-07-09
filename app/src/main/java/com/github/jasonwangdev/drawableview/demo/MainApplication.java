package com.github.jasonwangdev.drawableview.demo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Jason on 2017/7/8.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

}
