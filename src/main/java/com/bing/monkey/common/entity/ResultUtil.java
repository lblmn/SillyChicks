package com.bing.monkey.common.entity;

/**
 * @author zt
 * @version V1.0
 * @className ResultUtil
 * @description TODO
 * @date 2020/8/11 17:48
 */
public class ResultUtil {

    public ResultUtil() {
    }


    public static Result ok(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result ok() {
        return new Result(200, "操作成功", null);
    }

    public static Result ok(String msg) {
        return new Result(200, msg, null);
    }

    public static Result err() {
        return new Result(500, "失败成功", null);
    }

    public static Result err(String msg) {
        return new Result(500, msg, null);
    }

    public static Result err(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result badRequest(String message) {
        return new Result(400, message, null);
    }
}
