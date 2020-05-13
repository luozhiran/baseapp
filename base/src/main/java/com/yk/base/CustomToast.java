package com.yk.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {

    private static Context mContext;
    private static Toast mToast;

    private static long time = 0;


    @SuppressLint("ShowToast")
    public static void register(Context context) {
        mContext = context;
        mToast = Toast.makeText(mContext, "init", Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg) {
        if (System.currentTimeMillis() - time > 800&& !TextUtils.isEmpty(msg)) {
            mToast.setText(msg);
            mToast.show();
            time = System.currentTimeMillis();
        } else {
            mToast.cancel();
        }

//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

}
