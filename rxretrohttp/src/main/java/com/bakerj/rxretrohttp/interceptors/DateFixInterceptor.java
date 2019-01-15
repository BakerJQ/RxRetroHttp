package com.bakerj.rxretrohttp.interceptors;

import com.bakerj.rxretrohttp.util.RetroDateUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by BakerJ on 2019/1/15
 * fix date with server
 * 修正服务器时间
 */
public class DateFixInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String date = response.header("Date");
        //根据服务器时间修正本地时间
        //fix date by saving the Date parameter in header
        if (date != null) {
            RetroDateUtil.setDateFix(date);
        }
        return response;
    }
}
