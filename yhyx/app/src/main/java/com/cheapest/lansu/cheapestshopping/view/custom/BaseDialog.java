package com.cheapest.lansu.cheapestshopping.view.custom;


import android.app.Dialog;
import android.content.Context;

/**
 * Created by Allen Liu on 2017/2/23.
 */
public class BaseDialog extends Dialog {
    private int res;

    public BaseDialog(Context context, int theme, int res) {
        super(context, theme);

        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }
    public BaseDialog(Context context,int res) {
        super(context);

        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }
}