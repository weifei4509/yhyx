package com.cheapest.lansu.cheapestshopping.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.yanzhenjie.permission.AndPermission;

/*
* 文件名：AppUtils
* 描    述： 应用的工具类
* 作    者：lansu
* 时    间：2018/5/9 14:38
* 版    权：lansus
*/
public class AppUtils {
    /**
     * 得到当前应用版本名称的方法
     *
     * @param context
     *            :上下文
     * @throws Exception
     */
    public static String getVersionName(Context context)   {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }  /**
     * 得到当前应用版本号的方法
     *
     * @param context
     *            :上下文
     * @throws Exception
     */
    public static int getVersionCode(Context context)   {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }



    public static void applyPermission(Activity context){

        AndPermission.with( context)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE ,Manifest.permission.CAMERA)
                .send();
    }



    public static int px2dip(Context context, float pxValue) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        resources = null;
        return (int) (pxValue / scale + 0.5f * (pxValue >= 0 ? 1 : -1));
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }






}
