package com.bing.monkey.common.util.http;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
public class HttpUtils {
    private static final int CONNECTION_TIME_OUT = 2000;//连接超时时间
    private static final int SOCKET_TIME_OUT = 2000;//读写超时时间
    private static final int MAX_IDLE_CONNECTIONS = 30;// 空闲连接数
    private static final long KEEP_ALLIVE_TIME = 60000L;//保持连接时间

    private OkHttpClient okHttpClient;
    private volatile static HttpUtils httpUtils;

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;

    }

    public HttpUtils() {
        ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALLIVE_TIME, TimeUnit.MILLISECONDS);
        this.okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(false) //自动重连设置为false
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new RetryIntercepter(3)) //重试拦截器2次
                .addNetworkInterceptor(new NetworkIntercepter()) //网络拦截器，统一打印日志
                .build();
    }

    /**
     * post发送带url参数的json
     *
     * @param url        请求的URL
     * @param pathParams 请求的url参数
     * @param body       json数据
     * @return
     */
    public String postJSON(String url, Map<String, String> pathParams, String body) {
        RequestBody requestBody = RequestBody.
                create(MediaType.parse("application/json;charset=utf-8"), body);
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (pathParams != null) {
            for (String key : pathParams.keySet()) {
                builder.addQueryParameter(key, pathParams.get(key));
            }
        }
        Request request = new Request.Builder()
                .post(requestBody)
                .url(builder.build().toString())
                .build();
        return execute(request);
    }

    /**
     * 发送请求
     *
     * @param request 构造的请求对象
     * @return 请求的结果
     */
    private String execute(Request request) {
        String responseBody = null;
        try {
            Response response = okHttpClient.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}

