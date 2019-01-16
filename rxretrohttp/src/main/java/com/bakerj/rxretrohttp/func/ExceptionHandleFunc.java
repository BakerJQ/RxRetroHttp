package com.bakerj.rxretrohttp.func;

import com.bakerj.rxretrohttp.exception.ApiException;
import com.bakerj.rxretrohttp.exception.IExceptionHandler;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by BakerJ on 2019/1/15
 */
public class ExceptionHandleFunc<T> implements Function<Throwable, Observable<T>> {
    private IExceptionHandler mExceptionHandler;

    public ExceptionHandleFunc(IExceptionHandler mExceptionHandler) {
        this.mExceptionHandler = mExceptionHandler;
    }

    @Override
    public Observable<T> apply(Throwable throwable) {
        //cast to ApiException 转换为ApiException
        return Observable.error(mExceptionHandler.handleException(throwable));
    }
}
