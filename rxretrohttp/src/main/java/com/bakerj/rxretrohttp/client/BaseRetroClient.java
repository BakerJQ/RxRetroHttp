package com.bakerj.rxretrohttp.client;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.converter.RetroJsonConverterFactory;
import com.bakerj.rxretrohttp.exception.ApiException;
import com.bakerj.rxretrohttp.exception.IExceptionHandler;
import com.bakerj.rxretrohttp.func.ExceptionHandleFunc;
import com.bakerj.rxretrohttp.func.RetryExceptionFunc;
import com.bakerj.rxretrohttp.interceptors.HttpLoggingInterceptor;
import com.bakerj.rxretrohttp.interceptors.MockDataInterceptor;
import com.bakerj.rxretrohttp.util.RxSchedulerUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableTransformer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 抽象请求client
 * Abstract client for http request
 * Created by BakerJ on 2019/1/15
 */
public abstract class BaseRetroClient<Client extends BaseRetroClient> {
    private final List<Interceptor> networkInterceptors = new ArrayList<>();
    private final List<Interceptor> interceptors = new ArrayList<>();
    private String mBaseUrl;//api base url 请求地址
    private int mRetryCount;//retry count 重试次数
    private int mRetryDelay;//retry delay 重试延时
    private boolean mIsDebug;//is debug mode 是否为debug
    private boolean mMockEnable;//is mock enable 是否支持mock
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private IExceptionHandler mExceptionHandler;
    private String mDefaultErrMsg;

    /**
     * 初始化
     * initialize
     */
    public BaseRetroClient() {
        mBaseUrl = RxRetroHttp.getBaseUrl();
        mRetryCount = RxRetroHttp.getRetryCount();
        mRetryDelay = RxRetroHttp.getRetryDelay();
        mIsDebug = RxRetroHttp.isDebug();
        mMockEnable = RxRetroHttp.isMockEnable();
        mDefaultErrMsg = RxRetroHttp.getDefaultErrMsg();
        mExceptionHandler = RxRetroHttp.getExceptionHandler();
        if (mExceptionHandler == null) {
            mExceptionHandler = throwable -> ApiException.handleException(throwable,
                    mDefaultErrMsg);
        }
        generateOkClient();
        generateRetrofit();
    }

    /**
     * 初始化OkHttp
     * init okhttp
     */
    private void generateOkClient() {
        mOkHttpClientBuilder = RxRetroHttp.getOkHttpClient().newBuilder();
        setOkHttpClientBuilder(mOkHttpClientBuilder);
    }

    protected void setOkHttpClientBuilder(OkHttpClient.Builder okHttpClientBuilder) {
        for (Interceptor interceptor : interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }
        if (networkInterceptors.size() > 0) {
            for (Interceptor interceptor : networkInterceptors) {
                okHttpClientBuilder.addNetworkInterceptor(interceptor);
            }
        }
        if (mIsDebug) {
            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
            okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel
                    (HttpLoggingInterceptor.Level.BODY));
        }
        if (mMockEnable) {
            okHttpClientBuilder.addInterceptor(new MockDataInterceptor());
        }
    }

    /**
     * 初始化Retrofit
     * init Retrofit
     */
    private void generateRetrofit() {
        mRetrofitBuilder = RxRetroHttp.getRetrofit().newBuilder()
                .baseUrl(mBaseUrl);
        setRetrofitBuilder(mRetrofitBuilder);
    }

    protected void setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
        if (RxRetroHttp.getApiResultClass() == null) {
            retrofitBuilder.addConverterFactory(RetroJsonConverterFactory.create(null))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            return;
        }
        retrofitBuilder.addConverterFactory(RetroJsonConverterFactory.create(RxRetroHttp
                .getApiResultClass())).addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 构建client
     * build client
     */
    public Client build() {
        mOkHttpClient = mOkHttpClientBuilder.build();
        mRetrofitBuilder.client(mOkHttpClient);
        mRetrofit = mRetrofitBuilder.build();
        //noinspection unchecked
        return (Client) this;
    }

    /**
     * 构建retrofit请求接口
     * create retrofit interface
     */
    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    /**
     * 设置重试、线程转换
     * set retry and thread switch
     */
    public <T> ObservableTransformer<T, T> composeApi() {
        return observable -> observable
                .compose(RxSchedulerUtil.apiIoToMain())
                .onErrorResumeNext(new ExceptionHandleFunc<>(mExceptionHandler))
                .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay));
    }
}
