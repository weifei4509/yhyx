package com.cheapest.lansu.cheapestshopping.view.fragment.strategy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.view.activity.BaseActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.MyFragmentPageAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 码农哥
 * @date 2018/7/22 0022  21:10
 * @email 441293364@qq.com
 * @TODO <p/>省惠优选攻略
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
public class StrategyActivity extends BaseActivity {

	MyFragmentPageAdapter pageAdapter;

	@Bind(R.id.pagerTabStrip)
	PagerSlidingTabStrip pagerTabStrip;

	@Bind(R.id.vp_content)
	ViewPager vp_content;

	List<ClassifyEntity> datas = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("省惠优选攻略");

		initData();
	}

	private void initData() {
		ClassifyEntity bean_01 = new ClassifyEntity();
		ClassifyEntity bean_02 = new ClassifyEntity();
		ClassifyEntity bean_03 = new ClassifyEntity();
		ClassifyEntity bean_04 = new ClassifyEntity();

		bean_01.setId(1);
		bean_02.setId(2);
		bean_03.setId(3);
		bean_04.setId(4);

		bean_01.setName("淘宝查券");
		bean_02.setName("领券指南");
		bean_03.setName("如何省钱");
		bean_04.setName("新手必看");

		datas.add(bean_01);
		datas.add(bean_02);
		datas.add(bean_03);
		datas.add(bean_04);

		fragmentlist.add(new StrategyFragment(0));
		fragmentlist.add(new StrategyFragment(1));
		fragmentlist.add(new StrategyFragment(2));
		fragmentlist.add(new StrategyFragment(3));

		setPager(datas);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_strategy;
	}

	ArrayList<Fragment> fragmentlist = new ArrayList<>();

	/**
	 * 设置ViewPager
	 */
	private void setPager(List<ClassifyEntity> datas) {
		if (pageAdapter == null) {
			pageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(), fragmentlist);
			pageAdapter.setTitleList(datas);
			vp_content.setAdapter(pageAdapter);
		}
		vp_content.setOffscreenPageLimit(2);
		pagerTabStrip.setDividerColorResource(R.color.white);
		pagerTabStrip.setIndicatorColorResource(R.color.white);
		pagerTabStrip.setSelectedTextColor(0xFFFFFFFF);
		pagerTabStrip.setViewPager(vp_content);
	}
}
