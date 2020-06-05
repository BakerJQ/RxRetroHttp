package com.bakerj.rxretrohttp.interceptors;

import android.text.TextUtils;

import androidx.collection.LruCache;

import com.bakerj.rxretrohttp.RxRetroHttp;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockDataInterceptor implements Interceptor {
    private LruCache<String, String> mockDataCache = new LruCache<>(20);

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers();
        String mockEnableStr = headers.get(RxRetroHttp.MOCK_ENABLE_KEY);
        boolean mockEnable =
                TextUtils.isEmpty(mockEnableStr) || Boolean.parseBoolean(mockEnableStr);
        if (!RxRetroHttp.isMockEnable() || !mockEnable) {
            return chain.proceed(request);
        }
        threadSleep(headers.get(RxRetroHttp.MOCK_DELAY_KEY));
        String mockStr = mockDataCache.get(chain.request().url().url().getPath());
        if (!TextUtils.isEmpty(mockStr)) {
            return getMockResponse(chain, mockStr);
        }
        String mockData = headers.get(RxRetroHttp.getMockDataKey());
        String mockFilePath = headers.get(RxRetroHttp.getMockFilePathKey());
        if (!TextUtils.isEmpty(mockData)) {
            return getMockResponse(chain, mockData);
        } else if (!TextUtils.isEmpty(mockFilePath)) {
            return getMockResponse(chain, getResponseStringFromAssets(mockFilePath));
        }
        return chain.proceed(request);
    }

    private Response getMockResponse(Chain chain, String responseStr) throws IOException {
        if (TextUtils.isEmpty(responseStr)) {
            return chain.proceed(chain.request());
        }
        mockDataCache.put(chain.request().url().url().getPath(), responseStr);
        return new Response.Builder()
                .code(200)
                .message(responseStr)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"),
                        responseStr.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }

    private String getResponseStringFromAssets(String path) {
        BufferedReader bufferedReader = null;
        try {
            StringBuilder buf = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    RxRetroHttp.getContext().getAssets().open(path), "UTF-8"));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buf.append(str);
            }
            return buf.toString();
        } catch (Throwable t) {
            return "";
        } finally {
            try {
                bufferedReader.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void threadSleep(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                Thread.sleep(Integer.parseInt(str));
            }
        } catch (Throwable e) {
            //ignore
        }
    }
}
