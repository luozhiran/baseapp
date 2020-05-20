package com.yk.base.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.itg.util_lib.FileUtils;
import com.yk.base.ActivityStackManager;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import androidx.annotation.NonNull;

public class UnCatchExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mApp;
    private StringBuilder mDeviceInfo;
    private String mLogLocalPath;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;//系统的默认异常处理类

    public static UnCatchExceptionHandler crash() {
        return Holder.holder;
    }


    public void attach(Context context) {
        mApp = context.getApplicationContext();
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        String fileName = "log.txt";
        mLogLocalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash/" + fileName;
        uploadOneFile(mLogLocalPath);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        crashLog(e);
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(t, e);
        } else {
            ActivityStackManager.getInstance().finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            System.gc();
        }
    }


    private void crashLog(Throwable e) {
        mDeviceInfo = new StringBuilder();
        mDeviceInfo.append("------------------------start------------------------------\n");
        PackageManager pm = mApp.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mApp.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mDeviceInfo.append("versionName").append(":").append(versionName)
                        .append("\n")
                        .append("versionCode").append(":").append(versionCode).append("\n");
            }
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }


        mDeviceInfo.append("手机当前系统语言:").append(Locale.getDefault().getLanguage()).append("\n")
                .append("系统版本号:").append(android.os.Build.VERSION.RELEASE).append("\n")
                .append("手机型号:").append(android.os.Build.MODEL).append("\n")
                .append("手机厂商:").append(android.os.Build.BRAND).append("\n")
                .append("HARDWARE:").append(Build.HARDWARE).append("\n")
                .append("HOST:").append(Build.HOST).append("\n")
                .append("ID").append(Build.ID).append("\n")
                .append("TIME").append(Build.TIME).append("\n")
                .append("TYPE").append(Build.TYPE).append("\n")
                .append("USER").append(Build.USER).append("\n")
                .append("MANUFACTURER").append(Build.MANUFACTURER).append("\n");
        supportABIS(mDeviceInfo);
        mDeviceInfo.append(getCrashInfo(e)).append("\n");
        mDeviceInfo.append("------------------------end------------------------------\n");
        FileUtils.write(mLogLocalPath,mDeviceInfo.toString(),true);
    }


    private void supportABIS(StringBuilder builder) {
        String str = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            for (String abi : Build.SUPPORTED_32_BIT_ABIS) {
                str += abi + ",";
            }
            builder.append("32_bit_abis").append(":").append(str).append("\n");
            str = "";
            for (String abi : Build.SUPPORTED_64_BIT_ABIS) {
                str += abi + ",";
            }
            builder.append("34_bit_abis").append(":").append(str).append("\n");
            str = "";
            for (String abi : Build.SUPPORTED_ABIS) {
                str += abi + ",";
            }
            builder.append("bit_abis").append(":").append(str).append("\n");
        }
    }


    /**
     * 得到程序崩溃的详细信息
     */
    public String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        printWriter.close();
        return result.toString();
    }

    static class Holder {
        private static UnCatchExceptionHandler holder = new UnCatchExceptionHandler();
    }


    public static void uploadOneFile(String path) {
//        File file = new File(path);
//        if (!file.exists()) return;
//        Retrofit retrofit = ApiRetrofit.retrofit().getRetrofit()
//                .callbackExecutor(Executors.newSingleThreadExecutor())
//                .addConverterFactory(ScalarsConverterFactory.create()).build();
//        UploadCrashLogApi uploadOneFile = retrofit.create(UploadCrashLogApi.class);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        Call<String> call = uploadOneFile.uploadLogFile(part);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.code() == 200) {
//                    File file1 = new File(path);
//                    file1.delete();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
    }

}
