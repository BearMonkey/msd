package org.monkey.msd.cloud.common.dto;

import lombok.Data;
import org.monkey.msd.cloud.common.constants.CommonResult;

/**
 * Result
 *
 * @author cc
 * @since 2025/2/27 14:54
 */
@Data
public class Result<T> {

    /** 统一请求id */
    private String traceId;

    /** 返回码 */
    private String code;

    /** 消息内容 */
    private String msg;

    /** 响应数据 */
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(CommonResult.SUCCESS, CommonResult.SUCCESS_MSG, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonResult.SUCCESS, CommonResult.SUCCESS_MSG, data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(CommonResult.SUCCESS, msg, data);
    }

    public static <T> Result<T> success(String code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(CommonResult.FAIL, CommonResult.FAIL_MSG, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(CommonResult.FAIL, CommonResult.FAIL_MSG, data);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(CommonResult.FAIL, msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(CommonResult.FAIL, msg, data);
    }

    public static <T> Result<T> fail(String code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public Result() {
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, T data) {
        this.code = code;
        this.data = data;
    }
}
