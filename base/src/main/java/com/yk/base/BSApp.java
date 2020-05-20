package com.yk.base;

import android.app.Application;

public class BSApp extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        InitUtil.initUtil(this);
    }

    public static Application getApp() {
        return application;
    }
}
