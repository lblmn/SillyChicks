package com.bing.haagendzs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    /**
     * http 状态码
     */
    private int status;

    /**
     * 说明
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

}
