package com.bing.monkey.wxpusher.entity;

import lombok.Data;

/**
 * 说明：微信用户数据
 * 作者：zjiecode
 * 时间：2019-10-28
 */
@Data
public class WxUser {

    //UID，用户标志
    private String uid;

    //用户是否接收消息，也就是是否打开了消息开关
    private Boolean enable;

    //用户关注应用的时间
    private Long createTime;

    //昵称
    private String nickName;

    //头像
    private String headImg;
}
