package com.yk.net_lib;

import com.yk.net_lib.interceptor.HttpLoggingInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpConfig {


    public static Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request origin = chain.request();
                Request.Builder builder = origin.newBuilder();
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
    }


    public static HttpLoggingInterceptor getLogInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
