package com.bing.haagendzs.exceptions;

import com.bing.haagendzs.exceptions.enums.HaaSignHistoryExceptionEnum;

public class HaaSignHistoryException extends RuntimeException {

    private Integer code;

    public HaaSignHistoryException(HaaSignHistoryExceptionEnum haaSignHistoryExceptionEnum) {
        super(haaSignHistoryExceptionEnum.getErrMsg());
        this.code = haaSignHistoryExceptionEnum.getErrCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
