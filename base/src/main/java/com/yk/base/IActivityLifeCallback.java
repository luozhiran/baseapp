package com.yk.base;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

public abstract class IActivityLifeCallback implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (BuildConfig.DEBUG) {
            CustomToast.showToast(activity.getClass().getSimpleName());
        }
    }
}
