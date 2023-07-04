package com.leaves.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结构体
 *
 * @author haoxr
 * @date 2022/1/30
 **/
@Data
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> failed() {
        return failed(ResultCode.SYSTEM_EXECUTION_ERROR.getMsg());
    }

    public static <T> Result<T> failed(String msg) {
        return failed(ResultCode.SYSTEM_RESOURCE_ERROR, msg);
    }

    public static <T> Result<T> failed(IResultCode resultCode) {
        return failed(resultCode, resultCode.getMsg());
    }

    public static <T> Result<T> failed(IResultCode resultCode, String msg) {
        return result(resultCode.getCode(), msg, null);
    }

    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public static boolean isSuccess(Result<?> result) {
        return result != null && ResultCode.SUCCESS.getCode().equals(result.getCode());
    }
}
