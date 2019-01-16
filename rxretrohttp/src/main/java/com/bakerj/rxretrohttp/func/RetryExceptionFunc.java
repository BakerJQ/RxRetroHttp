package com.bakerj.rxretrohttp.func;

import com.bakerj.rxretrohttp.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by BakerJ on 2019/1/15
 */
public class RetryExceptionFunc implements Function<Observable<? extends Throwable>,
        Observable<?>> {
    private int count;//current retry count 当前retry次数
    private long delay;//retry delay 重试延时

    public RetryExceptionFunc(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, count + 1)
                , (BiFunction<Throwable, Integer, Wrapper>) Wrapper::new)
                .flatMap((Function<Wrapper, Observable<?>>) wrapper -> {
                    String errCode = "";
                    if (wrapper.throwable instanceof ApiException) {
                        ApiException exception = (ApiException) wrapper.throwable;
                        errCode = exception.getCode();
                    }
                    if ((wrapper.throwable instanceof ConnectException
                            || wrapper.throwable instanceof SocketTimeoutException
                            || ApiException.NETWORK_ERROR.equals(errCode)
                            || ApiException.TIMEOUT_ERROR.equals(errCode)
                            || wrapper.throwable instanceof SocketTimeoutException
                            || wrapper.throwable instanceof TimeoutException)
                            && wrapper.index < count + 1) {
                        return Observable.timer(delay, TimeUnit.MILLISECONDS);

                    }
                    return Observable.error(wrapper.throwable);
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
