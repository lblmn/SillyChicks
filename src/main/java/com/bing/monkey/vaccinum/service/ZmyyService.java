package com.bing.monkey.vaccinum.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bing.monkey.vaccinum.constant.VaccinumConstant;
import com.bing.monkey.wxpusher.service.MessageSenderService;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
public class ZmyyService {

    @Autowired
    private MessageSenderService messageSenderService;

    // 获取有九价的诊所列表
    public void getClinicList() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://cloud.cn2030.com" +
                        "/sc" +
                        "/wx" +
                        "/HandlerSubscribe.ashx?" +
                        "act=CustomerList" +
                        "&city=[\"\",\"\",\"\"]" +
                        "&lat=34.22259" +
                        "&lng=108.94878" +
                        "&id=0" +
                        "&cityCode=0" +
                        "&product=1")
                .method("GET", null)
                .addHeader("Referer", "https://servicewechat.com/wx2c7f0f3c30d99445/73/page-frame.html")
                .addHeader("zftsl", UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject allClinicList = JSONObject.parseObject(Objects.requireNonNull(response.body()).string());
            JSONArray clinics = allClinicList.getJSONArray("list");
            getClinicResource(clinics);
            // 打印每个clinic的详细信息
//            log.info(
//                    JSON.toJSONString(
//                            allClinicList,
//                            SerializerFeature.PrettyFormat,
//                            SerializerFeature.WriteMapNullValue,
//                            SerializerFeature.WriteDateUseDateFormat
//                    )
//            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断诊所九价的状态
    @Async
    public void getClinicResource(JSONArray clinics) {
        log.error("******************************开始遍历请求******************************");
        for (Object clinic : clinics) {
            JSONObject clinicJson = (JSONObject) clinic;
            Integer clinicId = clinicJson.getInteger("id");

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://cloud.cn2030.com" +
                            "/sc" +
                            "/wx" +
                            "/HandlerSubscribe.ashx?" +
                            "act=CustomerProduct" +
                            "&id=" + clinicId +
                            "&lat=34.22259" +
                            "&lat=34.22259" +
                            "&lng=108.94878")
                    .method("GET", null)
                    .addHeader("Referer", "https://servicewechat.com/wx2c7f0f3c30d99445/73/page-frame.html")
                    .addHeader("zftsl", UUID.randomUUID().toString().replaceAll("-", ""))
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject clinicInfo = JSONObject.parseObject(response.body().string());
                // 获取诊所提供的疫苗列表
                JSONArray VaccLists = clinicInfo.getJSONArray("list");
                for (Object vacc : VaccLists) {
                    JSONObject vaccInfo = JSONObject.parseObject(vacc.toString());
                    String vaccName = vaccInfo.getString("text");
                    if (vaccName.contains("九价")) {
                        if (!vaccInfo.getString("BtnLable").equals("暂未开始")) {
                            log.error("诊所信息：\n");
                            log.error(JSON.toJSONString(clinicInfo, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue));
                            // 发送通知
                            String clinicName = clinicInfo.getString("cname");
                            String clinicAddr = clinicInfo.getString("addr");
                            String clinicTel = clinicInfo.getString("tel");
                            String clinicPic = clinicInfo.getString("BigPic");
                            String msg = "！可能有苗了！\n" +
                                    "请在《知苗易约》小程序查看" +
                                    "名称：" + clinicName + "\n" +
                                    "地址：" + clinicAddr + "\n" +
                                    "电话：" + clinicTel + "\n" +
                                    "图片地址：<a href='" + clinicPic + "'>查看诊所图片</a>\n";
                            messageSenderService.sendMsg(VaccinumConstant.WXPUSHER_TOKEN, msg, null);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.error("******************************结束遍历请求******************************");
    }

}
