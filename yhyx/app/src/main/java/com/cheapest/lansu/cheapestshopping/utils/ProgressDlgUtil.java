package com.cheapest.lansu.cheapestshopping.utils;


import android.app.ProgressDialog;
import android.content.Context;

import com.cheapest.lansu.cheapestshopping.R;

public class ProgressDlgUtil {
    static ProgressDialog progressDlg = null;

    /**
     * 启动进度条
     *
     * @param strMessage
     *            进度条显示的信息*            当前的activity
     */
    public static void showProgressDlg(String strMessage, Context ctx) {

        if (null == progressDlg) {
            progressDlg = new ProgressDialog(ctx);

            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //设置进度条标题
//            progressDlg.setTitle(Tool.getApplicationContext().getString(
//                    R.string.app_name));
            //提示的消息
            progressDlg.setMessage(strMessage);
            progressDlg.setIndeterminate(false);
            progressDlg.setCancelable(true);
            progressDlg.show();
        }
    }

    /**
     * 结束进度条
     */
    public static void stopProgressDlg() {
        if (null != progressDlg) {
            progressDlg.dismiss();
            progressDlg = null;
        }
    }





}
