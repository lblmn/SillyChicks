package com.bing.monkey.haagendzs.util;

import com.bing.monkey.wxpusher.WxPusher;
import com.bing.monkey.wxpusher.entity.Message;
import com.bing.monkey.wxpusher.entity.MessageResult;
import com.bing.monkey.wxpusher.entity.Result;
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
public class MessageSenderUtil {

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
            signSend(msg, uid);
        }
    }

    @Async
    public void sendWithUidAndMsg(String uid, String msg) {
        signSend(msg, uid);
    }

    @Async
    void signSend(String msg, String uid) {
        Message message = new Message();
        message.setContentType(Message.CONTENT_TYPE_TEXT);
        message.setContent(msg);
        message.setUid(uid);
        message.setAppToken("AT_bFL4r0W3wLp4WQPXEbRDM3ZBbk5WXS2I");
        Result<List<MessageResult>> result = WxPusher.send(message);
        log.info(LocalDateTime.now() + "给" + uid + "发送消息" + msg + "结果" + result);
    }

}
