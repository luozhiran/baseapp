package com.yk.base;

import android.app.Activity;
import android.os.Bundle;

import com.yk.base.IActivityLifeCallback;

import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DefaultActivityLifeCallback extends IActivityLifeCallback {


    public DefaultActivityLifeCallback(boolean showCurrentPageName) {
       mShowCurrentActivityName = showCurrentPageName;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        ActivityStackManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        super.onActivityResumed(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        ActivityStackManager.getInstance().removeActivity(activity);

    }

}
