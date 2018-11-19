package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.view.adapter.LoginPaperAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.NoScrollViewPager;
import com.cheapest.lansu.cheapestshopping.view.fragment.FriendMessageFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.SystemMessageFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;

public class MessageActivity extends BaseActivity {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<Fragment> list;
    private String[] titles = {"好友消息", "系统消息"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("消息通知");
        list = new ArrayList<>();
        list.add(new FriendMessageFragment());
        list.add(new SystemMessageFragment());
        MyAdapter  mAdapter =  new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewpager);
        setIndicator(tabLayout, 60, 60);
        //ViewPager的适配器


    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip)

    {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) (displaysMetrics.density * leftDip);
        int right = (int)
                (displaysMetrics.density * rightDip);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    @Override
    public int bindLayout() {
        return R.layout.activity_message;
    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
