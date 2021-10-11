package com.daima.common.exception;

public class SystemInternalException extends NSException {
    public SystemInternalException() {
        super(CommonErrorCode.SYSTEM_INTERNAL_EXCEPTION);
    }
}
