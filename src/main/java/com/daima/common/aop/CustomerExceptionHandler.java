package com.daima.common.aop;

import com.daima.common.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerMyRuntimeException(RuntimeException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("errMsg", ex.getMessage());
        result.put("errCode", CommonErrorCode.SYSTEM_INTERNAL_EXCEPTION);
        log.error("error {}", ex);
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerException(Exception ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("errMsg", ex.getMessage());
        result.put("errCode", CommonErrorCode.SYSTEM_INTERNAL_EXCEPTION);
        log.error("error {}", ex);
        return result;
    }
}
