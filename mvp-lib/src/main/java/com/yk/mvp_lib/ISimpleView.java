package com.yk.mvp_lib;

public interface ISimpleView<T> extends IView {

    void onSuccess(T t);

    void quit();
}
