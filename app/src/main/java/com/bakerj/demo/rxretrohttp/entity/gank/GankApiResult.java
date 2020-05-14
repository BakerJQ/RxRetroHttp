package com.bakerj.demo.rxretrohttp.entity.gank;

import com.bakerj.rxretrohttp.bean.IApiResult;

public class GankApiResult<T> implements IApiResult<T> {
    private int status;
    private T data;

    @Override
    public boolean isSuccess() {
        return status == 100;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getResultMsg() {
        return "";
    }

    @Override
    public String getResultCode() {
        return "";
    }

    @Override
    public String getDataField() {
        return "data";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }
}
