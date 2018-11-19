package com.cheapest.lansu.cheapestshopping.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cheapest.lansu.cheapestshopping.model.entity.CategoriesEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heroYiu on 16/1/15.
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> list;
	List<ClassifyEntity> datasBeans;

	public MyFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		this.list = list;
	}

	public void setTitleList(List<ClassifyEntity> datasBeans) {
		this.datasBeans = datasBeans;
		Log.e("TAG", JSON.toJSONString(datasBeans));
	}

	/**
	 * Return the Fragment associated with a specified position.
	 *
	 * @param position
	 */
	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	/**
	 * Return the number of views available.
	 */
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return datasBeans.get(position).getName();
	}
}
