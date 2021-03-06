package com.cheapest.lansu.cheapestshopping.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;


/**
 * 自定义加载进度对话框
 * Created by gh on 2018/04/27.
 */

public class LoadingDialog extends Dialog {

    private TextView mTvMsg;

    public LoadingDialog(Context context) {
        super(context, R.style.style_loadingDialog);
        setContentView(R.layout.view_loadingdialog);
        mTvMsg = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    public LoadingDialog setMessage(String message) {
        mTvMsg.setText(message);
        return this;
    }
}
