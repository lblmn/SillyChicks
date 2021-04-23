package com.bing.monkey.wxpusher.service;

import com.bing.monkey.vaccinum.constant.VaccinumConstant;
import com.bing.monkey.wxpusher.WxPusher;
import com.bing.monkey.wxpusher.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息发送类
 */
@Slf4j
@Service
public class MessageSenderService {


    public void sendMsg(String token, String msg, String url) {
        int page = 0, pageSize = 10, currentPageSize;
        do {
            page++;
            //获取已经关注的用户列表
            Result<Page<WxUser>> pageResult = WxPusher.queryWxUser(token, page, pageSize);
            Page<WxUser> records = pageResult.getData();
            currentPageSize = records.getRecords().size();
            for (WxUser user :
                    records.getRecords()) {
                notify(VaccinumConstant.WXPUSHER_TOKEN, user.getUid(), msg, url);
            }
        } while (currentPageSize == pageSize);
    }

    @Async
    public void notify(String token, String uid, String msg, String url) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent(msg);
        message.setUid(uid);
        message.setAppToken(token);
        if (!StringUtils.isEmpty(url)) {
            message.setUrl(url);
        }
        Result<List<MessageResult>> result = WxPusher.send(message);
        log.info(LocalDateTime.now() + "给" + uid + "发送消息" + msg + "结果" + result);
    }

}
