package com.bing.haagendzs.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Objects;

@Log4j2
public class RequestUtil {

    public static String getNewToken(String token) {
        String newToken = null;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"partner\": \"apitest\",\r\n    \"secret\": \"111111\",\r\n    \"openid\": \"gh_68065de13ad5\",\r\n    \"app-id\": \"wx3656c2a2353eb377\"\r\n}");
        Request request = new Request.Builder()
                .url("https://haagendazs.smarket.com.cn/v1/api/token")
                .method("POST", body)
                .addHeader("Authorization", " Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String requestResult = Objects.requireNonNull(response.body()).string();
            log.error(requestResult);
            newToken = JSONObject.parseObject(requestResult).getString("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newToken;
    }

    public static Integer signIn(String token, String unique) {
        int retry = 0;
        int code = doSignIn(token, unique).getInteger("code");
        while (code != 0 && code != -1) {
            if (retry >= 3) {
                break;
            }
            token = RequestUtil.getNewToken(token);
            code = RequestUtil.doSignIn(token, unique).getInteger("code");
            retry++;
        }
        return code;
    }

    private static JSONObject doSignIn(String token, String unique) {
        JSONObject _return = new JSONObject();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://haagendazs.smarket.com.cn/v1/api/wxapp/daily/signIn?unionId=" + unique)
                .method("GET", null)
                .addHeader("Authorization", " Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String requetResult = Objects.requireNonNull(response.body()).string();
            log.error(requetResult);
            if (!StringUtils.isEmpty(requetResult)) {
                _return = JSONObject.parseObject(requetResult);
            } else {
                _return = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _return;
    }
}
