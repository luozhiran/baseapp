package com.yk.baseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yk.base.base.BaseActivity;
import com.yk.base.utils.L;
import com.yk.baseapp.databinding.ActivityTestNetBinding;
import com.yk.baseapp.mvp.BaiduView;
import com.yk.baseapp.mvp.TestPresent;

public class TestNetActivity extends BaseActivity implements BaiduView {

    private ActivityTestNetBinding mRootView;
    private TestPresent mTestPresent;

    @Override
    public int layoutId() {
        return R.layout.activity_test_net;
    }

    @Override
    public void init() {
        mRootView = findByView();
        mTestPresent = new TestPresent();
        mTestPresent.attachView(this);
        mRootView.btn.setOnClickListener(new View.OnClickListener() {
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
