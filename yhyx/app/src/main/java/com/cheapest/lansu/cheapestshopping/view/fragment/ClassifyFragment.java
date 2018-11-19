package com.cheapest.lansu.cheapestshopping.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.MenuEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.view.activity.ProductSumActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.ClassifyListAdapter;
import com.cheapest.lansu.cheapestshopping.view.adapter.ClassifyMenuAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/2 0002  21:38
 * @email 441293364@qq.com
 * @TODO <p/>分类
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
public class ClassifyFragment extends Fragment {

	ObservableScrollView scrollView;

	ListView list_menu;
	ClassifyMenuAdapter menuAdapter;

	TextView tv_hot_type;
	RecyclerView hot_rlv;

	TextView tv_all_type;
	RecyclerView all_rlv;

	String pid = "";

	int position = 0;

	private List<ClassifyEntity> menuList = new ArrayList<>();

	private List<MenuEntity> all = new ArrayList<>();
	private List<MenuEntity> hot = new ArrayList<>();

	ClassifyListAdapter allAdapter = null;
	ClassifyListAdapter hotAdapter = null;

	public ClassifyFragment() {

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_classify, container, false);

		scrollView = view.findViewById(R.id.scrollView);

		//ListView分类
		list_menu = view.findViewById(R.id.list_menu);

		tv_hot_type = view.findViewById(R.id.tv_hot_type);
		hot_rlv = view.findViewById(R.id.hot_rlv);

		tv_all_type = view.findViewById(R.id.tv_all_type);
		all_rlv = view.findViewById(R.id.all_rlv);

		GridLayoutManager hotLayoutManager = new GridLayoutManager(getActivity(), 3);
		hot_rlv.setLayoutManager(hotLayoutManager);

		GridLayoutManager allLayoutManager = new GridLayoutManager(getActivity(), 3);
		all_rlv.setLayoutManager(allLayoutManager);

		allAdapter = new ClassifyListAdapter(all);
		all_rlv.setAdapter(allAdapter);

		hotAdapter = new ClassifyListAdapter(hot);
		hot_rlv.setAdapter(hotAdapter);

		list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				scrollView.scrollTo(0, 0);
				scrollView.smoothScrollTo(0, 0);
				menuAdapter.setSelectItem(position);
				menuAdapter.notifyDataSetInvalidated();
				pid = menuList.get(position).getId() + "";
				allAdapter.notifyItemRangeRemoved(0, all.size());
				hotAdapter.notifyItemRangeRemoved(0, hot.size());
				getClassifyList();
			}
		});

		allAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				MenuEntity menuEntity = allAdapter.getItem(position);
				if (null != menuEntity) {
					Intent intent = new Intent(getActivity(), ProductSumActivity.class);
					intent.putExtra(Constants.INTENT_KEY_TITLE, menuEntity.getName());
					intent.putExtra(Constants.INTENT_KEY_ID, menuEntity.getId() + "");
					intent.putExtra(Constants.INTENT_KEY_TYPE, 5);
					startActivity(intent);
				}
			}
		});

		hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				MenuEntity menuEntity = hotAdapter.getItem(position);
				if (null != menuEntity) {
					Intent intent = new Intent(getActivity(), ProductSumActivity.class);
					intent.putExtra(Constants.INTENT_KEY_TITLE, menuEntity.getName());
					intent.putExtra(Constants.INTENT_KEY_ID, menuEntity.getId() + "");
					intent.putExtra(Constants.INTENT_KEY_TYPE, 5);
					startActivity(intent);
				}
			}
		});

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
	}

	private void initData() {
		getClassifyList();
	}

	private void getClassifyList() {
//		ClassifyEntity
		RetrofitFactory.getInstence().API().categories(pid).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(getActivity()) {

					@Override
					protected void onSuccees(BaseEntity<List<ClassifyEntity>> t) throws Exception {
						menuList.addAll(t.getData());
						if (pid == "" || menuAdapter == null) {
							menuAdapter = new ClassifyMenuAdapter(getActivity(), t.getData());
							list_menu.setAdapter(menuAdapter);
						}

						if (t.getData() != null && t.getData().size() > 0) {
							List<MenuEntity> allList = t.getData().get(position).getSubCategories();
							allAdapter.setNewData(allList);
						} else {
							tv_all_type.setVisibility(View.GONE);
							all_rlv.setVisibility(View.GONE);
						}

						if (t.getData() != null && t.getData().size() > 0) {
							List<MenuEntity> hotList = t.getData().get(position).getHotCategories();
							hotAdapter.setNewData(hotList);
						} else {
							tv_hot_type.setVisibility(View.GONE);
							hot_rlv.setVisibility(View.GONE);
						}


					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});

	}
}
