package com.yk.base.net;

import com.yk.base.BSApp;
import com.yk.base.CustomToast;
import com.yk.base.utils.LBMUtils;

import java.util.Locale;

import retrofit2.Call;

public abstract class RepoCallback<T> extends RfBaseCallback<T> {

    /**
     * 网络返回正常
     *
     * @param call
     * @param response
     */

    @Override
    public void onResponseSuccess(Call<Repo<T>> call, Repo<T> response) {
        if (response.code == -1 && response.data == null) {
            CustomToast.showToast(response.msg);
            onDataErrorResponse(call, "code == -1", response.code);
        } else {
            onCustomResponse(call, response);
        }
    }


    /**
     * 服务器返回错误数据
     * code !=0和success!=true 判断
     *
     * @param call
     */
    @Override
    public void onDataErrorResponse(Call<Repo<T>> call, String msg, int code) {
        CustomToast.showToast(msg);
        onResponseEmpty(call);
        if (code == 4007 || code == 4004) {
            LBMUtils.sendBroadcast(BSApp.getApp(), LBMUtils.LOGIN_ACTIVITY);
        }

    }

    /**
     * 服务器没有数据体返回
     *
     * @param call
     */
    @Override
    public void onNoDataResponse(Call<Repo<T>> call) {
        CustomToast.showToast("服务器异常");
        onResponseEmpty(call);
    }

    /**
     * 网络错误 500，400等
     *
     * @param call
     */
    @Override
    public void onNetErrorResponse(Call<Repo<T>> call, int netErrorCode) {
        CustomToast.showToast(String.format(Locale.CHINA, "网络错误%d", netErrorCode));
        onResponseEmpty(call);
    }


    public abstract void onCustomResponse(Call<Repo<T>> call, Repo<T> response);

    public abstract void onResponseEmpty(Call<Repo<T>> call);

}
