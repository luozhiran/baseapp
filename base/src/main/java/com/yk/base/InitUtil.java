package com.yk.base;

import android.app.Application;
import android.content.Context;

import com.yk.base.crash.UnCatchExceptionHandler;
import com.yk.base.utils.ApkUtils;
import com.yk.base.utils.DisplayUtils;
import com.yk.base.utils.FileUtils;
import com.yk.base.utils.SPUtils;
import com.yk.base.utils.StaticMemoryCache;
import com.yk.base.utils.SystemGalleryUtils;

public class InitUtil {

    public static void InitUtil(Context context) {
        UnCatchExceptionHandler.crash().attach(context);
        CustomToast.register(context);
        SystemGalleryUtils.Holder.holder.register(context);
        DisplayUtils.register(context);
        ApkUtils.register(context);
        FileUtils.register(context);
        SPUtils.register(context);
        StaticMemoryCache.cache().register(context);
    }

    public static void openActivityLife(Application application, IActivityLifeCallback activityLifeCallback) {
        application.registerActivityLifecycleCallbacks(activityLifeCallback);
    }
}
