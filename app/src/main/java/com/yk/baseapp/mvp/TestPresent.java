package com.yk.baseapp.mvp;

import com.yk.base.mvp.IPresenterImpl;
import com.yk.base.mvp.OnResult;
import java.io.IOException;

import okhttp3.ResponseBody;

public class TestPresent extends IPresenterImpl<BaiduView> {

    private TestModel mTestModel;

    public TestPresent() {
        mTestModel = new TestModel();
    }

    public void getBaidu() {
        progressFlowableBody(mTestModel.getBaidu(), new OnResult<ResponseBody>() {
            @Override
            public void result(ResponseBody responseBody) {
                try {
                    mView.transContent(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
