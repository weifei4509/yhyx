package com.cheapest.lansu.cheapestshopping.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 文件名：ScrollOffsetTransformer
 * 描    述：
 * 作    者：wangJun
 * 时    间：2017/11/25 11:17
 * 版    权： 云杉智慧新能源技术有限公司版权所有
 */


public class ScrollOffsetTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.85f;

    private static float MIN_ALPHA = 1f;
    /**
     * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。
     * 当一个页面（page)填充整个屏幕时，positoin值为0；
     * 当一个页面（page)刚刚离开屏幕右(左）侧时，position值为1（-1）；
     * 当两个页面分别滚动到一半时，其中一个页面是-0.5，另一个页面是0.5。
     * 基于屏幕上页面的位置，通过诸如setAlpha()、setTranslationX()或setScaleY()方法来设置页面的属性，创建自定义的滑动动画。
     */
    @Override
    public void transformPage(View view, float position) {
//        if (position > 0) {
//            //右侧的缓存页往左偏移100
//            page.setTranslationX(-140 * position);
//        }
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
        //    view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            // Fade the page relative to its size.
//            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
//                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
          //  view.setAlpha(0);
        }

}
}