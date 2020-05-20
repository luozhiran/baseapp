package com.yk.base.base;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.itg.lib_log.L;
import com.itg.util_lib.StatusBarUtil;
import com.yk.base.CustomToast;
import com.yk.base.WeakHandler;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity implements Handler.Callback, EasyPermissions.PermissionCallbacks {

    protected WeakHandler mHandler = new WeakHandler(this);
    private ViewDataBinding mTemp;
    protected AppCompatActivity mOwner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOwner = this;
        statusBarTransparent();
        hideActionbar();
        mTemp = DataBindingUtil.setContentView(this, layoutId());
        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public abstract int layoutId();

    public abstract void init();


    public <T> T findByView() {
        return (T) mTemp;
    }

    protected void statusBarTransparent() {
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#ffffff"));
    }

    protected void hideActionbar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        androidx.appcompat.app.ActionBar compatActionBar = getSupportActionBar();
        if (compatActionBar != null) {
            compatActionBar.hide();
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return true;
    }


    /**
     * 允许权限成功后触发
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        L.e("onPermissionsGranted:" + requestCode + ":" + perms.size());

    }

    /**
     * 禁止权限后触发
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        L.e("onPermissionsDenied:" + requestCode + ":" + perms.size());
        //如果用户点击永远禁止，这个时候就需要跳到系统设置页面去手动打开了
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("提醒")
                    .setRationale("此app需要这些权限才能正常使用")
                    .build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (createPermissions() != null) {
                if (EasyPermissions.hasPermissions(this, createPermissions())) {
                    hasPermissions();
                }
            }
        }
    }

    /**
     * 获得要开启的权限
     *
     * @return
     */
    protected String[] createPermissions() {
        return null;
    }

    /**
     * 通过系统手动开启权限
     */
    protected void hasPermissions() {
        CustomToast.showToast("权限获取成功");
    }


}
