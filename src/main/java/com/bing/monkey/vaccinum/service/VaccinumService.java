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

@Log4j2
@Service
public class VaccinumService {

    @Autowired
    private MessageSenderService messageSenderService;

    public void getPageContent() {
        String md5 = "";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://xa.bendibao.com/live/2019121/66349.shtm")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String content = response.body().string();
            analysis(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void analysis(String content) {
        Document document = Jsoup.parse(content);
        String key = document.select("body > div.wrapper > div.content_l > div.title.daoyu > div.article-info > span.time").text() + document.select("#bo").text();
        if (!DigestUtils.md5Hex(key).equals(VaccinumConstant.KEYMD5) && VaccinumConstant.KEYMD5.length() > 1) {
            messageSenderService.sendMsg(
                    VaccinumConstant.WXPUSHER_TOKEN,
                    "HPV疫苗相关消息有新动态，请点击下面的链接查看",
                    "http://xa.bendibao.com/live/2019121/66349.shtm"
            );
        }
    }

}
