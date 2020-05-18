package com.yk.baseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.yk.base.base.BaseActivity;
import com.yk.baseapp.databinding.ActivityWebBinding;

public class WebActivity extends BaseActivity {

    private ActivityWebBinding mRootView;
    private AgentWeb mAgentWeb;
    private String url= "http://192.168.40.163:8099/web/index.jsp";

    @Override
    public int layoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        mRootView = findByView();
        mAgentWeb = AgentWeb.with(mOwner)//
                .setAgentWebParent(mRootView.container, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go(url); //WebView载入该url地址的页面并显示。
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
