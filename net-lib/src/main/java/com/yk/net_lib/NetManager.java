package com.yk.net_lib;

import android.os.Looper;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yk.net_lib.intefaces.OnEmpty;
import com.yk.net_lib.intefaces.OnResult;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class NetManager {
    public static final int ERROR_ = -1111;
    public static final int EMPTY_ = -1112;
    private static final String msg = "服务器没有数据返回";


    public static boolean isUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static <T> void progressFlowableBody(Flowable<T> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnEmpty onEmpty, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T stringRepo) throws Exception {
                        if (stringRepo != null) {
                            onResult.result(stringRepo);
                        } else {
                            onEmpty.empty(EMPTY_, msg);
                        }
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onEmpty.empty(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
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
    public static <T> void progressFlowableRepo(Flowable<Repo<T>> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnEmpty onEmpty, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<Repo<T>>() {
                    @Override
                    public void accept(Repo<T> repo) throws Exception {
                        if (repo != null) {
                            if (repo.code == 0) {
                                if (repo.data != null) {
                                    onResult.result(repo.data);
                                } else {
                                    onEmpty.empty(repo.code, msg);
                                }
                            } else {
                                onEmpty.empty(repo.code, repo.msg);
                            }
                        } else {
                            onEmpty.empty(EMPTY_, msg);
                        }
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onEmpty.empty(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
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
    public static <T> void progressFlowableCommon(Flowable<T> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnEmpty onEmpty, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T repo) throws Exception {
                        if (repo != null) {
                            onResult.result(repo);
                        } else {
                            onEmpty.empty(EMPTY_, msg);
                        }
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onEmpty.empty(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                });
    }

}
