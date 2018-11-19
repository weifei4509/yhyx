package com.cheapest.lansu.cheapestshopping.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.ExchangeEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.UserInfoUtils;
import com.cheapest.lansu.cheapestshopping.view.activity.CollageActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.ExchangeDetailActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.LoginActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.MyInformationActivity;
import com.cheapest.lansu.cheapestshopping.view.activity.SignInActivity;
import com.cheapest.lansu.cheapestshopping.view.adapter.FindExchangeAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/2 0002  21:39
 * @email 441293364@qq.com
 * @TODO <p/>发现
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
public class FindFragment extends Fragment {

	TextView toolbarTitle;
	ImageView toolbarReturnIv;

	//	活动
	LinearLayout layout_activity;//R.layout.item_find_activity_layout

	RecyclerView exchange_recycler;

	FindExchangeAdapter mListAdapter;

	List<ExchangeEntity.DatasBean> mList = new ArrayList<>();

	int page = 1;

	ImageView iv_pintuan;
	ImageView iv_sigin;

	public FindFragment() {

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_find, container, false);
		toolbarTitle = view.findViewById(R.id.toolbar_title);
		toolbarReturnIv = view.findViewById(R.id.toolbar_return_iv);

		exchange_recycler = view.findViewById(R.id.exchange_recycler);
//		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//		llm.setOrientation(LinearLayoutManager.VERTICAL);
//		exchange_recycler.setLayoutManager(llm);

		layout_activity = view.findViewById(R.id.layout_activity);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		exchange_recycler.setLayoutManager(layoutManager);
		if (mListAdapter == null) {
			mListAdapter = new FindExchangeAdapter(mList);
			exchange_recycler.setAdapter(mListAdapter);
		}

//		View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_find_header, null, false);
//		mListAdapter.addHeaderView(header);
//		mListAdapter.openLoadAnimation();
//		mListAdapter.setUpFetchEnable(true);

		mListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				exchange_recycler.postDelayed(new Runnable() {
					@Override
					public void run() {
						getExchangeGoodsList();
					}

				}, 1000);
			}
		}, exchange_recycler);

		mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getActivity(), ExchangeDetailActivity.class);
				intent.putExtra("id", mList.get(position).getId());
				startActivity(intent);
			}
		});

		initView(view);
		initData();

		return view;
	}

	private void initView(View view) {
		iv_pintuan = view.findViewById(R.id.iv_pintuan);
		iv_sigin = view.findViewById(R.id.iv_sigin);

		iv_pintuan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (UserInfoUtils.isSharedLogin(getActivity()))
					startActivity(new Intent(getActivity(), CollageActivity.class));
				else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
			}
		});

		iv_sigin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (UserInfoUtils.isSharedLogin(getActivity()))
					startActivity(new Intent(getActivity(), SignInActivity.class));
				else {
					startActivity(new Intent(getActivity(), LoginActivity.class));
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		toolbarReturnIv.setVisibility(View.GONE);
		toolbarTitle.setText(R.string.home_find);
	}

	private void initData() {
		getExchangeGoodsList();
//		getCollageList();
	}

	/**
	 * 获取兑换商品列表
	 */
	private void getExchangeGoodsList() {
		RetrofitFactory.getInstence().API()
				.swapGoogsLists(page, 10)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<ExchangeEntity>(getActivity()) {
					@Override
					protected void onSuccees(final BaseEntity<ExchangeEntity> t) throws Exception {
						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							mListAdapter.loadMoreEnd();
						} else {
							mListAdapter.addData(t.getData().getDatas());
							mListAdapter.loadMoreComplete();
							page++;
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}
}
