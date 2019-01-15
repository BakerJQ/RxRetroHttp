package com.bakerj.rxretrohttp.exception;

import java.io.IOException;

/**
 * Created by BakerJ on 2019/1/15
 */
public class ServerException extends IOException {
    private String code;

    public ServerException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ServerException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
