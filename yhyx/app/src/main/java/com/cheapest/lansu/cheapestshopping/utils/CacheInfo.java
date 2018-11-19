package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.google.gson.Gson;


public class CacheInfo {
    private static String userID = "";
    private static String userToken = "";
    private static String userMobile = "";
    private static String authType = "";

    /**
     * 初始化userID
     */
    public static void setInitUserInfo(){
        userID = "";
        authType = "";
        userToken = "";
    }

    /**
     * 如果UserID是空，那么重新读取缓存文件
     */
    public static String getUserID(Context context){
        //如果
        if (TextUtils.isEmpty(userID)) {
            SharedPreferences sharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
            String userInfo = sharedPreferences.getString(Constants.PREFERENCE_KEY_USER_USERINFO, "");
            //判断时候有缓存的用户信息
            if (!TextUtils.isEmpty(userInfo)) {
                UserEntity customersEntity = new Gson().fromJson(userInfo, UserEntity.class);
               userID =customersEntity.getId();
            }
        }
        return userID;
    }



    /**
     * 如果UserID是空，那么重新读取缓存文件
     */
    public static String getUserToken(Context context){
        //如果
        if (TextUtils.isEmpty(userToken)) {
            SharedPreferences sharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
            userToken = sharedPreferences.getString(Constants.PREFERENCE_KEY_USER_CUSTOKEN, "");
        }
        return userToken;
    }

    /**
     * 如果userPhone是空，那么重新读取缓存文件
     */
    public static String getUserMobile(Context context){
        if (TextUtils.isEmpty(userMobile)) {
            SharedPreferences sharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
            userMobile = sharedPreferences.getString(Constants.PREFERENCE_KEY_USER_USERMOBILE, "");
        }
        return userMobile;
    }

    /**
     * 获取用户信息  缓存
     */
    public static UserEntity getUserInfoFromCache(Context context){
        UserEntity customersEntity;
        SharedPreferences userinfoPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
        String userInfo = userinfoPreferences.getString(Constants.PREFERENCE_KEY_USER_USERINFO, "");
        customersEntity = new Gson().fromJson(userInfo, UserEntity.class);
        return customersEntity == null ? new UserEntity() : customersEntity;
    }
}
