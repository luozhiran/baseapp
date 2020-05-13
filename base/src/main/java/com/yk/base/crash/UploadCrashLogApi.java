package com.yk.base.crash;

import com.yk.base.net.Repo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadCrashLogApi {
    @Multipart
    @POST("/file/upload")
    Call<String> uploadLogFile(@Part MultipartBody.Part body);

}
