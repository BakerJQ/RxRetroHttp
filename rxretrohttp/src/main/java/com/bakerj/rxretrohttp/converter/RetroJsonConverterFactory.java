package com.bakerj.rxretrohttp.converter;

import com.bakerj.rxretrohttp.bean.IApiResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by BakerJ on 2019/1/15
 */
public class RetroJsonConverterFactory<ApiResultType extends IApiResult> extends Converter.Factory {
    private Class<ApiResultType> apiClass;

    private RetroJsonConverterFactory(Class<ApiResultType> apiClass) {
        this.apiClass = apiClass;
    }

    public static <ApiResultType extends IApiResult> RetroJsonConverterFactory create(Class<ApiResultType> apiClass) {
        return new RetroJsonConverterFactory(apiClass);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new RetroJsonResponseBodyConverter<>(type, apiClass);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        return new RetroJsonRequestBodyConverter<>();
    }
}
