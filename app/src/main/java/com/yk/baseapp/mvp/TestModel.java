package com.yk.baseapp.mvp;

import com.yk.base.net.ApiRetrofit;
import com.yk.base.net.Repo;
import com.yk.base.net.RxScheduler;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class TestModel {

    public Flowable<ResponseBody> getBaidu() {
        return ApiRetrofit.getInstance().create(TestApi.class).getBaiduApi("http://www.baidu.com/");
    }
}
