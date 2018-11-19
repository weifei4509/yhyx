package com.cheapest.lansu.cheapestshopping.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.LoginActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MessageActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ScannerActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.SearchActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.SearchMainActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.MyFragmentPageAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.PagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

/**
 * @author 码农哥
 * @date 2018/7/8 0008  2:06
 * @email 441293364@qq.com
 * @TODO <p/>
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
public class HomeManageFragment extends Fragment implements View.OnClickListener {


	LinearLayout linFragmentHomeScanner;
	LinearLayout linFragmentHomeMessage;
	TextView tvSearch;

	PagerSlidingTabStrip pagerTabStrip;
	ViewPager vp_content;
	MyFragmentPageAdapter pageAdapter = null;

	RelativeLayout vToolbarBg;

	private float alpha = 0;


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_page, container, false);


		linFragmentHomeScanner = view.findViewById(R.id.lin_fragment_home_scanner);
		linFragmentHomeMessage = view.findViewById(R.id.lin_fragment_home_message);
		tvSearch = view.findViewById(R.id.tv_search);

		vToolbarBg = view.findViewById(R.id.v_toolbar_bg);
		pagerTabStrip = view.findViewById(R.id.pagerTabStrip);
		vp_content = view.findViewById(R.id.vp_content);


		linFragmentHomeScanner.setOnClickListener(this);
		linFragmentHomeMessage.setOnClickListener(this);
		tvSearch.setOnClickListener(this);


		return view;
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		EventBus.getDefault().register(this);
		initeData();
	}

	@SuppressLint("NewApi")
	private void initeData() {
		getCategories();
	}

	public void getCategories() {
		RetrofitFactory.getInstence().API().categories().subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(getActivity()) {

					@Override
					protected void onSuccees(BaseEntity<List<ClassifyEntity>> t) throws Exception {
						List<ClassifyEntity> datas = t.getData();
						ClassifyEntity bean = new ClassifyEntity();
						bean.setName("精选");
						bean.setId(1001);
						datas.add(0, bean);
						HomeFragment homeFragment = new HomeFragment();
						fragmentlist.add(0, homeFragment);
						for (int i = 1; i < datas.size(); i++) {
							Bundle bundle = new Bundle();
							bundle.putInt("categoryId", datas.get(i).getId());
							HomeTwoFragment twoFragment = HomeTwoFragment.newInstance(bundle);
							fragmentlist.add(twoFragment);
						}
						setPager(datas);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});

//		RetrofitFactory.getInstence().API()
//				.commodityCategories(1, 100)
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new BaseObserver<CategoriesEntity>(getActivity()) {
//
//					@Override
//					protected void onSuccees(BaseEntity<CategoriesEntity> t) throws Exception {
//						List<CategoriesEntity.DatasBean> datas = t.getData().getDatas();
//						CategoriesEntity.DatasBean bean = new CategoriesEntity.DatasBean();
//						bean.setName("精选");
//						bean.setId(1001);
//						datas.add(0, bean);
//						fragmentlist.add(0, new HomeFragment());
//						for (int i = 1; i < datas.size(); i++) {
//							fragmentlist.add(new HomeTwoFragment(datas.get(i).getId()));
//						}
//						setPager(datas);
//					}
//
//					@Override
//					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//					}
//				});
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(String event) {
		float alpha = Float.valueOf(event);
		if (alpha == this.alpha) return;
		this.alpha = alpha;
		vToolbarBg.setAlpha(alpha);
		if (alpha >= 1.0) {
			pagerTabStrip.setVisibility(View.VISIBLE);
		} else {
			pagerTabStrip.setVisibility(View.GONE);
		}

		Log.e("TAG", "alpha->" + alpha);
	}


	ArrayList<Fragment> fragmentlist = new ArrayList<>();

	/**
	 * 设置ViewPager
	 */
	private void setPager(List<ClassifyEntity> datas) {
		if (pageAdapter == null) {
			pageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragmentlist);
			pageAdapter.setTitleList(datas);
			vp_content.setAdapter(pageAdapter);
		}
		vp_content.setOffscreenPageLimit(3);
		pagerTabStrip.setDividerColorResource(R.color.bg_gray);
		pagerTabStrip.setIndicatorColorResource(R.color.colorPrimary);
		pagerTabStrip.setSelectedTextColor(0xFFF96C74);
		pagerTabStrip.setViewPager(vp_content);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_search:
//				startActivity(new Intent(getActivity(), SearchActivity.class));
				startActivity(new Intent(getActivity(), SearchMainActivity.class));
				break;
			case R.id.lin_fragment_home_message:
				if (UserInfoUtils.isSharedLogin(getActivity()))
					startActivity(new Intent(getActivity(), MessageActivity.class));
				else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.lin_fragment_home_scanner:
				Intent intent = new Intent(getActivity(), ScannerActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
				break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}
}
