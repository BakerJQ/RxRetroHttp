package com.bakerj.rxretrohttp.exception;

/**
 * Created by BakerJ on 2019/1/16
 */
public interface IExceptionHandler {
    ApiException handleException(Throwable throwable);
}
