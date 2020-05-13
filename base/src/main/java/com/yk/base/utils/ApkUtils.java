package com.yk.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.kongzue.dialog.v3.MessageDialog;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class ApkUtils {


    private static Context mApp;

    public static void register(Context application) {
        mApp = application;
    }

    // 3.下载成功，开始安装,兼容8.0安装位置来源的权限
    public static void installApkO(AppCompatActivity activity, String downloadApkPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = mApp.getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                L.i("8.0手机已经拥有安装未知来源应用的权限，直接安装！");
                installApk(downloadApkPath);
                activity.finish();
            } else {
                MessageDialog.show(activity, "", "安装应用需要打开安装未知来源应用权限，请去设置中开启权限", "确定")
                        .setOnOkButtonClickListener((baseDialog, v) -> {
                            Uri packageUri = Uri.parse("package:" + mApp.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUri);
                            activity.startActivityForResult(intent, 10086);
                            return false;
                        }).setOnDismissListener(() -> activity.finish());
            }
        } else {
            installApk(downloadApkPath);
            activity.finish();
        }
    }


    private static void installApk(String downloadApk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        L.i("安装路径==" + downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mApp, "com.yk.surveyor.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        mApp.startActivity(intent);
    }


    public static long getApkVersionCode() {
        PackageManager manager = mApp.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mApp.getPackageName(), 0);
            long versionCode = info.getLongVersionCode();
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getApkVersionName() {
        PackageManager manager = mApp.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mApp.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取apk包的信息：版本号，名称，图标等
     *
     * @param absPath
     * @return
     */
    public static String getApkInfo(String absPath) {
        PackageManager manager = mApp.getPackageManager();
        PackageInfo packageInfo = manager.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            String appName = manager.getApplicationLabel(appInfo).toString();// 得到应用名 
            String packageName = appInfo.packageName; // 得到包名 
            String version = packageInfo.versionName; // 得到版本信息 
            /* icon1和icon2其实是一样的 */
            Drawable icon1 = manager.getApplicationIcon(appInfo);// 得到图标信息 
            Drawable icon2 = appInfo.loadIcon(manager);
            String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, version, appName);
            L.i(String.format("PkgInfo: %s", pkgInfoStr));
            return version;
        }
        return null;

    }


}
