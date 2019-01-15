package com.bakerj.rxretrohttp.interfaces;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IBaseApiAction {
    void showLoading();

    void dismissLoading();

    void showToast(String msg);

    <T> LifecycleTransformer<T> getLifecycleTransformer();
}
