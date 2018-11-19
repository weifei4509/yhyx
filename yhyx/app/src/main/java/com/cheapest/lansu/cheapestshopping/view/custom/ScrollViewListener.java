package com.cheapest.lansu.cheapestshopping.view.custom;

/**
 * 滑动的监听事件的接口
 */
public interface ScrollViewListener {

    void onScroll(int oldy,int dy,boolean isUp);  //dy Y轴滑动距离，isUp 是否返回顶部
}
