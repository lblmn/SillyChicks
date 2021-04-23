package com.bing.monkey.haagendzs.exceptions;

import com.bing.monkey.haagendzs.exceptions.enums.SysImgsExceptionEnum;

public class SysImgsException extends RuntimeException {

    private Integer code;

    public SysImgsException(SysImgsExceptionEnum sysImgsExceptionEnum) {
        super(sysImgsExceptionEnum.getErrMsg());
        this.code = sysImgsExceptionEnum.getErrCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
