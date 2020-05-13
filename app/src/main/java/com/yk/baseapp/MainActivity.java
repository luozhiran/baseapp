package com.yk.baseapp;

import android.view.View;

import com.yk.base.base.BaseActivity;
import com.yk.base.net.Repo;
import com.yk.base.utils.L;
import com.yk.baseapp.databinding.ActivityMainBinding;
import com.yk.baseapp.mvp.BaiduView;
import com.yk.baseapp.mvp.TestPresent;

public class MainActivity extends BaseActivity implements BaiduView {

    ActivityMainBinding mRootView;
    private TestPresent mTestPresent;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mRootView = findByView();
        mTestPresent = new TestPresent();
        mTestPresent.attachView(this);
        mRootView.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestPresent.getBaidu();
            }
        });

    }

    @Override
    public void showLoading() {
        L.d("开始请求");
    }

    @Override
    public void hideLoading() {
        L.d("请求结束");
    }

    @Override
    public void transContent(String s) {
        L.d(s);
    }
}
