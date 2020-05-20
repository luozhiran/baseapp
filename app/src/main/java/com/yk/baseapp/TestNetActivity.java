package com.yk.baseapp;

import android.view.View;

import com.itg.lib_log.L;
import com.kongzue.dialog.v3.WaitDialog;
import com.yk.base.base.BaseActivity;
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
        mRootView.customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestPresent.getTomcat();
            }
        });
        mRootView.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestPresent.register();
            }
        });

    }

    @Override
    public void showLoading() {
        WaitDialog.show(mOwner, "loading...");
        L.d("开始请求");
    }

    @Override
    public void hideLoading() {
        L.d("请求结束");
        WaitDialog.dismiss();
    }

    @Override
    public void transContent(String s) {
        L.d(s);
    }

    @Override
    public void tomcat(String s) {
        L.d(s);
    }

    @Override
    public void register(String s) {
        L.d(s);
    }
}
