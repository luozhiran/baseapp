package com.yk.net_lib;

import com.yk.net_lib.gson.GsonFactory;
import com.yk.net_lib.intefaces.ResponseInterceptor;
import com.yk.net_lib.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private static Interceptor customHeader;
    private ResponseInterceptor responseInterceptor;
    private List<Integer> mSuccessCode;

    private static class Holder {
        private static NetApi netApi = new NetApi();
    }


    public static NetApi getInstance() {
        return Holder.netApi;
    }

    public NetApi() {
        mSuccessCode = new ArrayList<>();
        loggingInterceptor = OkHttpConfig.getLogInterceptor(HttpLoggingInterceptor.Level.NONE);
        okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.addInterceptor(OkHttpConfig.getHeaderInterceptor());
        if (customHeader != null) {
            okhttpBuilder.addInterceptor(customHeader);
        }
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


    public static void setCustomInterceptor(Interceptor it) {
        customHeader = it;
    }

    public void setResponseInterceptor(ResponseInterceptor ri) {
        responseInterceptor = ri;
    }

    ResponseInterceptor getResponseInterceptor() {
        return responseInterceptor;
    }

    public void addSuccessCode(int ...code){
        mSuccessCode.clear();
        for (int num:code){
            mSuccessCode.add(num);
        }
    }

    public List<Integer> getSuccessCode(){
        return mSuccessCode;
    }
}
