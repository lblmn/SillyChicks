package com.bing.monkey.haagendzs.exceptions.enums;

public enum HaatokenExceptionEnum {

    SYSTEM_ERR(10000, "系统错误"),
    EMPTY_PARAMS(10400, "输入参数为空"),
    NO_OBJECT_AS_THIS(20001, "没有该ID对应的对象");

    private Integer errCode;

    private String errMsg;

    HaatokenExceptionEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
    return errCode;
    }

    public String getErrMsg() {
    return errMsg;
    }
}
