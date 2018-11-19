package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.model.entity.UserEntity;
import com.cheapest.lansu.cheapestshopping.view.App;
import com.google.gson.Gson;

/*
* 文件名：UserInfoUtils
* 描    述：登录
* 作    者：lansu
* 时    间：2018/5/7 14:42
* 版    权：
*/
public class UserInfoUtils {



    /**
     * 获取用户是否登陆
     */
    public static  boolean isSharedLogin(Context context){
        //读取本地文件判断用户是否登录
        SharedPreferences sharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
        String login = sharedPreferences.getString(Constants.PREFERENCE_KEY_USER_ISLOGIN, Constants.FALSE);
        return Boolean.parseBoolean(login);
    }
  public  static void saveUserInfo(UserEntity userEntity,Context context){
      final String userInfo = new Gson().toJson(userEntity);
      SharedPreferences mSharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
      SharedPreferences.Editor editor = mSharedPreferences.edit();
      editor.putString(Constants.PREFERENCE_KEY_USER_USERINFO, userInfo);
      editor.putString(Constants.PREFERENCE_KEY_USER_ISLOGIN, Constants.TRUE);
      if (!StringUtils.isEmpty(userEntity.getToken())){
          editor.putString(Constants.PREFERENCE_KEY_USER_CUSTOKEN, userEntity.getToken());
      }
      editor.apply();
      WorkAvailable workAvailable=new WorkAvailable(context);
      workAvailable.setAlias(CacheInfo.getUserID(context),CacheInfo.getUserID(context));
  }
  public  static void saveUserInfo(UserEntity userEntity,Context context,String token){
      final String userInfo = new Gson().toJson(userEntity);
      SharedPreferences mSharedPreferences = SharedPreferencesManager.getUserInfoPreferences(context);
      SharedPreferences.Editor editor = mSharedPreferences.edit();
      editor.putString(Constants.PREFERENCE_KEY_USER_USERINFO, userInfo);
      editor.putString(Constants.PREFERENCE_KEY_USER_ISLOGIN, Constants.TRUE);
      if (!StringUtils.isEmpty(token)){
          editor.putString(Constants.PREFERENCE_KEY_USER_CUSTOKEN, token);
      }
      editor.apply();
      WorkAvailable workAvailable=new WorkAvailable(context);
      workAvailable.setAlias(CacheInfo.getUserID(context),CacheInfo.getUserID(context));
  }

    /**
     * 清空用户登录缓存
     * @param context
     */
    public static void logOff(Context context) {
        //初始化useriD
        CacheInfo.setInitUserInfo();
        //保留搜索历史
        WorkAvailable workAvailable=new WorkAvailable(context);
        workAvailable.setAlias("","");
        SharedPreferences sharedPreferences = SharedPreferencesManager.getDefaultPreferences(context);
        //清空用户缓存和订单缓存
        SharedPreferencesManager.getUserInfoPreferences(context).edit().clear().apply();
        SharedPreferencesManager.getOrderPreferences(context).edit().clear().apply();
        // 清除OrderController中的数据

    }



}
