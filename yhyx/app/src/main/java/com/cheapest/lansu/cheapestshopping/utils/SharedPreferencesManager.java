package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author po.
 * @date 2016/6/20.
 */
public class SharedPreferencesManager{

    private static final String FILE_APPCONFIG = "app_config";
    private static final String FILE_USERINFO = "user_info";
    private static final String FILE_ORDER = "car_order";

    public static SharedPreferences getDefaultPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getAppConfigPreferences(Context context){
        return context.getApplicationContext().getSharedPreferences(FILE_APPCONFIG, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getUserInfoPreferences(Context context){
        return context.getApplicationContext().getSharedPreferences(FILE_USERINFO, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getOrderPreferences(Context context){
        return context.getApplicationContext().getSharedPreferences(FILE_ORDER, Context.MODE_PRIVATE);
    }

    //public static String getAppConfigString(Context context, String key, String defValue){
    //    return getAppConfigPreferences(context).getString(key, defValue);
    //}
    //
    //public static int getAppConfigInt(Context context, String key, int defValue){
    //    return getAppConfigPreferences(context).getInt(key, defValue);
    //}
    //
    //public static boolean getAppConfigBoolean(Context context, String key, boolean defValue){
    //    return getAppConfigPreferences(context).getBoolean(key, defValue);
    //}
}
