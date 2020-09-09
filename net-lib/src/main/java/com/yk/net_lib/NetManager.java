package com.yk.net_lib;

import android.os.Looper;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yk.net_lib.intefaces.OnError;
import com.yk.net_lib.intefaces.OnResult;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;

public class NetManager {
    public static final int ERROR_ = -1111;
    public static final int EMPTY_ = -1112;
    private static final String msg = "请求已发送给服务器，服务器没有返回数据";


    public static boolean isUiThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static <T> void progressFlowableBody(Flowable<T> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T stringRepo) throws Exception {
                        onResult.result(stringRepo);
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onError.error(ERROR_, throwable.getMessage());
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
     * @param onError
     * @param <T>
     */
    public static <T> void progressFlowableRepo(Flowable<Repo<T>> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<Repo<T>>() {
                    @Override
                    public void accept(Repo<T> repo) throws Exception {
                        if (repo.code == 0) {
                            onResult.result(repo.data);
                        } else {
                            boolean consumeCode = false;
                            for (int code : NetApi.getInstance().getSuccessCode()) {//NetApi.getInstance().getSuccessCode()返回 特殊成功code码，
                                if (repo.code == code) {
                                    consumeCode = true;
                                    onResult.result(repo.data);
                                    break;
                                }
                            }
                            if (!consumeCode) {
                                //对一些特殊的错误码进行拦截，如果拦截器返回false，表示拦截器不处理，回调error()返回，否则在一个地方做全局处理，不执行error
                                if (NetApi.getInstance().getResponseInterceptor() != null && NetApi.getInstance().getResponseInterceptor().interceptor(repo.code, repo.msg)) {

                                } else {
                                    NetExpection.show(repo.msg);
                                    onError.error(repo.code, repo.msg);
                                }
                            }
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
                        onError.error(ERROR_, throwable.getMessage());
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
     * @param onError
     * @param <T>
     */
    public static <T> void progressFlowableCommon(Flowable<T> flowable, LifecycleOwner lifecycleOwner, OnResult<T> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        flowable.compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T repo) throws Exception {
                        onResult.result(repo);
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onError.error(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                });
    }

    public static <T1, T2, R> void progressMultiCommon(Flowable<T1> flowable1, Flowable<T2> flowable2, LifecycleOwner lifecycleOwner, BiFunction<? super T1, ? super T2, ? extends R> zipper, OnResult<R> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        Flowable.zip(flowable1, flowable2, zipper).compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<R>() {
                    @Override
                    public void accept(R repo) throws Exception {
                        onResult.result(repo);
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onError.error(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                });
    }


    public static <T1, T2, T3, R> void progressMultiCommon3(Flowable<T1> flowable1, Flowable<T2> flowable2, Flowable<T3> flowable3, LifecycleOwner lifecycleOwner, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper, OnResult<R> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        Flowable.zip(flowable1, flowable2, flowable3, zipper).compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<R>() {
                    @Override
                    public void accept(R repo) throws Exception {
                        onResult.result(repo);
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onError.error(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                });
    }


    public static <T1, T2, T3, T4, R> void progressMultiCommon4(Flowable<T1> flowable1, Flowable<T2> flowable2, Flowable<T3> flowable3, Flowable<T4> flowable4, LifecycleOwner lifecycleOwner, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper, OnResult<R> onResult, OnError onError, NetLoading netLoading) {
        if (netLoading != null) {
            netLoading.start();
        }
        Flowable.zip(flowable1, flowable2, flowable3, flowable4, zipper).compose(RxScheduler.Flo_io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)))
                .subscribe(new Consumer<R>() {
                    @Override
                    public void accept(R repo) throws Exception {
                        onResult.result(repo);
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //网络异常提示
                        NetExpection.NetExceptionTrip(throwable);
                        onError.error(ERROR_, throwable.getMessage());
                        if (netLoading != null) {
                            netLoading.end();
                        }
                    }
                });
    }

}
