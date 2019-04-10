package com.bakerj.demo.rxretrohttp;

import android.widget.Toast;

import com.bakerj.rxretrohttp.interfaces.IBaseApiAction;
import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity implements IBaseApiAction {
    @Override
    public void showLoading() {
        ToastUtils.showShort("loading");
    }

    @Override
    public void dismissLoading() {
        ToastUtils.showShort("loading finish");
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public <T> LifecycleTransformer<T> getLifecycleTransformer() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
