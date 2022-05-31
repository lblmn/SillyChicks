package com.bing.monkey.vaccinum.service;

import com.bing.monkey.vaccinum.constant.VaccinumConstant;
import com.bing.monkey.wxpusher.service.MessageSenderService;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Log4j2
@Service
public class VaccinumService {

    @Autowired
    private MessageSenderService messageSenderService;

    public void getPageContent() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(VaccinumConstant.NOTICEURL)
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String content = Objects.requireNonNull(response.body()).string();
            analysis(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void analysis(String content) {
        Document document = Jsoup.parse(content);
        String key = document.select("body > div.wrapper > div.content_l > div.title.daoyu > div.article-info > span.time").text();
        if (!DigestUtils.md5Hex(key).equals(VaccinumConstant.KEYMD5) && VaccinumConstant.KEYMD5.length() > 1) {
            log.error(key);
            messageSenderService.sendMsg(
                    VaccinumConstant.WXPUSHER_TOKEN,
                    VaccinumConstant.NOTICEMSG,
                    VaccinumConstant.NOTICEURL
            );
        }
        VaccinumConstant.KEYMD5 = DigestUtils.md5Hex(key);
    }

    public void testMsg() {
        messageSenderService.sendMsg(
                VaccinumConstant.WXPUSHER_TOKEN,
                "测试",
                VaccinumConstant.NOTICEURL
        );
    }

}
