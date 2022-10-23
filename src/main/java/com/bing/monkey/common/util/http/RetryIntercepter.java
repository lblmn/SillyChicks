package com.bing.monkey.common.util.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;


public class RetryIntercepter implements Interceptor{
    public int maxRetryCount;
    private int count = 0;
    public RetryIntercepter(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) {
        return retry(chain);
    }

    public Response retry(Chain chain){
        Response response = null;
        Request request = chain.request();
        try {
            response = chain.proceed(request);
            while (!response.isSuccessful() && count < maxRetryCount) {
                count++;
                response = retry(chain);
            }
        }
        catch (Exception e){
            while (count < maxRetryCount){
                count++;
                response = retry(chain);
            }
        }
        return response;
    }
}
