package com.yk.base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * 发送本地广播
 */
public class LBMUtils {
    public static final String LOGIN_ACTIVITY = "go_to_loginActivity";
    private LBMUtils() {
    }

    @NonNull
    public static LocalBroadcastManager getBroadcastManager(@NonNull Context ctx) {
        return LocalBroadcastManager.getInstance(ctx);
    }

    @Nullable
    public static IntentFilter getIntentFilter(@NonNull String... actions) {
        IntentFilter filter = null;
        if (actions.length > 0) {
            filter = new IntentFilter();
            for (String action : actions) {
                filter.addAction(action);
            }
        }
        return filter;
    }

    /**
     * @Desc 通过Action注册广播接收者
     * @Param [ctx, receiver, actions]
     */
    public static void registerReceiver(@NonNull Context ctx, @NonNull BroadcastReceiver receiver, @NonNull String... actions) {
        IntentFilter filter = getIntentFilter(actions);
        if (filter != null) {
            registerReceiver(ctx, receiver, filter);
        }
    }

    /**
     * @Desc 通过IntentFilter注册广播接收者
     * @Param [ctx, receiver, filter]
     */
    public static void registerReceiver(@NonNull Context ctx, @NonNull BroadcastReceiver receiver, @NonNull IntentFilter filter) {
        getBroadcastManager(ctx).registerReceiver(receiver, filter);
    }

    /**
     * @Desc 注销广播接收者
     * @Param [ctx, receiver]
     */
    public static void unRegisterReceiver(@NonNull Context ctx, BroadcastReceiver receiver) {
        getBroadcastManager(ctx).unregisterReceiver(receiver);
    }

    /**
     * @Desc 通过Action发送广播
     * @Param [ctx, action]
     */
    public static void sendBroadcast(@NonNull Context ctx, @NonNull String action) {
        sendBroadcast(ctx, new Intent(action));
    }

    /**
     * @Desc 通过intent发送广播
     * @Param [ctx, intent]
     */
    public static void sendBroadcast(@NonNull Context ctx, @NonNull Intent intent) {
        getBroadcastManager(ctx).sendBroadcast(intent);
    }

    /**
     * @Desc 通过Action同步发送广播
     * @Param [ctx, action]
     */
    public static void sendBroadcastSync(@NonNull Context ctx, @NonNull String action) {
        sendBroadcastSync(ctx, new Intent(action));
    }

    /**
     * @Desc 通过Intent同步发送广播
     * @Param [ctx, intent]
     */
    public static void sendBroadcastSync(@NonNull Context ctx, @NonNull Intent intent) {
        getBroadcastManager(ctx).sendBroadcastSync(intent);
    }
}
