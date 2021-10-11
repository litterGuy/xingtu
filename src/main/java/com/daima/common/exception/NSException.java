package com.daima.common.exception;


import org.apache.commons.lang.StringUtils;

public class NSException extends RuntimeException {
    private ErrorCode errorCode;
    private Integer code;
    private String msg;
    private Object[] args;

    public NSException() {
        super();
    }

    public NSException(ErrorCode errorCode) {
        this(errorCode, new Object[0]);
    }

    public NSException(ErrorCode errorCode, Object arg) {
        this(errorCode, new Object[]{arg});
    }

    public NSException(ErrorCode errorCode, Object... args) {
        super();
        this.errorCode = errorCode;
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.msg = errorCode.getMsg();
        }
        if (args == null) {
            this.args = new Object[0];
        } else {
            this.args = args;
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public String getMessage() {
        StringBuilder exMessage = new StringBuilder();
        exMessage.append("code: ");
        exMessage.append(this.code);
        exMessage.append("; message: ");
        exMessage.append(getMsg());
        return exMessage.toString();
    }


    public String getMsg() {
        if (StringUtils.isNotEmpty(this.msg)) {
            return String.format(this.msg, this.args);
        }
        return msg;
    }

}
