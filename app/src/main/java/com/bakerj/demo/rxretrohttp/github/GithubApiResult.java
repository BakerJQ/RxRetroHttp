package com.bakerj.demo.rxretrohttp.github;

import android.text.TextUtils;

import com.bakerj.rxretrohttp.bean.IApiResult;

public class GithubApiResult implements IApiResult<GithubApiResult> {
    private String message = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return TextUtils.isEmpty(message);
    }

    @Override
    public GithubApiResult getData() {
        return this;
    }

    @Override
    public String getResultMsg() {
        return message;
    }

    @Override
    public String getResultCode() {
        return "";
    }

    @Override
    public String getDataField() {
        return "";
    }
}
