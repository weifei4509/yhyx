package com.cheapest.lansu.cheapestshopping.utils;

/**
 * Created by admin on 2017/12/20.
 */

import android.content.Context;
import android.content.res.Resources;

import com.cheapest.lansu.cheapestshopping.R;


public class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockDiv);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
