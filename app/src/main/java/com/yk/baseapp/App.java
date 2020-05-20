package com.yk.baseapp;

import com.yk.base.BSApp;
import com.yk.net_lib.NetInit;

public class App extends BSApp {
    @Override
    public void onCreate() {
        super.onCreate();
        NetInit.init(this, "https://www.baidu.com/");
    }
}
