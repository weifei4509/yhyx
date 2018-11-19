package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.view.adapter.MyFragmentPageAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.PagerSlidingTabStrip;
import com.cheapest.lansu.cheapestshopping.view.fragment.PreferenceRankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 码农哥
 * @date 2018/7/13 0013  0:17
 * @email 441293364@qq.com
 * @TODO <p/>省惠优选排行榜
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
public class PreferenceRankActivity extends BaseActivity {

	@Bind(R.id.pagerTabStrip)
	PagerSlidingTabStrip pagerTabStrip;

	@Bind(R.id.vp_content)
	ViewPager vp_content;

	MyFragmentPageAdapter pageAdapter = null;

	ArrayList<Fragment> fragmentlist = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getToolbarTitle().setText("省惠优选排行榜");

		initData();
	}

	private void initData() {
		List<ClassifyEntity> datas = new ArrayList<>();
//		datas.add(new CategoriesEntity.DatasBean("精选"));
//		datas.add(new CategoriesEntity.DatasBean("女装"));
//		datas.add(new CategoriesEntity.DatasBean("美妆"));
//		datas.add(new CategoriesEntity.DatasBean("食品"));
//		datas.add(new CategoriesEntity.DatasBean("护肤"));
//		datas.add(new CategoriesEntity.DatasBean("手机数码"));

		fragmentlist.add(new PreferenceRankFragment());
		fragmentlist.add(new PreferenceRankFragment());
		fragmentlist.add(new PreferenceRankFragment());
		fragmentlist.add(new PreferenceRankFragment());
		fragmentlist.add(new PreferenceRankFragment());
		fragmentlist.add(new PreferenceRankFragment());

		setPager(datas);
	}

	@Override
	public int bindLayout() {
		return R.layout.activity_preference_rank;
	}

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
		pagerTabStrip.setDividerColorResource(R.color.bg_gray);
		pagerTabStrip.setIndicatorColorResource(R.color.colorPrimary);
		pagerTabStrip.setSelectedTextColor(R.color.colorPrimary);
		pagerTabStrip.setViewPager(vp_content);
	}
}
