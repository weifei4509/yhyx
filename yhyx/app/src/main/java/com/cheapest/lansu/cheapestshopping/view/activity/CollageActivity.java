package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.view.adapter.MyFragmentPageAdapter;
import com.cheapest.lansu.cheapestshopping.view.fragment.collage.CollageFragment;
import com.cheapest.lansu.cheapestshopping.view.fragment.collage.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  0:00
 * @email 441293364@qq.com
 * @TODO <p/>拼團
 * ** *** ━━━━━━神兽出没━━━━━━
 * ** ***       ┏┓　　  ┏┓
 * ** *** 	   ┏┛┻━━━┛┻┓
 * ** *** 　  ┃　　　　　　　┃
 * ** *** 　　┃　　　━　　　┃
 * ** *** 　　┃　┳┛　┗┳　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┃　　　┻　　　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┗━┓　　　┏━┛
 * ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 * ** *** 　　　　┃　　　┃
 * ** *** 　　　　┃　　　┗━━━┓
 * ** *** 　　　　┃　　　　　　　┣┓
 * ** *** 　　　　┃　　　　　　　┏┛
 * ** *** 　　　　┗┓┓┏━┳┓┏┛
 * ** *** 　　　　  ┃┫┫  ┃┫┫
 * ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class CollageActivity extends BaseActivity {

	@Bind(R.id.tv_collage)
	TextView tv_collage;

	@Bind(R.id.tv_order)
	TextView tv_order;

	@Bind(R.id.viewPager)
	ViewPager viewPager;

	MyFragmentPageAdapter pageAdapter;
	ArrayList<Fragment> fragments = new ArrayList<>();

	int index = 0;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("省惠拼团");

		fragments.add(new CollageFragment());
		fragments.add(new OrderFragment());

		pageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(index);
		viewPager.setOffscreenPageLimit(2);

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					tv_collage.setEnabled(true);
					tv_order.setEnabled(false);
					index = 0;
				} else {
					tv_collage.setEnabled(false);
					tv_order.setEnabled(true);
					index = 1;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});


	}

	@Override
	public int bindLayout() {
		return R.layout.activity_collage;
	}

	@OnClick({R.id.layout_collage, R.id.layout_order})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.layout_collage://今日拼團
				tv_collage.setEnabled(true);
				tv_order.setEnabled(false);
				index = 0;
				viewPager.setCurrentItem(index);
				break;

			case R.id.layout_order://我的订单
				tv_collage.setEnabled(false);
				tv_order.setEnabled(true);
				index = 1;
				viewPager.setCurrentItem(index);
				break;
		}
	}
}
