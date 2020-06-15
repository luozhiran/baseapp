package com.yk.base;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

public abstract class IActivityLifeCallback implements Application.ActivityLifecycleCallbacks {

    protected boolean mShowCurrentActivityName = false;
    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (mShowCurrentActivityName) {
            CustomToast.showToast(activity.getClass().getSimpleName());
        }
    }
}
