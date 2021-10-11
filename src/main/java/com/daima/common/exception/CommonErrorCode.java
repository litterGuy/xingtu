package com.daima.common.exception;

public interface CommonErrorCode {
    ErrorCode DATABASE_OPERATION_EXCEPTION = new ErrorCode(1, "数据库操作失败");
    ErrorCode PARAMETER_EXCEPTION = new ErrorCode(2, "参数错误: %s");
    ErrorCode SYSTEM_INTERNAL_EXCEPTION = new ErrorCode(3, "系统内部错误");
    ErrorCode SYSTEM_HANDLER_EXCEPTION = new ErrorCode(4, "系统内部错误:  %s");
    ErrorCode GET_PARAM_FAIL = new ErrorCode(5, "获取参数失败");
    ErrorCode UNKOWN_EXCEPTION = new ErrorCode(6, "未知异常");
    ErrorCode OPERATIOIN_FAIL = new ErrorCode(7, "操作失败");
    ErrorCode JSON_FAIL = new ErrorCode(8, "返回结果解析错误");
}
