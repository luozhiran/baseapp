package com.yk.baseapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.itg.lib_log.L;
import com.itg.util_lib.FileUtils;
import com.yk.base.activity.ZoomPhotoActivity;
import com.yk.base.base.BaseActivity;

import com.yk.baseapp.databinding.ActivityMainBinding;
import com.yk.baseapp.test.DpiUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.internal.Util;


public class MainActivity extends BaseActivity {

    ActivityMainBinding mRootView;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mRootView = findByView();
        mRootView.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mOwner, TestNetActivity.class);
                startActivity(intent);
            }
        });
        mRootView.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mOwner, WebActivity.class);
                startActivity(intent);
            }
        });

        mRootView.saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num =Integer.parseInt(mRootView.edit.getText().toString());
                File file = new File("/sdcard/text-mamo.png");
                if (file.exists()){
                    L.e("图片存在");
                }

                Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/text-mamo.png");
                if (bitmap == null){
                    L.e("没有取到bitmap");
                }else {
                    DpiUtils.save(bitmap, new File("/sdcard/aa.png"), num);
                }
            }
        });

        mRootView.cityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mOwner,SelfSelectCityActivity.class);
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
