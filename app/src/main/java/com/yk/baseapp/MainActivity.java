package com.yk.baseapp;

import android.content.Intent;
import android.view.View;

import com.yk.base.base.BaseActivity;

import com.yk.baseapp.databinding.ActivityMainBinding;


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


    }


}
