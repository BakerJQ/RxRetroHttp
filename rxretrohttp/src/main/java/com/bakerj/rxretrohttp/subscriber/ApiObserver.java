package com.bakerj.rxretrohttp.subscriber;

import com.bakerj.rxretrohttp.exception.ApiException;
import com.bakerj.rxretrohttp.interfaces.IBaseApiAction;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by BakerJ on 2019/1/15
 */
public abstract class ApiObserver<T> extends DisposableObserver<T> {
    protected ApiActionDelegate<T> mApiActionDelegate;

    public ApiObserver() {
        this(null);
    }

    public ApiObserver(IBaseApiAction apiAction) {
        this(apiAction, apiAction != null, apiAction != null);
    }

    public ApiObserver(IBaseApiAction apiAction, boolean isShowLoading, boolean isShowMsg) {
        mApiActionDelegate = new ApiActionDelegate<>(apiAction, isShowLoading, isShowMsg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiActionDelegate.onStart();
    }

    @Override
    public void onNext(T data) {
        success(data);
        mApiActionDelegate.onNext(data);
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ApiException && ApiException.DATA_NULL.equals(((ApiException) t).getCode
                ())) {
            dataNull();
            return;
        }
        mApiActionDelegate.onError(t);
        error(t);
    }

    @Override
    public void onComplete() {
        mApiActionDelegate.onComplete();
        complete();
    }

    protected abstract void success(T data);

    protected void dataNull() {

    }

    protected void error(Throwable t) {

    }

    protected void complete() {

    }
}
