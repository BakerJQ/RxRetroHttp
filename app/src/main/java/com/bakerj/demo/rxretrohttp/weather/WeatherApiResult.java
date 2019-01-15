package com.bakerj.demo.rxretrohttp.weather;

import com.bakerj.rxretrohttp.bean.IApiResult;

public class WeatherApiResult<T> implements IApiResult<T> {
    private int status;
    private T data;
    private String message;

    @Override
    public boolean isSuccess() {
        return status == 200;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getResultMsg() {
        return message;
    }

    @Override
    public String getResultCode() {
        return String.valueOf(status);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
