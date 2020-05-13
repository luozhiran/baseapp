package com.yk.baseapp;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.yk.base.base.BaseActivity;
import com.yk.baseapp.databinding.ActivitySplishBinding;

import androidx.core.app.ActivityCompat;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class SplashActivity extends BaseActivity {

    private static final int RC_WRITE_STORAGE = 1;

    private ActivitySplishBinding mRootView;
    private String[] mPrem = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public int layoutId() {
        return R.layout.activity_splish;
    }

    @Override
    public void init() {
        mRootView = findByView();
        requestPermissions();
    }


    @AfterPermissionGranted(RC_WRITE_STORAGE)
    private void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, createPermissions())) {
            hasPermissions();
        } else {
            PermissionRequest request = new PermissionRequest.Builder(this, RC_WRITE_STORAGE, createPermissions())
                    .setRationale("需要到你的应用信息里开启你手机的相关权限")
                    .setPositiveButtonText("确定")
                    .setNegativeButtonText("取消").build();
            EasyPermissions.requestPermissions(request);
        }
    }

    @Override
    protected void hasPermissions() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * 获取要申请的权限
     *
     * @return
     */
    @Override
    protected String[] createPermissions() {
        return mPrem;
    }


}
