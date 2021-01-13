package com.yk.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {

    private static Context mContext;
    private static Toast mToast;

    private static long time = 0;
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static boolean has;
    private final static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            has = false;
        }
    };

    @SuppressLint("ShowToast")
    public static void register(Context context) {
        mContext = context;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);

    }

    public static void showToast(String msg) {
        mToast.setText(msg);
        if (!has) {
            has = true;
            handler.postDelayed(r, 4000);
            mToast.show();
        }
    }

}
