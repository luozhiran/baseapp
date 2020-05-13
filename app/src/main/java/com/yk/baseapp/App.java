package com.yk.baseapp;

import com.yk.base.BSApp;
import com.yk.base.net.ApiRetrofit;

public class App extends BSApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiRetrofit.BASE_URL="https://www.baidu.com";
    }
}
