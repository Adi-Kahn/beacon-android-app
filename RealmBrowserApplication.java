package com.example.adi.rejsekortv1;

import android.app.Application;

import io.realm.Realm;

public class RealmBrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.getDefaultInstance();
    }
}
