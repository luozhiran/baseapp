package com.yk.base.net;

import com.yk.base.CustomToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RfBaseCallback<T> implements Callback<Repo<T>> {

    @Override
    public void onResponse(Call<Repo<T>> call, Response<Repo<T>> response) {
        if (response.code() == 200) {
            Repo<T> repo = response.body();
            if (repo != null) {
                if (repo.code == 0 || repo.success
                        || repo.code == 92003
                        || repo.code == 4103//92003身份证实名认证
                        || repo.code == 91002//检测师入驻申请已提交!
                        || repo.code == 91007 //您的账号已被停用，无法申请/登
                        || repo.code == 91001//检测师不存在!
                        ||repo.code == 91008//订单已经被接单，看看其他的订单吧
                ) {
                    onResponseSuccess(call, repo);
                } else {
                    onDataErrorResponse(call, repo.msg, repo.code);
                }
            } else {
                onNoDataResponse(call);

            }
        } else {
            onNetErrorResponse(call, response.code());
        }
    }

    /**
     * 网络返回正常
     *
     * @param call
     * @param response
     */
    public abstract void onResponseSuccess(Call<Repo<T>> call, Repo<T> response);

    /**
     * 服务器返回错误数据
     * code !=0和success!=true 判断
     *
     * @param call
     */
    public abstract void onDataErrorResponse(Call<Repo<T>> call, String msg, int code);

    /**
     * 服务器没有数据体返回
     *
     * @param call
     */
    public abstract void onNoDataResponse(Call<Repo<T>> call);

    /**
     * 网络错误 500，400等
     *
     * @param call
     */
    public abstract void onNetErrorResponse(Call<Repo<T>> call, int netErrorCode);


}
