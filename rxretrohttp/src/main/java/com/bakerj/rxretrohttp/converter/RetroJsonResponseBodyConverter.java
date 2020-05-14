package com.bakerj.rxretrohttp.converter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bakerj.rxretrohttp.bean.IApiResult;
import com.bakerj.rxretrohttp.exception.ServerException;
import com.bakerj.rxretrohttp.exception.SuccessWithNullDataException;
import com.facebook.stetho.common.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by BakerJ on 2019/1/15
 */
class RetroJsonResponseBodyConverter<T, ApiResultType extends IApiResult> implements
        Converter<ResponseBody, T> {
    private Type type;
    private Class<ApiResultType> apiClass;

    RetroJsonResponseBodyConverter(Type type, Class<ApiResultType> apiClass) {
        this.type = type;
        this.apiClass = apiClass;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
//            String response = value.string().replaceAll("\\s*|\r|\n|\t", "");
            String response = value.string();
            LogUtil.e("response:" + response);
            if (apiClass != null) {
                //parse to IApiResult based obj 解析Result
                ApiResultType apiResult = JSON.parseObject(response, apiClass);
                //throw exception for failed situation 请求失败，抛异常
                if (!apiResult.isSuccess()) {
                    throw new ServerException(apiResult.getResultMsg(), apiResult.getResultCode());
                    //如果需求类型为ApiResult本身（一般情况下为无具体返回内容，只关心成功与否），则强转t
                    //if the type of result is IApiResult itself
                    // (mostly the case which we don't care about the content of the result, but
                    // only
                    // care for success or not)
                    //cast directly
                } else if (type.equals(apiClass)) {
                    //noinspection unchecked
                    return (T) apiResult;
                } else if (apiResult.getData() == null) {
                    throw new SuccessWithNullDataException(apiResult.getResultMsg(), apiResult
                            .getResultCode());
                }
                //if there's no data field, means that the result itself is the returned data
                //如果未设置data字段，则认为返回的整个结果就是数据段
                if (TextUtils.isEmpty(apiResult.getDataField())) {
                    return JSON.parseObject(response, type);
                }
                //parse data field 解析data数据
                return JSON.parseObject(JSONObject.parseObject(response).getString(apiResult
                        .getDataField()), type);
            }
            return JSON.parseObject(response, type);
        } finally {
            value.close();
        }
    }
}
