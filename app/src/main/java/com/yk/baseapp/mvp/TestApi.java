package com.yk.baseapp.mvp;

import com.yk.base.net.Repo;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TestApi {
    @GET
    Flowable<ResponseBody> getBaiduApi(@Url String url);

    @GET
    @Headers({"terminal:mobile"})
    Flowable<ResponseBody> getTomcatApi(@Url String url);

    @GET
    @Headers({"terminal:mobile"})
    Flowable<ResponseBody> register(@Url String url, @Query("account")String account,@Query("pwd")String pwd,@Query("confirmPwd")String confirmPwd);

}
