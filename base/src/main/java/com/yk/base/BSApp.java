package com.yk.base;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BSApp extends Application {

    private static Application application;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
