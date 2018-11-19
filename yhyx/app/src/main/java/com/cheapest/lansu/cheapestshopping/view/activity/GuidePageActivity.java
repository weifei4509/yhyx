package com.cheapest.lansu.cheapestshopping.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuidePageActivity extends AppCompatActivity {
    private static final int[] guide_picture = new int[]{R.mipmap.splach_1, R.mipmap.splach_2, R.mipmap.splach_3};
    @Bind(R.id.wizard_vp)
    ViewPager wizardVp;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.wizard_btn_login_register)
    RelativeLayout wizardBtnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
// 隐藏标题栏

        List<View> list1 = new ArrayList<>();
        LayoutInflater mLayoutflaer = LayoutInflater.from(this);
        for (int index : guide_picture) {
            View item = mLayoutflaer.inflate(R.layout.page_guide, null);
            ImageView imageView = (ImageView) item.findViewById(R.id.viewpaper_iv);
            imageView.setImageResource(index);
            list1.add(item);

        }

        MViewPageAdapter adapter = new MViewPageAdapter(list1);
        wizardVp.setAdapter(adapter);
        wizardVp.addOnPageChangeListener(adapter);
        wizardVp.setCurrentItem(0);

        wizardBtnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
              SharedPreferencesUtils.setParam(GuidePageActivity.this, Constants.isFirstUse, false);
                finish();
            }
        });

    }

    class MViewPageAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private List<View> mViewList;

        public MViewPageAdapter(List<View> views) {
            mViewList = views;
        }


        public int getCount() {
            return mViewList.size();
        }


        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }


        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }


        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        public void onPageSelected(int position) {
            wizardVp.setCurrentItem(position);


            wizardBtnLoginRegister.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }
}
