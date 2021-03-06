package com.yk.mvp_lib;


import android.os.Handler;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yk.net_lib.NetApi;
import com.yk.net_lib.NetLoading;
import com.yk.net_lib.NetManager;
import com.yk.net_lib.Repo;
import com.yk.net_lib.intefaces.OnError;
import com.yk.net_lib.intefaces.OnMap;
import com.yk.net_lib.intefaces.OnMap3;
import com.yk.net_lib.intefaces.OnMap4;
import com.yk.net_lib.intefaces.OnResult;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;


public abstract class IPresenterImpl<V extends IView> {
    protected V mView;
    protected Handler mHandle;
    private LifecycleOwner lifecycleOwner;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param view view
     */
    public IPresenterImpl attachView(V view) {
        this.mView = view;
        if (view instanceof LifecycleOwner) {
            lifecycleOwner = (LifecycleOwner) view;
        }
        return this;
    }

    public IPresenterImpl attachView(V view, LifecycleOwner owner) {
        this.mView = view;
        lifecycleOwner = owner;
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
        return NetManager.isUiThread();
    }

    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        if (mView instanceof LifecycleOwner) {
            return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                    .from((LifecycleOwner) mView, Lifecycle.Event.ON_DESTROY));
        } else {
            throw new IllegalArgumentException("生命周期感知");
        }
    }


    protected <T> void progressFlowableBody(Flowable<T> flowable, OnResult<T> onResult, OnError onEmpty) {
        NetManager.progressFlowableBody(flowable, lifecycleOwner, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });
    }

    /**
     * 处理Repo类请求
     *
     * @param flowable
     * @param onResult
     * @param onEmpty
     * @param <T>
     */
    protected <T> void progressFlowableRepo(Flowable<Repo<T>> flowable, OnResult<T> onResult, OnError onEmpty) {
        NetManager.progressFlowableRepo(flowable, lifecycleOwner, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });
    }


    /**
     * 处理普通类请求
     *
     * @param flowable
     * @param onResult
     * @param onEmpty
     * @param <T>
     */
    protected <T> void progressFlowableCommon(Flowable<T> flowable, OnResult<T> onResult, OnError onEmpty) {
        NetManager.progressFlowableCommon(flowable, lifecycleOwner, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });
    }


    protected <T1, T2, R> void progressMultiFlowableCommon(Flowable<T1> flowable1, Flowable<T2> flowable2, OnMap<T1, T2, R> map, OnResult<R> onResult, OnError onEmpty) {
        NetManager.progressMultiCommon(flowable1, flowable2, lifecycleOwner, new BiFunction<T1, T2, R>() {
            @Override
            public R apply(T1 t1, T2 t2) throws Exception {
                return map.map(t1, t2);
            }
        }, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });

    }


    protected <T1, T2, T3, R> void progressMultiFlowableCommon3(Flowable<T1> flowable1, Flowable<T2> flowable2, Flowable<T3> flowable3, OnMap3<T1, T2, T3, R> map, OnResult<R> onResult, OnError onEmpty) {
        NetManager.progressMultiCommon3(flowable1, flowable2, flowable3, lifecycleOwner, new Function3<T1, T2, T3, R>() {

            @Override
            public R apply(T1 t1, T2 t2, T3 t3) throws Exception {
                return map.map(t1, t2, t3);
            }
        }, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });

    }

    protected <T1, T2, T3, T4, R> void progressMultiFlowableCommon4(Flowable<T1> flowable1, Flowable<T2> flowable2, Flowable<T3> flowable3, Flowable<T4> flowable4, OnMap4<T1, T2, T3, T4, R> map, OnResult<R> onResult, OnError onEmpty) {
        NetManager.progressMultiCommon4(flowable1, flowable2, flowable3, flowable4, lifecycleOwner, new Function4<T1, T2, T3, T4, R>() {

            @Override
            public R apply(T1 t1, T2 t2, T3 t3, T4 t4) throws Exception {
                return map.map(t1, t2, t3, t4);
            }
        }, onResult, onEmpty, new NetLoading() {
            @Override
            public void start() {
                mView.showLoading();
            }

            @Override
            public void end() {
                mView.hideLoading();
            }
        });

    }

}
