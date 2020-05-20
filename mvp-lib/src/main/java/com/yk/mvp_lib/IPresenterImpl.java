package com.yk.mvp_lib;


import android.os.Handler;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yk.net_lib.NetLoading;
import com.yk.net_lib.NetManager;
import com.yk.net_lib.Repo;
import com.yk.net_lib.intefaces.OnEmpty;
import com.yk.net_lib.intefaces.OnResult;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;


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
        lifecycleOwner = (LifecycleOwner) view;
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


    protected <T> void progressFlowableBody(Flowable<T> flowable, OnResult<T> onResult, OnEmpty onEmpty) {
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
    protected <T> void progressFlowableRepo(Flowable<Repo<T>> flowable, OnResult<T> onResult, OnEmpty onEmpty) {
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
    protected <T> void progressFlowableCommon(Flowable<T> flowable, OnResult<T> onResult, OnEmpty onEmpty) {
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
}
