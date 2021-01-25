package com.bing.haagendzs.exceptions;

import com.bing.haagendzs.exceptions.enums.HaatokenExceptionEnum;

public class HaatokenException extends RuntimeException {

    private Integer code;

    public HaatokenException(HaatokenExceptionEnum haatokenExceptionEnum) {
        super(haatokenExceptionEnum.getErrMsg());
        this.code = haatokenExceptionEnum.getErrCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
