package com.yk.base.mvp;

import com.uber.autodispose.AutoDisposeConverter;
import com.yk.base.net.Repo;

import androidx.lifecycle.LifecycleOwner;

public interface IView {
    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();



}
