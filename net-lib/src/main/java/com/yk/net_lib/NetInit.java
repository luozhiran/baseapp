package com.yk.net_lib;

import android.app.Application;

public class NetInit {
    private static Application app;
    private static String baseUrl="https://www.baidu.com/";

    public static void init(Application application, String url) {
        app = application;
        baseUrl = url;
    }

    public static String baseUrl(){
        return baseUrl;
    }
    public static Application app() {
        return app;
    }
}
