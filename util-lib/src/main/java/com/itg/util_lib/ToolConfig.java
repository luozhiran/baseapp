package com.itg.util_lib;

import android.app.Application;

public class ToolConfig {

    private static Application application;

    public static void init(Application app) {
        application = app;
    }
    public static Application app(){
        return application;
    }
}
