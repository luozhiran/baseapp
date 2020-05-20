package com.yk.base;

import android.app.Application;
import android.content.Context;

import com.yk.base.crash.UnCatchExceptionHandler;
import com.yk.base.utils.ApkUtils;
import com.yk.base.utils.SystemGalleryUtils;

public class InitUtil {

    public static void initUtil(Context context) {
        UnCatchExceptionHandler.crash().attach(context);
        CustomToast.register(context);
        SystemGalleryUtils.Holder.holder.register(context);
        ApkUtils.register(context);
    }

    public static void openActivityLife(Application application, IActivityLifeCallback activityLifeCallback) {
        application.registerActivityLifecycleCallbacks(activityLifeCallback);
    }
}
