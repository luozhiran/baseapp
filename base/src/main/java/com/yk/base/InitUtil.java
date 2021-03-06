package com.yk.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.itg.util_lib.ToolConfig;
import com.yk.base.crash.UnCatchExceptionHandler;
import com.yk.base.utils.ApkUtils;
import com.yk.base.utils.SystemGalleryUtils;

import androidx.annotation.RequiresApi;

public class InitUtil {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void initUtil(Context context) {
        synchronized (InitUtil.class) {
            if (ToolConfig.app() == null) {
                UnCatchExceptionHandler.crash().attach(context);
                CustomToast.register(context);
                SystemGalleryUtils.Holder.holder.register(context);
                ApkUtils.register(context);
                ToolConfig.init((Application) context);
            }
        }
    }

    public static void openActivityLife(Application application, IActivityLifeCallback activityLifeCallback) {
        application.registerActivityLifecycleCallbacks(activityLifeCallback);
    }
}
