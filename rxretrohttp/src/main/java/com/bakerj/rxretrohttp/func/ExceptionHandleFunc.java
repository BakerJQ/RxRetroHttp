package com.bakerj.rxretrohttp.func;

import com.bakerj.rxretrohttp.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by BakerJ on 2019/1/15
 */
public class ExceptionHandleFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) {
        //cast to ApiException 转换为ApiException
        return Observable.error(ApiException.handleException(throwable));
    }
}
