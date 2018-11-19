package com.cheapest.lansu.cheapestshopping.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;

/**
 * Created by Administrator on 2018/10/31.
 */

public class SearchDialog extends Dialog {

    private TextView mTvMsg;

    public SearchDialog(Context context, View.OnClickListener cancelListener, View.OnClickListener okListener) {
        super(context, R.style.search_dialog);
        setContentView(R.layout.view_searchdialog);
        mTvMsg = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        findViewById(R.id.tv_cancel).setOnClickListener(cancelListener);
        findViewById(R.id.tv_ok).setOnClickListener(okListener);
    }

    public SearchDialog setMessage(String message) {
        mTvMsg.setText(message);
        return this;
    }
}
