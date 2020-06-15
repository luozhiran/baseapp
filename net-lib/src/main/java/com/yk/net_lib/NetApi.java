package com.yk.net_lib;

import com.yk.net_lib.gson.GsonFactory;
import com.yk.net_lib.interceptor.HttpLoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetApi {

    private OkHttpClient okHttpClient = null;
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
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(OkHttpConfig.getHeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
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



}
