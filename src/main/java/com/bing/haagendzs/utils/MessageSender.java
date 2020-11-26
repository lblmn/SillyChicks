package com.bing.haagendzs.utils;

import com.bing.haagendzs.models.wxpush.Message;
import com.bing.haagendzs.models.wxpush.MessageResult;
import com.bing.haagendzs.models.wxpush.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息发送类
 */
@Slf4j
@Service
public class MessageSender {

    /**
     * 四叶草：AT_Q5MNsnk0cHCuM3ym2qPGZ3s0zBcObkJn
     *
     * @param uids
     * @param msg
     */

    @Async
    public void send(List<String> uids, String msg) {
        for (String uid :
                uids) {
            Message message = new Message();
            message.setContentType(Message.CONTENT_TYPE_TEXT);
            message.setContent(msg);
            message.setUid(uid);
//            if (msg.contains("小象商城") || msg.contains("格乐惠购") || msg.contains("四叶草")) {
//                message.setAppToken("AT_OSKdI36LoJMY4YjW89sAiTtYHoVwz6C3");
//            } else {
//                message.setAppToken("AT_KEh4Eyyl0Rkcz9C44t6yuMqLNinwTZiZ");
//            }
            message.setAppToken("AT_bFL4r0W3wLp4WQPXEbRDM3ZBbk5WXS2I");
            Result<List<MessageResult>> result = WxPusher.send(message);
            log.info(LocalDateTime.now() + "给" + uid + "发送消息" + msg + "结果" + result);
        }
    }
}
