package com.bakerj.rxretrohttp.util;

import com.bakerj.rxretrohttp.interfaces.IBaseApiAction;
import com.trello.rxlifecycle3.LifecycleTransformer;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by BakerJ on 2019/1/15
 */
public class RxSchedulerUtil {
    public static <T> ObservableTransformer<T, T> apiIoToMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> ioToMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> ioToMain(IBaseApiAction apiAction) {
        return upstream -> {
            upstream = upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            LifecycleTransformer<T> lifecycleTransformer = apiAction == null ? null : apiAction
                    .getLifecycleTransformer();
            if (lifecycleTransformer != null) {
                upstream = upstream.compose(lifecycleTransformer);
            }
            return upstream;
        };
    }
}
