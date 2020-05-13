package com.yk.baseapp;

import android.content.Intent;
import android.view.View;

import com.yk.base.base.BaseActivity;
import com.yk.base.net.Repo;
import com.yk.base.utils.L;
import com.yk.baseapp.databinding.ActivityMainBinding;
import com.yk.baseapp.mvp.BaiduView;
import com.yk.baseapp.mvp.TestPresent;

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
                Intent intent = new Intent(mOwner,TestNetActivity.class);
                startActivity(intent);
            }
        });

    }


}
