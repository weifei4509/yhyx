package com.cheapest.lansu.cheapestshopping.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 设置字体
 */
public class TextUtil {
    public static  void  setTextType(Context mContext, TextView textView){
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(),"font/droidsansthai.ttf");
        textView.setTypeface(typeFace);
    }

}
