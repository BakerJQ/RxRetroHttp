package com.bakerj.rxretrohttp.exception;

import com.bakerj.rxretrohttp.RxRetroHttp;
import com.bakerj.rxretrohttp.bean.IApiResult;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created by BakerJ on 2019/1/15
 */
public class ApiException extends IOException {
    public static final String SERVER_ERROR = "SERVER_ERROR", NOT_CARE = "NOT_CARE",
            NETWORK_ERROR = "NETWORK_ERROR", TIMEOUT_ERROR = "TIMEOUT_ERROR", NETWORK_NOT_OPEN =
            "NETWORK_NOT_OPEN", DATA_NULL = "DATA_NULL";
    //对应HTTP的状态码
    public static final String BADREQUEST = "400", UNAUTHORIZED = "401", FORBIDDEN = "403",
            NOT_FOUND =
                    "404", METHOD_NOT_ALLOWED = "405", REQUEST_TIMEOUT = "408",
            INTERNAL_SERVER_ERROR =
                    "500",
            BAD_GATEWAY = "502", SERVICE_UNAVAILABLE = "503", GATEWAY_TIMEOUT = "504";

    private final String code;
    private String displayMessage;
    private String message;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public static boolean isSuccess(IApiResult apiResult) {
        return apiResult != null && apiResult.isSuccess();
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof SuccessWithNullDataException) {
            ex = new ApiException(e, DATA_NULL);
            ex.message = e.getMessage();
            return ex;
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, String.valueOf(httpException.code()));
//            ex.message = httpException.getMessage();
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException) {
            com.jakewharton.retrofit2.adapter.rxjava2.HttpException httpException = (com
                    .jakewharton.retrofit2.adapter.rxjava2.HttpException) e;
            ex = new ApiException(httpException, String.valueOf(httpException.code()));
//            ex.message = httpException.getMessage();
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
//            ex.message = resultException.getMessage();
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, NETWORK_ERROR);
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, TIMEOUT_ERROR);
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ApiException(e, TIMEOUT_ERROR);
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        } else if (e instanceof ApiException) {
            return (ApiException) e;
        } else {
            ex = new ApiException(e, NOT_CARE);
            ex.message = RxRetroHttp.getDefaultErrMsg();
            return ex;
        }
    }

    public static ApiException getUnHandleException() {
        return new ApiException(NOT_CARE, "");
    }

    public String getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return message;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
