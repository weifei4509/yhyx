package com.cheapest.lansu.cheapestshopping.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: ActivityCollectorUtlis
 * Author: xieganag
 * Email:
 * Date: 2017-02-16 17:48
 */

public class ActivityCollectorUtlis {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }





}
