package com.yk.net_lib;

import com.yk.net_lib.gson.GsonFactory;
import com.yk.net_lib.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetApi {

    private OkHttpClient okHttpClient = null;
    private OkHttpClient.Builder okhttpBuilder = null;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;

    private static class Holder {
        private static NetApi netApi = new NetApi();
    }


    public static NetApi getInstance() {
        return Holder.netApi;
    }

    public NetApi() {
        loggingInterceptor = OkHttpConfig.getLogInterceptor(HttpLoggingInterceptor.Level.NONE);
        okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.addInterceptor(OkHttpConfig.getHeaderInterceptor());
        okhttpBuilder.addInterceptor(loggingInterceptor);
        okhttpBuilder.retryOnConnectionFailure(true);
        okHttpClient = okhttpBuilder.build();
        retrofit = new Retrofit.Builder().baseUrl(NetInit.baseUrl())
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void setLogLevel(HttpLoggingInterceptor.Level level) {
        loggingInterceptor.setLevel(level);
    }

    public <T> T create(Class<T> services) {
        return retrofit.create(services);
    }


    public void setCustomInterceptor(Interceptor customHeader) {
        okhttpBuilder.addInterceptor(customHeader);
    }

}
