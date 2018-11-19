package com.cheapest.lansu.cheapestshopping.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cheapest.lansu.cheapestshopping.Constant.Constants;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ClassifyEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.view.adapter.MyFragmentPageAdapter;
import com.cheapest.lansu.cheapestshopping.view.custom.PagerSlidingTabStrip;
import com.cheapest.lansu.cheapestshopping.view.fragment.home.ProductListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/28 0028  1:00
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
public class HomeMenuTwoActivity extends BaseActivity {

	String productId = "";
	String title = "";
	int type = 0;

	@Bind(R.id.pagerTabStrip)
	PagerSlidingTabStrip pagerTabStrip;

	ArrayList<Fragment> fragmentlist = new ArrayList<>();

	@Bind(R.id.vp_content)
	ViewPager vp_content;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		productId = getIntent().getStringExtra(Constants.INTENT_KEY_ID);
		title = getIntent().getStringExtra(Constants.INTENT_KEY_TITLE);
		type = getIntent().getIntExtra(Constants.INTENT_KEY_TYPE, 0);

		initView();
		initData();

	}

	@Override
	public int bindLayout() {
		return R.layout.activity_home_menu_two;
	}


	/**
	 * 初始化
	 */
	private void initView() {
		if (type == 1 || type == 3) {
			getToolbarTitle().setText(title);
		} else if (type == 2) {
			getToolbarTitle().setText("省惠优选排行榜");
		}
	}

	private void initData() {
		getCategories();
	}

	/**
	 * 分類
	 */
	private void getCategories() {
		RetrofitFactory.getInstence().API().categories().subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<List<ClassifyEntity>>(mContext) {

					@Override
					protected void onSuccees(BaseEntity<List<ClassifyEntity>> t) throws Exception {
						List<ClassifyEntity> datas = t.getData();
						ClassifyEntity bean = new ClassifyEntity();
						bean.setName("精选");
						bean.setId(1001);
						datas.add(0, bean);
						fragmentlist.add(0, new ProductListFragment(1001, productId, type));
						for (int i = 1; i < datas.size(); i++) {
							fragmentlist.add(new ProductListFragment(datas.get(i).getId(), productId, type));
						}
						setPager(datas);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}


	MyFragmentPageAdapter pageAdapter = null;

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
		pagerTabStrip.setSelectedTextColor(0xFFF96C74);
		pagerTabStrip.setViewPager(vp_content);
	}

}


