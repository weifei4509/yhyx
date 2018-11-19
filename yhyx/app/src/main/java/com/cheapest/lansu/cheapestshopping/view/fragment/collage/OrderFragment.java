package com.cheapest.lansu.cheapestshopping.view.fragment.collage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheapest.lansu.cheapestshopping.R;
import com.cheapest.lansu.cheapestshopping.model.entity.BaseEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.CollageEntity;
import com.cheapest.lansu.cheapestshopping.model.entity.OrderEntity;
import com.cheapest.lansu.cheapestshopping.model.http.BaseObserver;
import com.cheapest.lansu.cheapestshopping.model.http.RetrofitFactory;
import com.cheapest.lansu.cheapestshopping.utils.CacheInfo;
import com.cheapest.lansu.cheapestshopping.view.adapter.CollageAdapter;
import com.cheapest.lansu.cheapestshopping.view.adapter.CollageOrderAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 码农哥
 * @date 2018/7/20 0020  20:57
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
public class OrderFragment extends Fragment {

	RecyclerView order_recycler;
	CollageOrderAdapter mAdapter = null;
	List<OrderEntity.DatasBean> mList = new ArrayList<>();

	int page = 1;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_collage_order, container, false);

		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		order_recycler = view.findViewById(R.id.order_recycler);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		order_recycler.setLayoutManager(layoutManager);
		if (mAdapter == null) {
			mAdapter = new CollageOrderAdapter(mList);
			order_recycler.setAdapter(mAdapter);
		}

		mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				order_recycler.postDelayed(new Runnable() {
					@Override
					public void run() {
						getOrderList();
					}

				}, 1000);
			}
		}, order_recycler);

	}

	private void initData() {
		getOrderList();
	}

	private void getOrderList() {
		RetrofitFactory.getInstence().API().groupOrders(CacheInfo.getUserID(getActivity()), page, 10)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BaseObserver<OrderEntity>(getActivity()) {

					@Override
					protected void onSuccees(BaseEntity<OrderEntity> t) throws Exception {
						Log.e("TAG", JSON.toJSONString(t.getData().getDatas()));
						if (mList.size() >= t.getData().getTotal()) {
							//数据全部加载完毕
							mAdapter.loadMoreEnd();
						} else {
							mAdapter.addData(t.getData().getDatas());
							mAdapter.loadMoreComplete();
							page++;
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

}
