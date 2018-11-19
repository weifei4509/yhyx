package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
* 文件名：LoginPaperAdapter
* 描    述：
* 作    者：lansu
* 时    间：2018/4/26 15:07
* 版    权： 云杉智慧新能源技术有限公司版权所有
*/
public class LoginPaperAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;
    CharSequence[] mTitleArray={"密码登录","手机登录"};
    public LoginPaperAdapter() {
    }

    public LoginPaperAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleArray[position];
    }
}