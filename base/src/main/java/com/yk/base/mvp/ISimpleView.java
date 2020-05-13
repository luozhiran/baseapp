package com.yk.base.mvp;

public interface ISimpleView<T> extends IView {

    void onSuccess(T t);

    void quit();
}
