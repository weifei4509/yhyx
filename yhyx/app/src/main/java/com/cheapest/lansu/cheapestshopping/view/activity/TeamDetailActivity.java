package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.AppUtils;
import com.cheapest.lansu.cheapestshopping.view.fragment.TeamDirectFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.TeamIndirectFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;

public class TeamDetailActivity extends BaseActivity {
    public static final int VIP = 1;
    public static final int Normal = 0;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private ArrayList<Fragment> list;
    private String[] titles = {"直属", "间属"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra(Constants.INTENT_KEY_TEAM_TYPE, 1) == VIP) {
            getToolbarTitle().setText("VIP会员");
        } else {
            getToolbarTitle().setText("普通会员");
        }
        list = new ArrayList<>();
        Bundle  bundle=new Bundle();
        bundle.putInt(Constants.INTENT_KEY_TYPE,getIntent().getIntExtra(Constants.INTENT_KEY_TEAM_TYPE, 1));
       TeamDirectFragment teamDirectFragment= new TeamDirectFragment();
        TeamIndirectFragment teamIndirectFragment= new TeamIndirectFragment();
       teamDirectFragment.setArguments(bundle);
       teamIndirectFragment.setArguments(bundle);
        list.add(teamDirectFragment);
        list.add(teamIndirectFragment);
    MyAdapter mAdapter =  new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewpager);
        setIndicator(tabLayout, 60, 60);
        //ViewPager的适配器
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(AppUtils.dip2px(this,12));
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
        return R.layout.activity_team_detail;
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
