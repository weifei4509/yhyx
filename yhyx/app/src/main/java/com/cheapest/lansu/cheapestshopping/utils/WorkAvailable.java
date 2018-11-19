package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


import static android.content.ContentValues.TAG;

/**
 * 检测当前手机状态的方法,包括网络状态和GPS是否开启
 * Created by Wangjun on 2015/12/28.
 */
public class WorkAvailable {
    private Context context;
    public WorkAvailable(Context context) {
        this.context=context;
    }

    public  static boolean isNetworkAvailable(Context mContext) {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }


        return flag;
    }

    /**
     * 判断第三方调用的应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static  boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }



    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public   void setAlias(String alias, String tag) {


        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
      //  mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tag));
    }

    private  final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private   final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private  final Handler mHandler = new Handler() {
        @Override
        public  void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    Set<String> set1 = new HashSet<>();
                    JPushInterface.setAliasAndTags(context,
                            (String) msg.obj,
                            set1,
                            mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    Set<String> set = new HashSet<>();
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(context,
                            (String) msg.obj, set,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

}
