package com.bakerj.rxretrohttp.converter;

import com.bakerj.rxretrohttp.bean.IApiResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by BakerJ on 2019/1/15
 */
public class RetroGsonConverterFactory<ApiResultType extends IApiResult> extends Converter.Factory {
    private final Gson gson;
    private Class<ApiResultType> apiClass;

    private RetroGsonConverterFactory(Gson gson, Class<ApiResultType> apiClass) {
        this.gson = gson;
        this.apiClass = apiClass;
    }

    public static <ApiResultType extends IApiResult> RetroGsonConverterFactory create
            (Class<ApiResultType> apiClass) {
        return create(new Gson(), apiClass);
    }

    @SuppressWarnings("ConstantConditions")
    public static <ApiResultType extends IApiResult> RetroGsonConverterFactory create(Gson gson,
                                                                                      Class<ApiResultType> apiClass) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new RetroGsonConverterFactory(gson, apiClass);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new RetroGsonResponseBodyConverter<>(gson, type, apiClass);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new RetroGsonRequestBodyConverter<>(gson, adapter);
    }
}
