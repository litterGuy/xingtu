package com.daima.common.exception;

public class ErrorCode {
    private int code;
    private String msg;

    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
