package com.yk.baseapp.mvp;


import com.yk.mvp_lib.IPresenterImpl;
import com.yk.net_lib.NetApi;
import com.yk.net_lib.intefaces.OnError;
import com.yk.net_lib.intefaces.OnResult;

import java.io.IOException;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

public class TestPresent extends IPresenterImpl<BaiduView> {



    public void getBaidu() {
        Flowable<ResponseBody> flowable = NetApi.getInstance().create(TestApi.class).getBaiduApi("http://www.baidu.com/");
        progressFlowableBody(flowable, new OnResult<ResponseBody>() {
            @Override
            public void result(ResponseBody responseBody) {
                try {
                    mView.transContent(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new OnError() {
            @Override
            public void error(int code, String msg) {

            }
        });
    }


    public void getTomcat() {
        Flowable<ResponseBody> flowable = NetApi.getInstance().create(TestApi.class).getTomcatApi("http://192.168.40.163:8099/web/hello");
        progressFlowableBody(flowable, new OnResult<ResponseBody>() {
            @Override
            public void result(ResponseBody responseBody) {
                try {
                    mView.tomcat(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } ,new OnError() {
            @Override
            public void error(int code, String msg) {

            }
        });
    }

    public void register() {
        Flowable<ResponseBody> flowable = NetApi.getInstance().create(TestApi.class)
                .register("http://192.168.40.163:8099/web/hello","luozhiran","123456","123456");
        progressFlowableBody(flowable, new OnResult<ResponseBody>() {
            @Override
            public void result(ResponseBody responseBody) {
                try {
                    mView.register(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },new OnError() {
            @Override
            public void error(int code, String msg) {

            }
        });
    }
}
