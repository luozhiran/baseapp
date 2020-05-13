package com.yk.base.mvp;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonParseException;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yk.base.CustomToast;
import com.yk.base.net.Repo;
import com.yk.base.net.RxScheduler;
import com.yk.base.utils.L;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;


public abstract class IPresenterImpl<V extends IView> {
    protected V mView;
    protected Handler mHandle;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public IPresenterImpl attachView(V view) {
        this.mView = view;
        return this;
    }

    public IPresenterImpl attachHandle(Handler handler) {
        mHandle = handler;
        return this;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mView = null;
    }

    /**
     * 释放一些不需要实力对象（一般都是静态实力对象）
     */
    public void release() {

    }

    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    public boolean isUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        if (mView instanceof LifecycleOwner) {
            return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                    .from((LifecycleOwner) mView, Lifecycle.Event.ON_DESTROY));
        } else {
            throw new IllegalArgumentException("生命周期感知");
        }
    }


    protected <T> void progressFlowableBody(Flowable<T> flowable, OnResult<T> onResult) {
        mView.showLoading();
        flowable.compose(RxScheduler.Flo_io_main())
                .as(bindAutoDispose())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T stringRepo) throws Exception {
                        onResult.result(stringRepo);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        mView.hideLoading();
                    }
                });
    }

    protected <T> void progressFlowableRepo(Flowable<Repo<T>> flowable, OnResult<T> onResult, OnEmpty onEmpty) {
        mView.showLoading();
        flowable.compose(RxScheduler.Flo_io_main())
                .as(bindAutoDispose())
                .subscribe(new Consumer<Repo<T>>() {
                    @Override
                    public void accept(Repo<T> repo) throws Exception {
                        if (repo.code == 0) {
                            onResult.result(repo.data);
                        } else {
                            onEmpty.empty(repo.code);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        mView.hideLoading();
                    }
                });
    }
}
