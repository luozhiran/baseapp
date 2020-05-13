package com.yk.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

/**
 * BaseActivity和BaseFragment都实现了Handler.Callback接口
 * WeakReference里面实际保存了BaseActivity或者BaseFragment
 */
public class WeakHandler extends Handler {
    private WeakReference<Handler.Callback> mActivity;

    public WeakHandler(Handler.Callback callback) {
        mActivity = new WeakReference<>(callback);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        if (mActivity != null && mActivity.get() != null) {
            mActivity.get().handleMessage(msg);
        }
    }
}
