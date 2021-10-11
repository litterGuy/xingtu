package com.daima.common.exception;

public class ParameterException extends NSException {
    public ParameterException() {
        super(CommonErrorCode.PARAMETER_EXCEPTION);
    }

    public ParameterException(String msg) {
        super(CommonErrorCode.PARAMETER_EXCEPTION, msg);
    }
}
