package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 读取本地文件的sharePreference
 *
 * @author by Sunqk
 * @date 2016/6/17
 */
public class SharePreferenceUtils{

    public static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 读取本地文件
     *
     * @param context 上下文环境
     */
    public SharePreferenceUtils(Context context){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mSharedPreferences.edit();
    }

    /**
     * 读取sharePreference文件中的值
     *
     * @param key   键值
     * @param value 默认值
     * @return String值
     */
    public static String getSharePreference(String key, String value){
        return mSharedPreferences.getString(key, value);
    }

    /**
     * 读取sharePreference文件中的值
     *
     * @param key   键值
     * @param value 默认值
     * @return int值
     */
    public static int getSharePreference(String key, int value){
        return mSharedPreferences.getInt(key, value);
    }

    /**
     * 读取sharePreference文件中的值
     *
     * @param key   键值
     * @param value 默认值
     * @return Boolean值
     */
    public static boolean getSharePreference(String key, boolean value){
        return mSharedPreferences.getBoolean(key, value);
    }

    /**
     * 保存布尔值到缓存文件
     *
     * @param key   键值
     * @param value 保存的值
     */
    public static void putSharePreference(String key, boolean value){
        editor.putBoolean(key, value).apply();
    }

    /**
     * 保存String到缓存文件
     *
     * @param key   键值
     * @param value 保存的值
     */
    public static void putSharePreference(String key, String value){
        editor.putString(key, value).apply();
    }

    /**
     * 保存String到缓存文件
     *
     * @param key   键值
     * @param value 保存的值
     */
    public static void putSharePreference(String key, int value){
        editor.putInt(key, value).apply();
    }

}
