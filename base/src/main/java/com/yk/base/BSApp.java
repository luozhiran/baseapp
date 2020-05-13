package com.yk.base;

import android.app.Application;

public class BSApp extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        InitUtil.InitUtil(this);
    }

    public static Application getApp() {
        return application;
    }
}
