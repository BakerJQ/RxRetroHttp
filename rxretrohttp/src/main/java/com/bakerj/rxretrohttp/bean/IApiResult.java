package com.bakerj.rxretrohttp.bean;

/**
 * Api返回数据结构接口
 * Api Result Data Structure Interface
 * Created by BakerJ on 2019/1/15
 */
public interface IApiResult<T> {
    /**
     * 是否成功
     * api result success or not
     * @return true for success
     */
    boolean isSuccess();

    /**
     * 返回数据
     * api data
     * @return api data
     */
    T getData();

    /**
     * 返回message
     * api result message
     * @return message returned
     */
    String getResultMsg();

    /**
     * 返回code标识
     * api result code
     * @return code returned
     */
    String getResultCode();

    /**
     * 返回data数据的json字段名
     * api data field name in json
     * @return data field
     */
    String getDataField();
}
