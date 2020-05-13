package com.yk.base.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yk.base.gson.GsonFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    OkHttpClient okHttpClient = null;
    private Retrofit retrofit;
    public static String BASE_URL = "";

    public ApiRetrofit() {

        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(getHeaderInterceptor())
                .addInterceptor(getInterceptor())
                .retryOnConnectionFailure(true).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.buildGson()))//GsonConverterFactory.create()
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    private static class Holder {
        private static ApiRetrofit retrofitClient = new ApiRetrofit();
    }


    public static ApiRetrofit getInstance() {
        return Holder.retrofitClient;
    }


    public <T> T create(Class<T> services) {
        return retrofit.create(services);
    }


    private Interceptor getHeaderInterceptor() {
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

    private Interceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


}
