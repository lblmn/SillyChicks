package com.bing.monkey.haagendzs.exceptions;


import com.bing.monkey.haagendzs.exceptions.enums.HaaOrgDataExceptionEnum;

public class HaaOrgDataException extends RuntimeException {

    private Integer code;

    public HaaOrgDataException(HaaOrgDataExceptionEnum haaOrgDataExceptionEnum) {
        super(haaOrgDataExceptionEnum.getErrMsg());
        this.code = haaOrgDataExceptionEnum.getErrCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
